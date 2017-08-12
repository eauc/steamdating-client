(ns steamdating.components.player.list-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [steamdating.components.player.list :refer [render-list]]))


(defcard-rg players-list-test
  "Players list component"
  (fn [state]
    (let [on-player-click #(println "player-click" %)]
      [render-list (:players @state)
       {:on-player-click on-player-click}]))
  (reagent/atom {:players [{:name "toto"
                            :origin "lyon"
                            :faction "Legion"
                            :lists ["Absylonia1" "Bethayne1"]}
                           {:name "titi"
                            :origin "dijon"
                            :faction "Protectorate"
                            :lists ["Amon1" "Feora1"]}
                           {:name "tutu"
                            :origin "aubagne"
                            :faction "Mercenaries"
                            :lists ["Bartolo1" "Cyphon1"]}
                           {:name "tete"
                            :origin "lyon"
                            :faction "Khador"
                            :lists ["Butcher2" "Koslov1"]}
                           {:name "tyty"
                            :origin "nantes"
                            :faction "Protectorate"
                            :lists ["Malekus1" "Severius1"]}
                           {:name "toutou"
                            :origin "paris"
                            :faction "Legion"
                            :lists ["Absylonia1" "Lylyth2"]}
                           {:name "teuteu"
                            :origin "paris"
                            :faction "Scyrah"
                            :lists ["Helynna1" "Vyros1"]}]})
  {:inspect-data true
   :history true})
