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
    (let [examples [{:desc "no filter"
                     :pattern ""
                     :list players
                     :columns [:name :origin :faction :lists]}
                    {:desc "simple name"
                     :pattern "toto"
                     :list []
                     :columns [:name :origin :faction :lists]}
                    {:pattern "tete"
                     :list [{:name "tete" :origin "Dijon" :faction "Cygnar"}]
                     :columns [:name :origin :faction :lists]}
                    {:desc "OR names"
                     :pattern "tete toto"
                     :list [{:name "tete" :origin "Dijon" :faction "Cygnar"}]
                     :columns [:name :origin :faction :lists]}
                    {:pattern "tutu tete"
                     :list [{:name "tete" :origin "Dijon" :faction "Cygnar"}
                            {:name "tutu" :origin "Lyon" :faction "Khador"}]
                     :columns [:name :origin :faction :lists]}
                    {:desc "simple other"
                     :pattern "cyg"
                     :list [{:name "tete" :origin "Dijon" :faction "Cygnar"}]
                     :columns [:name :faction :origin :lists]}
                    {:desc "name&other same match"
                     :pattern "cyg toto"
                     :list [{:name "tete" :origin "Dijon" :faction "Cygnar"}]
                     :columns [:name :faction :origin :lists]}
                    {:desc "OR others"
                     :pattern "Khad Cyg"
                     :list [{:name "tete" :origin "Dijon" :faction "Cygnar"}
                            {:name "titi" :origin "Paris" :faction "Khador"}
                            {:name "tutu" :origin "Lyon" :faction "Khador"}]
                     :columns [:name :faction :origin :lists]}
                    {:desc "mix others"
                     :pattern "dij cyg"
                     :list [{:name "tete" :origin "Dijon" :faction "Cygnar"}]
                     :columns [:name :origin :faction :lists]}
                    {:pattern "kha dij"
                     :list [{:name "tete" :origin "Dijon" :faction "Cygnar"}
                            {:name "titi" :origin "Paris" :faction "Khador"}
                            {:name "tutu" :origin "Lyon" :faction "Khador"}]
                     :columns [:name :origin :faction :lists]}]]
      (doall
        (for [{:keys [desc pattern list columns]} examples]
          (is (= {:list list
                  :columns columns}
                 (player/filter-with players (filter/filter->regexp pattern)))
              desc)))))


  (testing "sort-with"
    (let [examples [{:players []
                     :sort {:by :name
                            :reverse false}
                     :result []}
                    {:desc "simple sort : by name"
                     :players [{:name "toto"}
                               {:name "tata"}
                               {:name "tutu"}]
                     :sort {:by :name :reverse false}
                     :result [{:name "tata"}
                              {:name "toto"}
                              {:name "tutu"}]}
                    {:players [{:name "toto"}
                               {:name "tata"}
                               {:name "tutu"}]
                     :sort {:by :name :reverse true}
                     :result [{:name "tutu"}
                              {:name "toto"}
                              {:name "tata"}]}
                    {:desc "sort by another prop"
                     :players [{:name "toto" :origin "Lyon"}
                               {:name "tata" :origin "Paris"}
                               {:name "tutu" :origin "Dijon"}]
                     :sort {:by :origin :reverse false}
                     :result [{:name "tutu" :origin "Dijon"}
                              {:name "toto" :origin "Lyon"}
                              {:name "tata" :origin "Paris"}]}
                    {:players [{:name "toto" :origin "Lyon"}
                               {:name "tata" :origin "Paris"}
                               {:name "tutu" :origin "Dijon"}]
                     :sort {:by :origin :reverse true}
                     :result [{:name "tata" :origin "Paris"}
                              {:name "toto" :origin "Lyon"}
                              {:name "tutu" :origin "Dijon"}]}
                    {:desc "resolve equalities using name"
                     :players [{:name "toto" :origin "Lyon"}
                               {:name "titi" :origin "Lyon"}
                               {:name "tata" :origin "Paris"}
                               {:name "tutu" :origin "Dijon"}]
                     :sort {:by :origin :reverse true}
                     :result [{:name "tata" :origin "Paris"}
                              {:name "toto" :origin "Lyon"}
                              {:name "titi" :origin "Lyon"}
                              {:name "tutu" :origin "Dijon"}]}
                    {:players [{:name "toto" :origin "Lyon"}
                               {:name "titi" :origin "Lyon"}
                               {:name "tata" :origin "Paris"}
                               {:name "tutu" :origin "Dijon"}]
                     :sort {:by :origin :reverse false}
                     :result [{:name "tutu" :origin "Dijon"}
                              {:name "titi" :origin "Lyon"}
                              {:name "toto" :origin "Lyon"}
                              {:name "tata" :origin "Paris"}]}]]
      (doall
        (for [{:keys [desc players sort result]} examples]
          (is (= result
                 (player/sort-with players sort))
              desc))))))
