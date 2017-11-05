(ns steamdating.components.online.tournaments-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [steamdating.components.online.tournaments :refer [online-tournaments-render]]
            [steamdating.models.filter :refer [->pattern]]
            [steamdating.models.online :as online]
            [steamdating.models.sort :as sort]
            [steamdating.services.debug :as debug]))


(defcard-rg online-tournaments-test.
  "Online tournaments component"


  (fn [state]
    (let [on-filter-update #(swap! state assoc  :filter %)
          on-tournament-click #(println "tournament-click" %)
          on-sort-by #(swap! state update :sort sort/toggle-by %)

          {:keys [filter list sort]} @state
          tournaments (->> list
                           (online/tournaments-filter-with (->pattern filter))
                           (online/tournaments-sort-with sort))]

      [online-tournaments-render
       {:on-filter-update on-filter-update
        :on-sort-by on-sort-by
        :on-tournament-click on-tournament-click
        :state (assoc @state :list tournaments)}]))


  (reagent/atom
    {:filter ""
     :sort {:by :date
            :reverse false}
     :list [{:_id 1
             :date "2017-10-11"
             :name "Vinter is coming '17"
             :updatedAt "2017-10-11 10:48:35"
             :user "manu"
             :link "/tournaments/mine/1"}
            {:_id 2
             :date "2017-07-08"
             :name "Joinville '17"
             :updatedAt "2017-07-09 10:48:35"
             :user "manu"
             :link "/tournaments/mine/2"}
            {:_id 3
             :date "2017-12-03"
             :name "Blood & Rust '17"
             :updatedAt "2017-12-04 09:04:55"
             :user "manu"
             :link "/tournaments/mine/3"}]})


  {:inspect-data true
   :history true})
