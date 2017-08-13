(ns steamdating.models.player-test
  (:require [clojure.test :refer [is are testing]]
            [devcards.core :as dc :refer-macros [deftest]]
            [steamdating.models.filter :as filter]
            [steamdating.models.player :as player]))


(def players
  [{:name "tata"
    :origin "Lyon"
    :faction "Cryx"}
   {:name "tete"
    :origin "Dijon"
    :faction "Cygnar"}
   {:name "titi"
    :origin "Paris"
    :faction "Khador"}
   {:name "tutu"
    :origin "Lyon"
    :faction "Khador"}])


(deftest player-model-test
  (testing "filter-with"
    (are [pattern players-list columns]
        (= {:list players-list
            :columns columns}
           (player/filter-with players (filter/filter->regexp pattern)))

      ""
      players
      [:name :origin :faction :lists]

      "toto"
      []
      [:name :origin :faction :lists]

      "tete"
      [{:name "tete" :origin "Dijon" :faction "Cygnar"}]
      [:name :origin :faction :lists]

      "tete toto"
      [{:name "tete" :origin "Dijon" :faction "Cygnar"}]
      [:name :origin :faction :lists]

      "tutu tete"
      [{:name "tete" :origin "Dijon" :faction "Cygnar"}
       {:name "tutu" :origin "Lyon" :faction "Khador"}]
      [:name :origin :faction :lists]

      "cyg"
      [{:name "tete" :origin "Dijon" :faction "Cygnar"}]
      [:name :faction :origin :lists]

      "cyg toto"
      [{:name "tete" :origin "Dijon" :faction "Cygnar"}]
      [:name :faction :origin :lists]

      "Khad Cyg"
      [{:name "tete" :origin "Dijon" :faction "Cygnar"}
       {:name "titi" :origin "Paris" :faction "Khador"}
       {:name "tutu" :origin "Lyon" :faction "Khador"}]
      [:name :faction :origin :lists]

      "dij cyg"
      [{:name "tete" :origin "Dijon" :faction "Cygnar"}]
      [:name :origin :faction :lists]

      "kha dij"
      [{:name "tete" :origin "Dijon" :faction "Cygnar"}
       {:name "titi" :origin "Paris" :faction "Khador"}
       {:name "tutu" :origin "Lyon" :faction "Khador"}]
      [:name :origin :faction :lists]))


  (testing "sort-with"
    (are [players sort sorted-players]
        (= sorted-players
           (player/sort-with players sort))

      []
      {:by :name
       :reverse false}
      []

      ;; simple sort : by name
      [{:name "toto"}
       {:name "tata"}
       {:name "tutu"}]
      {:by :name :reverse false}
      [{:name "tata"}
       {:name "toto"}
       {:name "tutu"}]

      [{:name "toto"}
       {:name "tata"}
       {:name "tutu"}]
      {:by :name :reverse true}
      [{:name "tutu"}
       {:name "toto"}
       {:name "tata"}]

      ;; sort by another prop
      [{:name "toto" :origin "Lyon"}
       {:name "tata" :origin "Paris"}
       {:name "tutu" :origin "Dijon"}]
      {:by :origin :reverse false}
      [{:name "tutu" :origin "Dijon"}
       {:name "toto" :origin "Lyon"}
       {:name "tata" :origin "Paris"}]

      [{:name "toto" :origin "Lyon"}
       {:name "tata" :origin "Paris"}
       {:name "tutu" :origin "Dijon"}]
      {:by :origin :reverse true}
      [{:name "tata" :origin "Paris"}
       {:name "toto" :origin "Lyon"}
       {:name "tutu" :origin "Dijon"}]

      ;; resolve equalities using name
      [{:name "toto" :origin "Lyon"}
       {:name "titi" :origin "Lyon"}
       {:name "tata" :origin "Paris"}
       {:name "tutu" :origin "Dijon"}]
      {:by :origin :reverse true}
      [{:name "tata" :origin "Paris"}
       {:name "toto" :origin "Lyon"}
       {:name "titi" :origin "Lyon"}
       {:name "tutu" :origin "Dijon"}]

      [{:name "toto" :origin "Lyon"}
       {:name "titi" :origin "Lyon"}
       {:name "tata" :origin "Paris"}
       {:name "tutu" :origin "Dijon"}]
      {:by :origin :reverse false}
      [{:name "tutu" :origin "Dijon"}
       {:name "titi" :origin "Lyon"}
       {:name "toto" :origin "Lyon"}
       {:name "tata" :origin "Paris"}])))
