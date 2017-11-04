(ns steamdating.components.ranking.bests
  (:require [clojure.string :as s]
            [re-frame.core :as re-frame]
            [steamdating.components.generics.faction-icon :refer [faction-icon]]
            [steamdating.services.rankings]))


(defn ranking-bests-in-faction
  [{:keys [bests icons]}]
  (when (not-empty bests)
    [:table.sd-table.sd-ranking-bests-in-faction
     [:caption
      [:div.sd-table-caption
       [:div.sd-table-caption-label "Bests in factions"]]]
     [:thead
      [:tr
       [:th "Faction"]
       [:th "Rank"]
       [:th "Players"]]]
     [:tbody
      (doall
        (for [[faction {:keys [rank name]}] (sort-by (fn [[f p]] (:rank p)) bests)]
          [:tr {:key faction}
           [:td [faction-icon {:name faction :icons icons}]]
           [:td rank]
           [:td name]]))]]))


(defn ranking-bests-scores
  [{:keys [bests]}]
  (when (some? (get-in bests [:sos :value]))
    [:table.sd-table.sd-ranking-bests-scores
     [:caption
      [:div.sd-table-caption
       [:div.sd-table-caption-label "Bests scores"]]]
     [:thead
      [:tr
       [:th]
       [:th "Score"]
       [:th "Players"]]]
     [:tbody
      [:tr
       [:th "SOS"]
       [:td (get-in bests [:sos :value])]
       [:td (s/join ", " (sort (get-in bests [:sos :names])))]]
      [:tr
       [:th "Scenario"]
       [:td (get-in bests [:scenario :value])]
       [:td (s/join ", " (sort (get-in bests [:scenario :names])))]]
      [:tr
       [:th "Army"]
       [:td (get-in bests [:army :value])]
       [:td (s/join ", " (sort (get-in bests [:army :names])))]]
      [:tr
       [:th "Assassination"]
       [:td (get-in bests [:assassination :value])]
       [:td (s/join ", " (sort (get-in bests [:assassination :names])))]]]]))


(defn ranking-bests
  []
  (let [{:keys [bests icons]} @(re-frame/subscribe [:sd.rankings/bests])
        {:keys [in-faction scores]} bests]
    [:div.sd-ranking-bests
     ;; [:p (with-out-str (cljs.pprint/pprint bests))]
     [ranking-bests-in-faction {:bests in-faction :icons icons}]
     [ranking-bests-scores {:bests scores}]]))
