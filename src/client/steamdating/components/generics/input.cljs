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
  [current-value pristine get-event-value debounce?
   {:keys [field on-update] :as props}]
  (let [id (s/join "." (map #(if (keyword? %) (name %) %) field))

        on-update-debounced (when debounce?
                              (debounce on-update 250))
        on-change #(let [new-value (get-event-value % props)]
                     (reset! current-value new-value)
                     (reset! pristine false)
                     (if debounce?
                       (on-update-debounced field new-value)
                       (on-update field new-value)))]

    {:id id
     :on-change on-change}))


(def dyn-props-keys
  [:autofocus :label :multiple :order :options :placeholder :required :type])


(defn dynamic-props
  [current-value pristine debounce?
   {:keys [field label multiple placeholder state] :as props}]
  (let [has-value? (or (and (seq? @current-value) (not-empty @current-value))
                       (and (not (seq? @current-value)) (not (nil? @current-value))))
        clear? (and @pristine (not has-value?))
        error (form/field-error state field)
        show-error? (and (not clear?) error)
        show-valid? (and (not clear?) (not error))
        class (s/join " " (remove nil? [(when @pristine "pristine")
                                        (when show-valid? "valid")
                                        (when show-error? "error")]))
        value (if debounce?
                @current-value
                (form/field-value state field @current-value))]
    (assoc (select-keys props dyn-props-keys)
           :class class
           :error error
           :placeholder (or placeholder label)
           :value value)))


(defn get-value
  [event {:keys [type]}]
  (-> event .-target .-value
      (cond-> (= :number type) (js/Number))))


(defn render-field
  [props]
  [:input.sd-Input-value props])


(defn render-input
  []
  (let [once (reagent/atom false)]
    (fn
      [render-value {:keys [autofocus class error id label] :as props}]
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
  ([render-value get-event-value debounce?]
   (fn [{:keys [field multiple on-update state] :as base-props}]
     (let [default-value (if multiple [] "")
           current-value (reagent/atom (form/field-value state field default-value))
           pristine (reagent/atom true)
           static-props (static-props current-value pristine get-event-value debounce? base-props)]
       (fn input-component
         [props]
         (let [dyn-props (merge static-props
                                (dynamic-props current-value pristine debounce? props))]
           [render-input render-value dyn-props])))))
  ([render-value get-event-value]
   (->input-component render-value get-event-value true)))


(def input
  (->input-component render-field get-value))
