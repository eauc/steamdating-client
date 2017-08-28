(ns steamdating.components.generics.select
  (:require [reagent.core :as reagent]
            [steamdating.components.generics.input :refer [->input-component]]
            [steamdating.models.form :as form]
            [steamdating.services.debug :as debug]))


(defn get-value
  [event {:keys [multiple]}]
  (if-not multiple
    (-> event .-target .-value)
    (-> event .-target .-options
        (js/Array.from)
        (.filter #(.-selected %))
        (.map #(.-value %))
        (js->clj))))


(defn render-select
  [{:keys [on-change options multiple] :as props}]
  [:select.sd-Input-value
   (-> props
       (dissoc :options)
       (update :value #(if (some? %) % "")))

   (when-not multiple [:option {:value ""} ""])
   (let [sorted-options (sort-by #(.toLowerCase (nth % 1)) options)]
     (for [[val label] sorted-options]
       [:option {:key val
                 :value val}
        label]))])


(def select
  (->input-component {:get-value get-value
                      :render-value render-select
                      :debounce? false}))
