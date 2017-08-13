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
    (let [update-players #(swap! state assoc :players
                                 (player/filter-with
                                   (player/sort-with players (:sort @state))
                                   (filter/filter->regexp (:filter @state))))
          on-player-click #(println "player-click" %)
          on-filter-update #(do (swap! state assoc :filter %2)
                                (update-players))
          on-sort-by #(do (swap! state assoc :sort %)
                          (update-players))]
      [:div
       [render-filter-input ""
        {:name :player
         :on-update on-filter-update}]
       [render-list (:players @state) (:sort @state)
        {:on-player-click on-player-click
         :on-sort-by on-sort-by}]]))
  (reagent/atom
    (let [filter ""
          sort {:by :name :reverse false}]
      {:filter filter
       :sort sort
       :players (player/filter-with
                  (player/sort-with players sort)
                  (filter/filter->regexp filter))}))
  {:inspect-data true
   :history true})
