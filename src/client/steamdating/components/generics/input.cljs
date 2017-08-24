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
  [current-value pristine get-event-value
   {:keys [field on-update] :as props}]
  (let [id (s/join "." (map #(if (keyword? %) (name %) %) field))

        on-update-debounced (debounce on-update 250)
        on-change #(let [new-value (get-event-value % props)]
                     (reset! current-value new-value)
                     (reset! pristine false)
                     (on-update-debounced field new-value))]

    {:id id
     :on-change on-change}))


(def dyn-props-keys
  [:autofocus :label :multiple :order :options :placeholder :required :type])


(defn dynamic-props
  [current-value pristine
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

        default-value (if multiple [] "")
        value (if (nil? @current-value) default-value @current-value)]

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
  [value {:keys [class error id label] :as props}]
  [:div.sd-Input {:class class}
   (when label
     [:label {:for id}
      label])
   [value (dissoc props :autofocus :error)]
   [:p.sd-Input-info
    (or error "No error")]])


(defn ->input-component
  [render-value get-event-value]
  (fn [{:keys [field on-update state] :as base-props}]
    (let [current-value (reagent/atom (form/field-value state field))
          pristine (reagent/atom true)
          static-props (static-props current-value pristine get-event-value base-props)]
      (fn input-component
        [props]
        (let [dyn-props (merge static-props
                               (dynamic-props current-value pristine props))]
          (render-input render-value dyn-props))))))


(def input
  (->input-component render-field get-value))
