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


(defn players->ngames
  [players]
  (-> (count players)
      (+ 1)
      (/ 2)
      js/Math.floor))

(defn ->round
  [players]
  (let [ngames (players->ngames players)]
    {:players (mapv :name players)
     :games (mapv #(game/->game {:table (inc %)}) (range ngames))}))


(defn paired-players
  [{:keys [games] :as round}]
  (flatten (map game/player-names games)))


(defn game-for-player
  [round name]
  (first (filter #(game/player-paired? % name) (:games round))))


(defn games-for-player
  [name rounds]
  (mapv #(game-for-player % name) rounds))


(defn opponents-for-player
  [name rounds]
  (->> rounds
       (games-for-player name)
       (map #(game/opponent-for-player % name))
       (remove nil?)))


(defn lists-for-player
  [name rounds]
  (->> rounds
       (games-for-player name)
       (map #(game/list-for-player % name))
       (remove nil?)
       (set)))


(defn results-for-player
  [name rounds]
  (->> rounds
       (games-for-player name)
       (mapv #(game/result-for-player % name))))


(defn player-paired?
  [round name]
  (some #(game/player-paired? % name) (:games round)))


(defn already-paired
  [{:keys [games]} opponents]
  (->> games
       (map game/player-names)
       (map vector (range))
       (filter (fn [[n [p1 p2]]]
                 (some #{p2} (get opponents p1 '()))))
       (into {})))


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


(defn already-paired->warns
  [already-paired]
  (into {} (map (fn [[n]] [n {:pairing :already}])
                already-paired)))


(defn already-paired->string
  [already-paired]
  (let [plural? (> (count already-paired) 1)]
    (str "Pairing" (when plural? "s") " " (s/join ", " (map #(s/join "-" %) (vals already-paired)))
         " " (if plural? "have" "has")" already been played")))


(defn unpaired-players->string
  [unpaired-players]
  (let [plural? (> (count unpaired-players) 1)]
    (str "Player" (when plural? "s") " " (s/join ", " (sort-by #(.toLowerCase %) unpaired-players))
         " " (if plural? "are" "is")" not paired")))


(defn faction-mirrors->string
  [faction-mirrors]
  (let [n-mirrors (count faction-mirrors)
        plural? (> n-mirrors 1)]
    (str "There " (if plural? "are" "is") " " n-mirrors
         " mirror game" (when plural? "s"))))


(def merge-warns
  (fnil
    (fn [base extend]
      (into base (map (fn [[k v]]
                        (if (contains? base k)
                          [k (merge (get base k) v)]
                          [k v]))
                      extend)))
    {}))


(defn validate-pairings
  [form-state {:keys [factions opponents]}]
  (let [{:keys [edit]} form-state
        already-paired (already-paired edit opponents)
        unpaired-players (unpaired-players edit)
        faction-mirrors (faction-mirrors edit factions)]
    (cond-> form-state
      (not-empty unpaired-players)
      (assoc-in [:error :global :unpaired]
                (unpaired-players->string unpaired-players))
      (not-empty faction-mirrors)
      (-> (assoc-in [:warn :global :faction]
                    (faction-mirrors->string faction-mirrors))
          (update-in [:warn :games]
                     merge-warns faction-mirrors))
      (not-empty already-paired)
      (-> (assoc-in [:error :global :pairings]
                    (already-paired->string already-paired))
          (update-in [:warn :games]
                     merge-warns (already-paired->warns already-paired))))))


(defn unpair-player
  [round name]
  (update round :games #(mapv (fn [g] (game/unpair-player g name)) %)))


(defn rename-player
  [round old-name new-name]
  (-> round
      (update :players #(vec (remove (fn [name] (= name old-name)) %)))
      (update :players conj new-name)
      (update :games #(mapv (fn [game] (game/rename-player game old-name new-name)) %))))


(defn pair-player
  [round field name]
  (-> round
      (unpair-player name)
      (assoc-in field name)))


(defn random-score
  [round lists]
  (update round :games
          #(mapv (fn [game]
                   (game/random-score game lists))
                 %)))


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
