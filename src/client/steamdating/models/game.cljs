(ns steamdating.models.game
  (:require [cljs.spec.alpha :as spec]
            [steamdating.models.form :as form]
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


(defn find-by-names
  [names games]
  (first (filter #(= names (player-names %)) games)))


(defn drop-by-names
  [names games]
  (vec (remove (fn [game] (= names (player-names game))) games)))


(defn update-by-names
  [names game games]
  (as-> games $
    (drop-by-names names $)
    (conj $ game)))


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


(defn rename-player
  [game old-name new-name]
  (cond-> game
    (= (get-in game [:player1 :name]) old-name)
    (assoc-in [:player1 :name] new-name)
    (= (get-in game [:player2 :name]) old-name)
    (assoc-in [:player2 :name] new-name)))


(defn toggle-win-loss
  [game p-key]
  (let [other (if (= :player1 p-key) :player2 :player1)]
    (if (= 1 (get-in game [p-key :score :tournament]))
      (-> game
          (assoc-in [p-key :score :tournament] 0))
      (-> game
          (assoc-in [p-key :score :tournament] 1)
          (assoc-in [other :score :tournament] 0)))))


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


(defn validate
  [form-state]
  (form/validate form-state :steamdating.game/game))
