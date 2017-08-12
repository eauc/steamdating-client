(ns steamdating.components.player.list-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [steamdating.components.filter.input :refer [render-filter-input]]
            [steamdating.components.player.list :refer [render-list]]
            [steamdating.models.player :as player]
            [steamdating.models.filter :as filter]))


(def players
  [{:name "toto"
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
    :lists ["Helynna1" "Vyros1"]}])


(defcard-rg players-list-test.
  "Players list component"
  (fn [state]
    (let [on-player-click #(println "player-click" %)
          on-filter-update #(reset! state (player/pattern
                                            players
                                            (filter/filter->regexp %2)))]
      [:div
       [render-filter-input ""
        {:name :player
         :on-update on-filter-update}]
       [render-list @state
        {:on-player-click on-player-click}]]))
  (reagent/atom
    (player/pattern
      players
      (filter/filter->regexp "")))
  {:inspect-data true
   :history true})
