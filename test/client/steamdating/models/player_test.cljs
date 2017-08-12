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
  (testing "pattern"
    (are [pattern players-list columns]
        (= {:list players-list
            :columns columns}
           (player/pattern players (filter/filter->regexp pattern)))

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
      [:name :origin :faction :lists])))
