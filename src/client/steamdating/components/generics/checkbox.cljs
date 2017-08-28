(ns steamdating.components.generics.checkbox
  (:require [reagent.core :as reagent]
            [steamdating.models.form :as form]
            [steamdating.components.generics.input :refer [->input-component]]))


(defn get-value
  [event]
  (-> event .-target .-checked))


(defn render-checkbox
  [_ {:keys [class error id label value] :as props}]
  [:div.sd-Input {:class class}
   [:label {:for id}
    [:input.sd-Input-value
     (-> props
         (dissoc :error)
         (assoc :type "checkbox" :checked value))]
    [:span (str " " label)]]
   [:p.sd-Input-info
    (or error "No error")]])


(def checkbox
  (->input-component {:render-input render-checkbox
                      :get-value get-value
                      :debounce? false}))
