(ns steamdating.models.game
  (:require [cljs.spec.alpha :as spec]))


(spec/def :steamdating.game/table
  (spec/and integer? #(> % 0)))


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


(defn ->game
  []
  {:table nil
   :player1 {:name nil}
   :player2 {:name nil}})


(defn update-factions
  [game factions]
  (-> game
      (assoc-in [:player1 :faction]
                (get factions (get-in game [:player1 :name])))
      (assoc-in [:player2 :faction]
                (get factions (get-in game [:player2 :name])))))


(defn player-paired?
  [game name]
  (or (= name (get-in game [:player1 :name]))
      (= name (get-in game [:player2 :name]))))
