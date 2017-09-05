(ns steamdating.components.generics.input
	(:require [clojure.string :as s]
						[reagent.core :as reagent]
						[steamdating.models.form :as form]
						[steamdating.services.debug :as debug]))


(defn debounce [fun ms]
	(let [timeout (atom nil)]
		(fn [& args]
			(let [caller (fn []
										 (reset! timeout nil)
										 (apply fun args))]
				(when @timeout
					(.clearTimeout js/window @timeout))
				(reset! timeout
								(.setTimeout js/window caller ms))))))


(defn static-props
	[{:keys [field on-update] :as props}
	 {:keys [local-state get-value debounce?]}]
	(let [id (form/field->id field)

				on-update-debounced (when debounce?
															(debounce on-update 250))
				on-change #(let [new-value (get-value % props)]
										 (swap! local-state merge {:current-value new-value
																							 :pristine? false})
										 (if debounce?
											 (on-update-debounced field new-value)
											 (on-update field new-value)))]

		{:id id
		 :on-change on-change}))


(def dyn-props-keys
	[:autofocus :label :min :multiple :order :options :placeholder :required :type])


(defn dynamic-props
	[{:keys [field label placeholder form-state] :as props}
	 {:keys [local-state]}]
	(let [error (form/field-error form-state field)
        class (form/state->class (assoc @local-state :error error))]
    (assoc (select-keys props dyn-props-keys)
           :class class
           :error error
           :placeholder (or placeholder label)
           :value (:current-value @local-state))))


(defn update-local-state!
  [local-state {:keys [field form-state multiple]}]
  (let [{:keys [last-form-value]} @local-state
        form-value (form/field-value form-state field (if multiple [] ""))]
		(when-not (= last-form-value form-value)
			(swap! local-state merge {:current-value form-value
																:last-form-value form-value}))))


(defn get-value-default
	[event {:keys [type]}]
	(-> event .-target .-value
			(cond-> (= :number type) (js/Number))))


(defn render-default-value
	[props]
	[:input.sd-Input-value props])


(defn render-default-input
	[]
	(let [once (reagent/atom false)]
		(fn	[render-value {:keys [autofocus class error id label] :as props}]
			[:div.sd-Input {:class class}
			 (when label
				 [:label {:for id} label])
			 [render-value (-> props
												 (dissoc :autofocus :error)
												 (assoc :ref (fn [element]
																			 (when (and autofocus element (not @once))
																				 (reset! once true)
																				 (js/setTimeout #(.focus element) 100)))))]
			 [:p.sd-Input-info
				(or error "No error")]])))


(defn ->input-component
	([{:keys [get-value debounce? render-value render-input]
     :or {render-input render-default-input
          render-value render-default-value
          get-value get-value-default
          debounce? true}}]
	 (fn [{:keys [field multiple on-update form-state] :as base-props}]
		 (let [default-value (if multiple [] "")
					 form-value (form/field-value form-state field default-value)
					 local-state (reagent/atom {:current-value form-value
																			:last-form-value form-value
																			:pristine? true})
					 static-props (static-props base-props
                                      {:local-state local-state
                                       :get-value get-value
                                       :debounce? debounce})]
			 (fn input-component
				 [current-props]
         (update-local-state! local-state current-props)
				 (let [dyn-props (dynamic-props current-props {:local-state local-state
                                                       :debounce? debounce?})]
           [render-input render-value (merge static-props dyn-props)]))))))


(def input
	(->input-component {}))
