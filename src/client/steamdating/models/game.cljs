(ns steamdating.models.game
  (:require [cljs.spec.alpha :as spec]
            [steamdating.models.player]
            [steamdating.services.debug :as debug]))


(spec/def :steamdating.score/tournament
  (spec/nilable #{0 1}))


(spec/def :steamdating.score/assassination
  boolean?)


(spec/def :steamdating.score/scenario
  (spec/and integer? #(>= % 0)))


(spec/def :steamdating.score/army
  (spec/and integer? #(>= % 0)))


(spec/def :steamdating.game/score
  (spec/keys :req-un [:steamdating.score/tournament
                      :steamdating.score/assassination
                      :steamdating.score/scenario
                      :steamdating.score/army]))


(spec/def :steamdating.game/table
  (spec/nilable (spec/and integer? pos?)))


(spec/def :steamdating.game/name
  (spec/nilable :steamdating.player/name))


(spec/def :steamdating.game/player
  (spec/keys :req-un [:steamdating.game/name
                      :steamdating.game/score]))


(spec/def :steamdating.game/player1
  :steamdating.game/player)


(spec/def :steamdating.game/player2
  :steamdating.game/player)


(spec/def :steamdating.game/game
  (spec/keys :req-un [:steamdating.game/player1
                      :steamdating.game/player2
                      :steamdating.game/table]))


(defn ->score
  []
  {:tournament nil
   :assassination false
   :scenario 0
   :army 0})


(defn ->game
  [{:keys [table]}]
  {:table table
   :player1 {:name nil
             :score (->score)}
   :player2 {:name nil
             :score (->score)}})


(def player-names
  (juxt #(get-in % [:player1 :name])
        #(get-in % [:player2 :name])))


(defn player-paired?
  [game name]
  (or (= name (get-in game [:player1 :name]))
      (= name (get-in game [:player2 :name]))))


(defn list-for-player
  [game name]
  (cond
    (= name (get-in game [:player1 :name]))
    (get-in game [:player1 :list])
    (= name (get-in game [:player2 :name]))
    (get-in game [:player2 :list])
    :else nil))


(defn opponent-for-player
  [game name]
  (cond
    (= name (get-in game [:player1 :name]))
    (get-in game [:player2 :name])
    (= name (get-in game [:player2 :name]))
    (get-in game [:player1 :name])
    :else nil))


(defn result-for-player
  [game name]
  (cond
    (= name (get-in game [:player1 :name]))
    {:table (:table game)
     :opponent (get-in game [:player2 :name])
     :score (get-in game [:player1 :score])
     :game game}
    (= name (get-in game [:player2 :name]))
    {:table (:table game)
     :opponent (get-in game [:player1 :name])
     :score (get-in game [:player2 :score])
     :game game}
    :else nil))


(defn faction-mirror?
  [game factions]
  (let [f1 (get factions (get-in game [:player1 :name]))
        f2 (get factions (get-in game [:player2 :name]))]
    (and (some? f1)
         (= f1 f2))))


(defn match-pattern?
  [game pattern]
  (or (re-find pattern (or (get-in game [:player1 :name]) ""))
      (re-find pattern (or (get-in game [:player2 :name]) ""))))


(defn unpair-player
  [game name]
  (cond-> game
    (= name (get-in game [:player1 :name]))
    (assoc-in [:player1 :name] nil)
    (= name (get-in game [:player2 :name]))
    (assoc-in [:player2 :name] nil)))


(defn update-factions
  [game factions]
  (-> game
      (assoc-in [:player1 :faction]
                (get factions (get-in game [:player1 :name])))
      (assoc-in [:player2 :faction]
                (get factions (get-in game [:player2 :name])))))


(defn game-player->title
  [player]
  (str (or (:name player) "Phantom")
       (case (get-in player [:score :tournament])
         0 " - Loser"
         1 " - Winner"
         nil "") "\n"
       "List: " (:list player) "\n"
       (if (get-in player [:score :assassination])
         "Assassination\n"
         "")
       "Scenario: " (get-in player [:score :scenario]) "\n"
       "Army: " (get-in player [:score :army]) "\n"))


(defn game->title
  [game]
  (str "Table: " (:table game) "\n"
       "\nPlayer 1:\n" (game-player->title (:player1 game))
       "\nPlayer 2:\n" (game-player->title (:player2 game))))
