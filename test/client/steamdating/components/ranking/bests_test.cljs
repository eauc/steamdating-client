(ns steamdating.components.ranking.bests-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
						[reagent.core :as reagent]
						[steamdating.components.ranking.bests :refer [bests-in-faction bests-scores]]))


(defcard-rg ranking-bests-in-faction-test
  "## Ranking Bests in Faction"


  (fn [state]
    [:div
     [bests-in-faction (:bests @state)]])


  (reagent/atom {:bests {"Protectorate" {:name "tyty", :rank 1},
                         "Legion" {:name "toto", :rank 2},
                         "Khador" {:name "tete", :rank 3},
                         "Retribution" {:name "teuteu", :rank 4},
                         "Mercenaries" {:name "tutu", :rank 5}}})


  {:inspect-data true})


(defcard-rg ranking-bests-scores-test
  "## Ranking Bests Scores"


  (fn [state]
    [:div
     [bests-scores (:bests @state)]])


  (reagent/atom {:bests {:sos {:value 7, :names ["toto" "tete"]},
                         :scenario {:value 41, :names ["toto"]},
                         :army {:value 872, :names ["toto"]},
                         :assassination {:value 3, :names ["toutou"]}}})


  {:inspect-data true})
