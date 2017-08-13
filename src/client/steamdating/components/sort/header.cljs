(ns steamdating.components.sort.header
  (:require [clojure.string :as s]
            [re-frame.core :as re-frame]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.services.sorts]))


(defn sort-header
  [{:keys [by reverse] :as current-sort}
   {:keys [label name on-sort-by]}]
  (let [active (= name by)
        on-click #(on-sort-by
                    {:by name
                     :reverse (if active
                                (not reverse)
                                false)})]
    [:th.sd-SortHeader
     {:on-click on-click
      :title (str "Click to sort by " label)}
     [:span label " "]
     [:span.sd-SortHeader-icon
      {:class (when active
                "sd-SortHeader-icon-show")}
      [icon {:name (str "chevron-" (if reverse "up" "down"))}]]]))
