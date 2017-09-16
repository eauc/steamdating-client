(ns steamdating.components.ranking.list-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [steamdating.components.filter.input :refer [render-filter-input]]
            [steamdating.components.ranking.list :refer [ranking-list]]
            [steamdating.models.ranking :as ranking]
            [steamdating.models.filter :as filter-model]))


(defcard-rg ranking-list-test
  "## Ranking list"


  (fn [state]
    (let [{:keys [filter ranking bests sort]} @state
          list (->> ranking
                    (ranking/filter-with (filter-model/filter->regexp filter))
                    (ranking/sort-with sort))]
      [:div
       [render-filter-input filter
        {:on-update #(swap! state assoc :filter %2)
         :name :ranking}]
       [ranking-list {:on-player-edit #(println "player edit!" %)
                      :on-sort-by #(swap! state assoc :sort %)
                      :on-toggle-drop-player #(println "player drop!" %)}
        list bests sort]]))


  (reagent/atom {:ranking
                 [{:name "tyty",
                   :origin "nantes",
                   :faction "Protectorate",
                   :lists ["Malekus1" "Severius1"],
                   :droped-after 1
                   :score
                   {:tournament 4,
                    :assassination 2,
                    :scenario 37,
                    :army 250,
                    :custom 0,
                    :opponents #{"tutu" "tete" "toto"},
                    :sos 5},
                   :rank 1}
                  {:name "toto",
                   :origin "lyon",
                   :faction "Legion",
                   :lists ["Absylonia1" "Bethayne1"],
                   :score
                   {:tournament 2,
                    :assassination 2,
                    :scenario 41,
                    :army 872,
                    :custom 0,
                    :opponents #{"titi" "toutou" "teuteu" "tyty"},
                    :sos 7},
                   :rank 2}
                  {:name "tete",
                   :origin "lyon",
                   :faction "Khador",
                   :lists ["Butcher2" "Koslov1"],
                   :droped-after 4
                   :score
                   {:tournament 2,
                    :assassination 1,
                    :scenario 38,
                    :army 616,
                    :custom 0,
                    :opponents #{"teuteu" "tyty" "titi"},
                    :sos 7},
                   :rank 3}
                  {:name "teuteu",
                   :origin "paris",
                   :faction "Retribution",
                   :lists ["Helynna1" "Vyros1"],
                   :score
                   {:tournament 2,
                    :assassination 1,
                    :scenario 25,
                    :army 793,
                    :custom 0,
                    :opponents #{"tete" "titi" "toto" "tutu"},
                    :sos 6},
                   :rank 4}
                  {:name "tutu",
                   :origin "aubagne",
                   :faction "Mercenaries",
                   :lists ["Bartolo1" "Cyphon1"],
                   :score
                   {:tournament 1,
                    :assassination 2,
                    :scenario 26,
                    :army 699,
                    :custom 0,
                    :opponents #{"toutou" "tyty" "teuteu"},
                    :sos 6},
                   :rank 5}
                  {:name "titi",
                   :origin "dijon",
                   :faction "Protectorate",
                   :lists ["Amon1" "Feora1"],
                   :droped-after 2
                   :score
                   {:tournament 1,
                    :assassination 1,
                    :scenario 12,
                    :army 332,
                    :custom 0,
                    :opponents #{"toto" "teuteu" "toutou" "tete"},
                    :sos 6},
                   :rank 6}
                  {:name "toutou",
                   :origin "paris",
                   :faction "Legion",
                   :lists ["Absylonia1" "Lylyth2"],
                   :score
                   {:tournament 0,
                    :assassination 3,
                    :scenario 19,
                    :army 639,
                    :custom 0,
                    :opponents #{"tutu" "toto" "titi"},
                    :sos 4},
                   :rank 7}],
                 :bests
                 {:faction
                  {"Protectorate" {:name "tyty", :rank 1},
                   "Legion" {:name "toto", :rank 2},
                   "Khador" {:name "tete", :rank 3},
                   "Retribution" {:name "teuteu", :rank 4},
                   "Mercenaries" {:name "tutu", :rank 5}},
                  :sos {:value 7, :names ["toto" "tete"]},
                  :scenario {:value 41, :names ["toto"]},
                  :army {:value 872, :names ["toto"]},
                  :assassination {:value 3, :names ["toutou"]}},
                 :filter ""
                 :sort {:by [:rank]}})


  {:inspect-data true})
