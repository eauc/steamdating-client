(ns steamdating.components.form.input-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [steamdating.components.form.input :refer [form-input]]))


(defcard-rg form-input-text-test
  "Generic form input component"
  (fn [state]
    [:div
     [form-input {:autofocus? true
                  :name "text-input-test"
                  :on-update #(swap! state assoc :value %)
                  :value (:value @state)}]
     [:button {:type :button
               :on-click #(reset! state {:value "value"})}
      "Reset value"]])
  (reagent/atom {:value "value"})
  {:inspect-data true
   :history true})


(defcard-rg form-input-number-test
  "Generic form input component"
  (fn [state]
    [:div
     [form-input {:name "text-input-test"
                  :on-update #(swap! state assoc :value %)
                  :value (:value @state)}]
     [:button {:type :button
               :on-click #(reset! state {:value 42})}
      "Reset value"]])
  (reagent/atom {:value 42})
  {:inspect-data true
   :history true})
