(ns steamdating.components.ranking.bests-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [steamdating.components.ranking.bests :refer [ranking-bests-in-faction
                                                          ranking-bests-scores]]
            [steamdating.services.debug :as debug]))


(defcard-rg ranking-bests-test.
  "Ranking bests components"


  (fn [state]
    (let [{:keys [bests icons]} @state]
      [:div.sd-ranking-bests
       [ranking-bests-in-faction {:bests (:in-faction bests)
                                  :icons icons}]
       [ranking-bests-scores {:bests (:scores bests)}]]))


  (reagent/atom
    {:bests {:in-faction {"Protectorate" {:name "tyty", :rank 1},
                          "Khador" {:name "tete", :rank 2},
                          "Legion" {:name "toto", :rank 3},
                          "Retribution" {:name "teuteu", :rank 5},
                          "Mercenaries" {:name "tutu", :rank 6}},
             :scores {:sos {:value 5, :names ["tete" "titi" "tutu"]},
                      :scenario {:value 38, :names ["tyty"]},
                      :army {:value 831, :names ["tutu"]},
                      :assassination {:value 3, :names ["tyty"]}}}
     :icons {:Cygnar "cygnar.png",
             :Retribution "retribution.png",
             :Khador "khador.png",
             :Legion "legion.png",
             :Trollbloods "trollbloods.png",
             :Minions "minions.png",
             :Protectorate "protectorate.png",
             :Grymkin "grymkin.png",
             :Cryx "cryx.png",
             :Skorne "skorne.png",
             :Mercenaries "mercenaries.png",
             :Convergence "convergence.png",
             :Circle "circle.png"}})


  {:inspect-data true
   :history true})
