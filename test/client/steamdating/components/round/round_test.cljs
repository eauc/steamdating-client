(ns steamdating.components.round.round-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [steamdating.components.round.round :refer [round]]))


(defcard-rg round-test
  "Round view component"
  (fn [state]
    [:div
     [round (:round @state)]])
  (reagent/atom
    {:round {:players ["Toto" "titi" "Tutu" "tete" "tyty" "toutou" "Teuteu"]
             :games [{:table 1
                      :player1 {:name "tete", :faction "Khador"}
                      :player2 {:name "Teuteu", :faction "Retribution"}}
                     {:table 2
                      :player1 {:name "titi", :faction "Protectorate"}
                      :player2 {:name "Toto", :faction "Legion"}}
                     {:table 3
                      :player1 {:name "toutou", :faction "Legion"}
                      :player2 {:name "Tutu", :faction "Mercenaries"}}
                     {:table 4
                      :player1 {:name "tyty", :faction "Protectorate"}
                      :player2 {:name nil, :faction nil}}]}})
  {:inspect-data true
   :history true})
