(ns steamdating.components.round.next-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [steamdating.components.round.next :refer [round-next-render]]
            [steamdating.models.round :as round]
            [steamdating.services.debug :as debug]))


(defcard-rg round-next-test.
  "Round next component"


  (fn [state]
    (let [options (round/players-options (get-in @state [:form :edit]))]
      [round-next-render
       {:on-player-update #(swap! state update-in [:form :edit] round/pair-player %1 %2)
        :on-table-update #(swap! state assoc-in (concat [:form :edit] %1) %2)
        :state (-> (:form @state)
                   (assoc :options options))}]))


  (reagent/atom
    {:form {:base {}
            :edit {:players ["Toto" "titi" "Tutu" "tete" "tyty" "toutou" "Teuteu"],
                   :games
                   [{:table 1,
                     :player1 {:name nil,
                               :score {:tournament nil, :assassination false, :scenario 0, :army 0}},
                     :player2 {:name nil,
                               :score {:tournament nil, :assassination false, :scenario 0, :army 0}}}
                    {:table 2,
                     :player1 {:name nil,
                               :score {:tournament nil, :assassination false, :scenario 0, :army 0}},
                     :player2 {:name nil,
                               :score {:tournament nil, :assassination false, :scenario 0, :army 0}}}
                    {:table 3,
                     :player1 {:name nil,
                               :score {:tournament nil, :assassination false, :scenario 0, :army 0}},
                     :player2 {:name nil,
                               :score {:tournament nil, :assassination false, :scenario 0, :army 0}}}
                    {:table 4,
                     :player1 {:name nil,
                               :score {:tournament nil, :assassination false, :scenario 0, :army 0}},
                     :player2 {:name nil,
                               :score {:tournament nil, :assassination false, :scenario 0, :army 0}}}]}
            :factions {"Toto" :Cygnar
                       "titi" :Khador
                       "Tutu" :Protectorate
                       "tete" :Legion
                       "tyty" :Khador
                       "toutou" :Legion
                       "Teuteu" :Cryx}
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
                    :Circle "circle.png"}}})


  {:inspect-data true
   :history true})
