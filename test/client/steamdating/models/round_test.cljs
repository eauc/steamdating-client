(ns steamdating.models.round-test
  (:require [clojure.test :refer [is are testing]]
            [devcards.core :as dc :refer-macros [deftest]]
            [steamdating.models.round :as round]))


(deftest round-model-test
  (testing "update-factions"
    (is (= {:players ["Toto" "titi" "Tutu" "tete" "tyty" "toutou" "Teuteu"]
            :games [{:table 1
                     :player1 {:name "tete" :faction "Khador"}
                     :player2 {:name "Teuteu" :faction "Retribution"}}
                    {:table 2
                     :player1 {:name "titi" :faction "Protectorate"}
                     :player2 {:name "Toto" :faction "Legion"}}
                    {:table 3
                     :player1 {:name "toutou" :faction "Legion"}
                     :player2 {:name "Tutu" :faction "Mercenaries"}}
                    {:table 4
                     :player1 {:name "tyty" :faction "Protectorate"}
                     :player2 {:name nil :faction nil}}]}
           (round/update-factions
             {:players ["Toto" "titi" "Tutu" "tete" "tyty" "toutou" "Teuteu"]
              :games [{:table 1
                       :player1 {:name "tete"}
                       :player2 {:name "Teuteu"}}
                      {:table 2
                       :player1 {:name "titi"}
                       :player2 {:name "Toto"}}
                      {:table 3
                       :player1 {:name "toutou"}
                       :player2 {:name "Tutu"}}
                      {:table 4
                       :player1 {:name "tyty"}
                       :player2 {:name nil}}]}
             {"tete" "Khador"
              "Teuteu" "Retribution"
              "titi" "Protectorate"
              "Toto" "Legion"
              "toutou" "Legion"
              "Tutu" "Mercenaries"
              "tyty" "Protectorate"}))
        "Add players factions to games"))

  (testing "update-players-options"
    (is (= {:players {"Toto" "Toto"
                      "titi" "> titi"
                      "Tutu" "> Tutu"
                      "tete" "tete"
                      "tyty" "tyty"
                      "toutou" "toutou"
                      "Teuteu" "> Teuteu"}
            :games [{:table 1
                     :player1 {:name "tete"}
                     :player2 {:name nil}}
                    {:table 2
                     :player1 {:name nil}
                     :player2 {:name "Toto"}}
                    {:table 3
                     :player1 {:name "toutou"}
                     :player2 {:name nil}}
                    {:table 4
                     :player1 {:name "tyty"}
                     :player2 {:name nil}}]}
           (round/update-players-options
             {:players ["Toto" "titi" "Tutu" "tete" "tyty" "toutou" "Teuteu"]
              :games [{:table 1
                       :player1 {:name "tete"}
                       :player2 {:name nil}}
                      {:table 2
                       :player1 {:name nil}
                       :player2 {:name "Toto"}}
                      {:table 3
                       :player1 {:name "toutou"}
                       :player2 {:name nil}}
                      {:table 4
                       :player1 {:name "tyty"}
                       :player2 {:name nil}}]}))
        "Create options object for round edit's selects")))
