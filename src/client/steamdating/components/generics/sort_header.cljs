(ns steamdating.components.generics.sort-header
  (:require [clojure.string :as s]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.models.ui :as ui]))


(defn sort-header
  [{:keys [class col label on-sort-by state] :as props}]
  (let [{:keys [by reverse?]} state]
    [:th.sd-sort-header
     (-> props
         (dissoc :col :label :on-sort-by :state)
         (assoc :class (ui/classes class (when (= by col) "active"))
                :on-click #(on-sort-by col)))
     [:span.sd-sort-header-label
      (or label (s/capitalize (name col)))]
     [icon {:name (if reverse? "chevron-up" "chevron-down")}]]))
