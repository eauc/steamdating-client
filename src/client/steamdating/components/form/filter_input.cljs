(ns steamdating.components.form.filter-input
  (:require [steamdating.components.form.input :refer [form-input]]
            [steamdating.components.generics.button :refer [button]]))


(defn filter-input
  [{:keys [filter on-filter-update]}]
  [:div.sd-filter-input
   [button {:class "sd-filter-input-clear"
            :icon "delete"
            :on-click #(on-filter-update "")
            :title "Clear filter"}]
   [form-input {:on-update on-filter-update
                :placeholder "Filter"
                :value filter}]])
