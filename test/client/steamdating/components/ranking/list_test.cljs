(ns steamdating.components.ranking.list-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [steamdating.components.ranking.list :refer [ranking-list-render]]
            [steamdating.models.filter :refer [->pattern]]
            [steamdating.models.ranking :as ranking]
            [steamdating.models.round :as round]
            [steamdating.models.sort :as sort]
            [steamdating.services.debug :as debug]))


(defcard-rg ranking-list-test
  "Ranking list component"


  (fn [state]
    (let [on-filter-update #(swap! state assoc  :filter %)
          on-player-click #(println "player-click" %)
          on-player-drop #(println "player-drop" %)
          on-sort-by #(swap! state update :sort sort/toggle-by %)

          {:keys [filter icons players rounds sort]} @state
          names (mapv :name players)
          scores (round/total-scores-for-players names rounds)
          rankings (->> (ranking/->ranking scores players)
                        (ranking/filter-with (->pattern filter))
                        (ranking/sort-with sort))]

      [ranking-list-render {:on-filter-update on-filter-update
                            :on-player-click on-player-click
                            :on-player-drop on-player-drop
                            :on-sort-by on-sort-by
                            :state (assoc @state :rankings rankings)}]))


  (reagent/atom
    {:filter ""
     :sort {:by [:rank]
            :reverse false}
     :players [{:name "toto",
                :origin "lyon",
                :faction "Legion",
                :lists ["Absylonia1" "Bethayne1"]}
               {:name "titi",
                :origin "dijon",
                :faction "Protectorate",
                :lists ["Amon1" "Feora1"]}
               {:name "tutu",
                :origin "aubagne",
                :faction "Mercenaries",
                :lists ["Bartolo1" "Cyphon1"]}
               {:name "tete",
                :origin "lyon",
                :faction "Khador",
                :lists ["Butcher2" "Koslov1"]}
               {:name "tyty",
                :origin "nantes",
                :faction "Protectorate",
                :lists ["Malekus1" "Severius1"]}
               {:name "toutou",
                :origin "paris",
                :faction "Legion",
                :lists ["Absylonia1" "Lylyth2"]}
               {:name "teuteu",
                :origin "paris",
                :faction "Retribution",
                :lists ["Helynna1" "Vyros1"]}]
     :rounds [{:players ["toto" "titi" "tutu" "tete" "tyty" "toutou" "teuteu"],
               :games [{:table 1,
                        :player1 {:name "tete",:list "Butcher2",
                                  :score {:tournament 1,:assassination false,:scenario 5,:army 52,:custom 0}},
                        :player2 {:name "teuteu",:list "Vyros1",
                                  :score {:tournament 0,:assassination false,:scenario 3,:army 21,:custom 0}}}
                       {:table 2,
                        :player1 {:name "titi",:list "Amon1",
                                  :score {:tournament 0,:assassination false,:scenario 3,:army 32,:custom 0}},
                        :player2 {:name "toto",:list "Bethayne1",
                                  :score {:tournament 1,:assassination true,:scenario 5,:army 75,:custom 0}}}
                       {:table 3,
                        :player1 {:name "toutou",:list "Lylyth2",
                                  :score {:tournament 0,:assassination true,:scenario 0,:army 46,:custom 0}},
                        :player2 {:name "tutu",:list "Bartolo1",
                                  :score {:tournament 0,:assassination false,:scenario 4,:army 30,:custom 0}}}
                       {:table 4,
                        :player1 {:name "tyty",:list nil,
                                  :score {:tournament 1,:assassination false,:scenario 0,:army 0,:custom 0}},
                        :player2 {:name nil,:list nil,
                                  :score {:tournament 0,:assassination false,:scenario 0,:army 0,:custom 0}}}]}
              {:players ["toto" "titi" "tutu" "tete" "tyty" "toutou" "teuteu"],
               :games [{:table 4,
                        :player1 {:name nil,:list nil,
                                  :score {:tournament 0,:assassination false,:scenario 0,:army 0,:custom 0}},
                        :player2 {:name "tete",:list "Koslov1",
                                  :score {:tournament 1,:assassination false,:scenario 0,:army 0,:custom 0}}}
                       {:table 1,
                        :player1 {:name "teuteu",:list "Helynna1",
                                  :score {:tournament 1,:assassination true,:scenario 4,:army 105,:custom 0}},
                        :player2 {:name "titi",:list "Feora1",
                                  :score {:tournament 0,:assassination false,:scenario 3,:army 10,:custom 0}}}
                       {:table 3,
                        :player1 {:name "toto",:list "Absylonia1",
                                  :score {:tournament 1,:assassination false,:scenario 5,:army 45,:custom 0}},
                        :player2 {:name "toutou",:list "Lylyth2",
                                  :score {:tournament 0,:assassination false,:scenario 0,:army 75,:custom 0}}}
                       {:table 2,
                        :player1 {:name "tutu",:list "Cyphon1",
                                  :score {:tournament 0,:assassination false,:scenario 4,:army 56,:custom 0}},
                        :player2 {:name "tyty",:list "Malekus1",
                                  :score {:tournament 1,:assassination true,:scenario 0,:army 103,:custom 0}}}]}
              {:players ["toto" "titi" "tutu" "tete" "tyty" "toutou" "teuteu"],
               :games [{:table 1,
                        :player1 {:name "titi",:list "Feora1",
                                  :score {:tournament 1, :assassination true, :scenario 4, :army 205}},
                        :player2 {:name "toutou",
                                  :score {:tournament 0, :assassination true, :scenario 10, :army 246},
                                  :list "Absylonia1"}}
                       {:table 2,
                        :player1 {:name "tutu",
                                  :score {:tournament 1, :assassination true, :scenario 2, :army 359},
                                  :list "Cyphon1"},
                        :player2 {:name nil,
                                  :score {:tournament 0, :assassination false, :scenario 0, :army 148},
                                  :list nil}}
                       {:table 3,
                        :player1 {:name "tete",
                                  :score {:tournament 0, :assassination false, :scenario 15, :army 347},
                                  :list "Koslov1"},
                        :player2 {:name "tyty",
                                  :score {:tournament 1, :assassination false, :scenario 17, :army 144},
                                  :list "Malekus1"}}
                       {:table 4,
                        :player1 {:name "toto",
                                  :score {:tournament 0, :assassination true, :scenario 15, :army 465},
                                  :list "Bethayne1"},
                        :player2 {:name "teuteu",
                                  :score {:tournament 1, :assassination  false, :scenario 7, :army 372},
                                  :list "Vyros1"}}]}
              {:players ["toto" "titi" "tutu" "tete" "tyty" "toutou" "teuteu"],
               :games [{:table 1,
                        :player1 {:name "tyty",
                                  :score {:tournament 1, :assassination true, :scenario 20, :army 3},
                                  :list "Malekus1"},
                        :player2 {:name "toto",
                                  :score {:tournament 0, :assassination false, :scenario 16, :army 287},
                                  :list "Bethayne1"}}
                       {:table 2,
                        :player1 {:name "tete",
                                  :score {:tournament 0, :assassination true, :scenario 18, :army 217},
                                  :list "Koslov1"},
                        :player2 {:name "titi",
                                  :score {:tournament 0, :assassination false, :scenario 2, :army 85},
                                  :list "Feora1"}}
                       {:table 3,
                        :player1 {:name "teuteu",
                                  :score {:tournament 0, :assassination false, :scenario 11, :army 295},
                                  :list "Vyros1"},
                        :player2 {:name "tutu",
                                  :score {:tournament 0, :assassination true, :scenario 16, :army 254},
                                  :list "Bartolo1"}}
                       {:table 4,
                        :player1 {:name "toutou",
                                  :score {:tournament 0, :assassination true, :scenario 9, :army 272},
                                  :list "Absylonia1"},
                        :player2 {:name nil,
                                  :score {:tournament 1, :assassination false, :scenario 13, :army 46},
                                  :list nil}}]}]
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
