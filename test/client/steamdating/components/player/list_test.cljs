(ns steamdating.components.player.list-test
  (:require [clojure.string :as s]
            [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [steamdating.components.player.list :refer [player-list-render]]
            [steamdating.models.filter :as filter]
            [steamdating.models.player :as player]
            [steamdating.models.sort :as sort]))


(defcard-rg player-list-test
  "Players list component"


  (fn [state]
    (let [on-player-click #(println "player-click" %)
          on-filter-update #(swap! state assoc :filter %)
          on-sort-by #(swap! state update :sort sort/toggle-by %)
          {:keys [filter players sort]} @state
          list (->> players
                    (player/filter-with (filter/->pattern filter))
                    (player/sort-with sort))
          props {:on-filter-update on-filter-update
                 :on-player-click on-player-click
                 :on-sort-by on-sort-by
                 :state (assoc @state :list list)}]

      [:div {:style {:border "1px dotted black"
                     ;; :height "320px"
                     ;; :width "320px"
                     }}
       [player-list-render props]]))


  (reagent/atom
    {:columns [:name :origin :faction :lists]
     :filter ""
     :sort {:by :name
            :reverse? false}
     :players
     [{:name "Toto",
       :origin "lyon",
       :faction "Legion",
       :lists ["Absylonia1" "Bethayne1"]}
      {:name "titi",
       :origin "dijon",
       :faction "Protectorate",
       :lists ["Amon1" "Feora1"]}
      {:name "Tutu",
       :origin "Aubagne",
       :faction "Mercenaries",
       :lists ["Bartolo1" "Cyphon1"]}
      {:name "tete",
       :origin "lyon",
       :faction "Khador",
       :lists ["Butcher2" "Koslov1"]}
      {:name "tyty",
       :origin "Nantes",
       :faction "Protectorate",
       :lists ["Malekus1" "Severius1"]}
      {:name "toutou",
       :origin "Paris",
       :faction "Legion",
       :lists ["Absylonia1" "Lylyth2"]}
      {:name "Teuteu",
       :origin "paris",
       :faction "Retribution",
       :lists ["Helynna1" "Vyros1"]}]
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
