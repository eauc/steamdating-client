(ns steamdating.models.round
  (:require [cljs.spec.alpha :as spec]
            [steamdating.models.form :as form]
            [steamdating.models.player]))


(spec/def :steamdating.game/table
  (spec/and integer? #(> % 1)))


(spec/def :steamdating.game/player
  (spec/keys :req-un [:steamdating.game/name]))


(spec/def :steamdating.game/player1
  :steamdating.game/player)


(spec/def :steamdating.game/player2
  :steamdating.game/player)


(spec/def :steamdating.game/game
  (spec/keys :req-un [:steamdating.game/player1
                      :steamdating.game/player2
                      :steamdating.game/table]))


(spec/def :steamdating.round/games
  (spec/coll-of :steamdating.game/game :kind vector?))


(spec/def :steamdating.round/players
  (spec/coll-of :steamdating.player/name :kind vector?))


(spec/def :steamdating.round/round
  (spec/keys :req-un [:steamdating.round/players
                      :steamdating.round/games]))


(defn ->game
  []
  {:table nil
   :player1 {:name nil}
   :player2 {:name nil}})


(defn ->round
  [players]
  (let [ngames (-> (count players)
                   (/ 2)
                   js/Math.floor
                   (+ 1))]
    {:players (mapv :name players)
     :games (vec (repeat ngames (->game)))}))


(defn validate
  [form-state]
  (form/validate form-state :steamdating.round/round))
