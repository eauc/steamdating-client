(ns steamdating.components.round.summary-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [steamdating.components.round.summary :refer [summary]]))


(def results
  '({:name "tete", :origin "lyon", :faction "Khador", :lists ["Butcher2" "Koslov1"],
     :results [{:table 1, :opponent "teuteu", :score {:tournament 1, :assassination false, :scenario 5, :army 52, :custom 0},
                :game {:table 1,
                       :player1 {:name "tete", :list "Butcher2",
                                 :score {:tournament 1, :assassination false, :scenario 5, :army 52, :custom 0}},
                       :player2 {:name "teuteu", :list "Vyros1",
                                 :score {:tournament 0, :assassination false, :scenario 3, :army 21, :custom 0}}}}
               {:table 4, :opponent nil, :score {:tournament 1, :assassination false, :scenario 0, :army 0, :custom 0},
                :game {:table 4,
                       :player1 {:name nil, :list nil,
                                 :score {:tournament 0, :assassination false, :scenario 0, :army 0, :custom 0}},
                       :player2 {:name "tete", :list "Koslov1",
                                 :score {:tournament 1, :assassination false, :scenario 0, :army 0, :custom 0}}}}],
     :played-lists #{"Butcher2" "Koslov1"}}
    {:name "teuteu", :origin "paris", :faction "Retribution", :lists ["Helynna1" "Vyros1"],
     :results [{:table 1, :opponent "tete", :score {:tournament 0, :assassination false, :scenario 3, :army 21, :custom 0},
                :game {:table 1,
                       :player1 {:name "tete", :list "Butcher2",
                                 :score {:tournament 1, :assassination false, :scenario 5, :army 52, :custom 0}},
                       :player2 {:name "teuteu", :list "Vyros1",
                                 :score {:tournament 0, :assassination false, :scenario 3, :army 21, :custom 0}}}}
               {:table 1, :opponent "titi", :score {:tournament 1, :assassination true, :scenario 4, :army 105, :custom 0},
                :game {:table 1,
                       :player1 {:name "teuteu", :list "Helynna1",
                                 :score {:tournament 1, :assassination true, :scenario 4, :army 105, :custom 0}},
                       :player2 {:name "titi", :list "Feora1",
                                 :score {:tournament 0, :assassination false, :scenario 3, :army 10, :custom 0}}}}],
     :played-lists #{"Vyros1" "Helynna1"}}
    {:name "titi", :origin "dijon", :faction "Protectorate", :lists ["Amon1" "Feora1"],
     :results [{:table 2, :opponent "toto", :score {:tournament 0, :assassination false, :scenario 3, :army 32, :custom 0},
                :game {:table 2,
                       :player1 {:name "titi", :list "Amon1",
                                 :score {:tournament 0, :assassination false, :scenario 3, :army 32, :custom 0}},
                       :player2 {:name "toto", :list "Bethayne1",
                                 :score {:tournament 1, :assassination true, :scenario 5, :army 75, :custom 0}}}}
               {:table 1, :opponent "teuteu", :score {:tournament 0, :assassination false, :scenario 3, :army 10, :custom 0},
                :game {:table 1,
                       :player1 {:name "teuteu", :list "Helynna1",
                                 :score {:tournament 1, :assassination true, :scenario 4, :army 105, :custom 0}},
                       :player2 {:name "titi", :list "Feora1",
                                 :score {:tournament 0, :assassination false, :scenario 3, :army 10, :custom 0}}}}],
     :played-lists #{"Amon1" "Feora1"}}
    {:name "toto", :origin "lyon", :faction "Legion", :lists ["Absylonia1" "Bethayne1"],
     :results [{:table 2, :opponent "titi", :score {:tournament 1, :assassination true, :scenario 5, :army 75, :custom 0},
                :game {:table 2,
                       :player1 {:name "titi", :list "Amon1",
                                 :score {:tournament 0, :assassination false, :scenario 3, :army 32, :custom 0}},
                       :player2 {:name "toto", :list "Bethayne1",
                                 :score {:tournament 1, :assassination true, :scenario 5, :army 75, :custom 0}}}}
               {:table 3, :opponent "toutou", :score {:tournament 1, :assassination false, :scenario 5, :army 45, :custom 0},
                :game {:table 3,
                       :player1 {:name "toto", :list "Absylonia1",
                                 :score {:tournament 1, :assassination false, :scenario 5, :army 45, :custom 0}},
                       :player2 {:name "toutou", :list "Lylyth2",
                                 :score {:tournament 0, :assassination false, :scenario 0, :army 75, :custom 0}}}}],
     :played-lists #{"Bethayne1" "Absylonia1"}}
    {:name "toutou", :origin "paris", :faction "Legion", :lists ["Absylonia1" "Lylyth2"],
     :results [{:table 3, :opponent "tutu", :score {:tournament 0, :assassination true, :scenario 0, :army 46, :custom 0},
                :game {:table 3,
                       :player1 {:name "toutou", :list "Lylyth2",
                                 :score {:tournament 0, :assassination true, :scenario 0, :army 46, :custom 0}},
                       :player2 {:name "tutu", :list "Bartolo1",
                                 :score {:tournament 0, :assassination false, :scenario 4, :army 30, :custom 0}}}}
               {:table 3, :opponent "toto", :score {:tournament 0, :assassination false, :scenario 0, :army 75, :custom 0},
                :game {:table 3,
                       :player1 {:name "toto", :list "Absylonia1",
                                 :score {:tournament 1, :assassination false, :scenario 5, :army 45, :custom 0}},
                       :player2 {:name "toutou", :list "Lylyth2",
                                 :score {:tournament 0, :assassination false, :scenario 0, :army 75, :custom 0}}}}],
     :played-lists #{"Lylyth2"}}
    {:name "tutu", :origin "aubagne", :faction "Mercenaries", :lists ["Bartolo1" "Cyphon1"],
     :results [{:table 3, :opponent "toutou", :score {:tournament 0, :assassination false, :scenario 4, :army 30, :custom 0},
                :game {:table 3,
                       :player1 {:name "toutou", :list "Lylyth2",
                                 :score {:tournament 0, :assassination true, :scenario 0, :army 46, :custom 0}},
                       :player2 {:name "tutu", :list "Bartolo1",
                                 :score {:tournament 0, :assassination false, :scenario 4, :army 30, :custom 0}}}}
               {:table 2, :opponent "tyty",:score {:tournament 0, :assassination false, :scenario 4, :army 56, :custom 0},
                :game {:table 2,
                       :player1 {:name "tutu", :list "Cyphon1",
                                 :score {:tournament 0, :assassination false, :scenario 4, :army 56, :custom 0}},
                       :player2 {:name "tyty", :list "Malekus1",
                                 :score {:tournament 1, :assassination true, :scenario 0, :army 103, :custom 0}}}}],
     :played-lists #{"Bartolo1" "Cyphon1"}}
    {:name "tyty", :origin "nantes", :faction "Protectorate", :lists ["Malekus1" "Severius1"],
     :results [{:table 4, :opponent nil, :score {:tournament 1, :assassination false, :scenario 0, :army 0, :custom 0},
                :game {:table 4,
                       :player1 {:name "tyty", :list nil,
                                 :score {:tournament 1, :assassination false, :scenario 0, :army 0, :custom 0}},
                       :player2 {:name nil, :list nil,
                                 :score {:tournament 0, :assassination false, :scenario 0, :army 0, :custom 0}}}}
               {:table 2, :opponent "tutu", :score {:tournament 1, :assassination true, :scenario 0, :army 103, :custom 0},
                :game {:table 2,
                       :player1 {:name "tutu", :list "Cyphon1",
                                 :score {:tournament 0, :assassination false, :scenario 4, :army 56, :custom 0}},
                       :player2 {:name "tyty", :list "Malekus1",
                                 :score {:tournament 1, :assassination true, :scenario 0, :army 103, :custom 0}}}}],
     :played-lists #{"Malekus1"}}))


(defcard-rg round-summary-test
  "Rounds summary component"
  (fn [state]
    [:div
     [summary (:results @state)
      {:n-rounds 2
       :sort (:sort @state)
       :on-sort-by #(println "sort by!" %)
       :on-player-click #(println "player click!" %)}]])
  (reagent/atom {:sort {:by :player
                        :reverse false}
                 :results results})
  {:inspect-data true
   :history true})
