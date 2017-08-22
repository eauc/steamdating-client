(ns steamdating.models.round
  (:require [cljs.spec.alpha :as spec]
            [steamdating.models.form :as form]
            [steamdating.models.game :as game]
            [steamdating.models.player]))


(spec/def :steamdating.round/games
  (spec/coll-of :steamdating.game/game :kind vector?))


(spec/def :steamdating.round/players
  (spec/coll-of :steamdating.player/name :kind vector?))


(spec/def :steamdating.round/round
  (spec/keys :req-un [:steamdating.round/players
                      :steamdating.round/games]))


(spec/def :steamdating.round/rounds
  (spec/coll-of :steamdating.round/round :kind vector?))


(defn ->round
  [players]
  (let [ngames (-> (count players)
                   (/ 2)
                   js/Math.floor
                   (+ 1))]
    {:players (mapv :name players)
     :games (vec (repeat ngames (game/->game)))}))


(defn validate
  [form-state]
  (form/validate form-state :steamdating.round/round))


(defn update-factions
  [round factions]
  (update round :games
          #(mapv
             (fn [game]
               (game/update-factions game factions))
             %)))


(defn player-paired?
  [round name]
  (some #(game/player-paired? % name) (:games round)))


(defn update-players-options
  [round]
  (update round :players
          #(into {} (map
                      (fn [name]
                        (vector name (if (player-paired? round name)
                                       name
                                       (str "> " name))))
                      %))))
