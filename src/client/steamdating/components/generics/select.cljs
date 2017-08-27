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
   (dissoc props :options)

   (when-not multiple [:option {:value ""} ""])
   (let [sorted-options (sort-by #(.toLowerCase (nth % 1)) options)]
     (for [[value label] sorted-options]
       [:option {:key value
                 :value value}
        label]))])


(def select
  (->input-component render-select get-value))
