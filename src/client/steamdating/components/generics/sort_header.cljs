(ns steamdating.components.generics.sort-header
  (:require [clojure.string :as s]
            [steamdating.components.generics.icon :refer [icon]]))


(defn sort-header
  [{:keys [col on-sort-by state] :as props}]
  (let [{:keys [by reverse?]} state]
    [:th.sd-sort-header {:class (when (= by col) "active")
                         :on-click #(on-sort-by col)}
     [:span.sd-sort-header-label
      (s/capitalize (name col))]
     [icon {:name (if reverse? "chevron-up" "chevron-down")}]]))
