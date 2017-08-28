(ns steamdating.models.round
  (:require [cljs.spec.alpha :as spec]
            [clojure.string :as s]
            [steamdating.models.form :as form]
            [steamdating.models.game :as game]
            [steamdating.models.player]
            [steamdating.services.debug :as debug]))


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
     :games (mapv #(game/->game {:table (inc %)}) (range ngames))}))


(defn paired-players
  [{:keys [games] :as round}]
  (flatten (map game/player-names games)))


(defn player-paired?
  [round name]
  (some #(game/player-paired? % name) (:games round)))


(defn unpaired-players
  [{:keys [players] :as round}]
  (clojure.set/difference
    (set players)
    (set (paired-players round))))


(defn faction-mirrors
  [{:keys [games] :as round} factions]
  (reduce (fn [warns [i game]]
            (cond-> warns
              (game/faction-mirror? game factions)
              (assoc-in [i :faction] :mirror)))
          {} (map vector (range) games)))


(defn validate
  [form-state]
  (form/validate form-state :steamdating.round/round))


(defn unpaired-players->string
  [unpaired-players]
  (let [plural? (> (count unpaired-players) 1)]
    (str "Player" (when plural? "s") " " (s/join ", " unpaired-players)
         " " (if plural? "are" "is")" not paired")))


(defn faction-mirrors->string
  [faction-mirrors]
  (let [n-mirrors (count faction-mirrors)
        plural? (> n-mirrors 1)]
    (str "There " (if plural? "are" "is") " " n-mirrors
         " mirror game" (when plural? "s"))))


(defn validate-pairings
  [form-state factions]
  (let [{:keys [edit]} form-state
        unpaired-players (unpaired-players edit)
        faction-mirrors (faction-mirrors edit factions)]
    (cond-> form-state
      (not-empty unpaired-players)
      (assoc-in [:error :global :pairings]
                (unpaired-players->string unpaired-players))
      (not-empty faction-mirrors)
      (-> (assoc-in [:warn :global :faction]
                    (faction-mirrors->string faction-mirrors))
          (assoc-in [:warn :games] faction-mirrors)))))


(defn unpair-player
  [round name]
  (update round :games #(mapv (fn [g] (game/unpair-player g name)) %)))


(defn pair-player
  [round field name]
  (-> round
      (unpair-player name)
      (assoc-in field name)))


(defn filter-with
  [round pattern]
  (update round :games #(vec (filter (fn [game] (game/match-pattern? game pattern)) %))))


(def sort-props
  {:table :table
   :player1 #(.toLowerCase (or (get-in % [:player1 :name]) ""))
   :player2 #(.toLowerCase (or (get-in % [:player2 :name]) ""))})


(defn sort-with
  [round {:keys [by reverse]}]
  (update round :games #(-> %
                            (->> (sort-by (by sort-props)))
                            (cond-> reverse (cljs.core/reverse)))))


(defn update-factions
  [round factions]
  (update round :games
          #(mapv
             (fn [game]
               (game/update-factions game factions))
             %)))


(defn update-players-options
  [round]
  (update round :players
          #(into {} (map
                      (fn [name]
                        (vector name (if (player-paired? round name)
                                       name
                                       (str "> " name))))
                      %))))
