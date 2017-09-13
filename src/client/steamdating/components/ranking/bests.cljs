(ns steamdating.components.ranking.bests
  (:require [clojure.string :as s]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [steamdating.services.rankings]))


(defn bests-in-faction
  [bests]
  [:table.sd-RankingBests-factions
   [:thead
    [:tr
     [:th "Faction"]
     [:th "Rank"]
     [:th "Players"]]]
   [:tbody
    (doall
      (for [[faction {:keys [rank name]}] (sort-by (fn [[f p]] (:rank p)) bests)]
        [:tr {:key faction}
         [:td faction]
         [:td rank]
         [:td name]]))]])


(defn bests-scores
  [bests]
  [:table.sd-RankingBests-scores
   [:thead
    [:tr
     [:th]
     [:th "Score"]
     [:th "Players"]]]
   [:tbody
    [:tr
     [:th "SOS"]
     [:td (get-in bests [:sos :value])]
     [:td (s/join ", " (get-in bests [:sos :names]))]]
    [:tr
     [:th "Scenario"]
     [:td (get-in bests [:scenario :value])]
     [:td (s/join ", " (get-in bests [:scenario :names]))]]
    [:tr
     [:th "Army"]
     [:td (get-in bests [:army :value])]
     [:td (s/join ", " (get-in bests [:army :names]))]]
    [:tr
     [:th "Assassination"]
     [:td (get-in bests [:assassination :value])]
     [:td (s/join ", " (get-in bests [:assassination :names]))]]]])


(defn bests-component
  []
  (let [bests @(re-frame/subscribe [:steamdating.rankings/bests])]
    [:div.sd-RankingBests
     (bests-in-faction (get-in bests [:faction]))
     (bests-scores bests)]))
