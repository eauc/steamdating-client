(ns steamdating.models.round
  (:require [cljs.spec.alpha :as spec]
            [clojure.set :as set]
            [clojure.string :as s]
            [steamdating.models.faction]
            [steamdating.models.filter]
            [steamdating.models.form :as form]
            [steamdating.models.game :as game]
            [steamdating.models.sort]
            [steamdating.models.player]
            [steamdating.services.debug :as debug]))


(spec/def :sd.round/games
  (spec/coll-of :sd.game/game :kind vector?))


(spec/def :sd.round/players
  (spec/coll-of :sd.player/name :kind vector?))


(spec/def :sd.round/round
  (spec/keys :req-un [:sd.round/players
                      :sd.round/games]))


(spec/def :sd.round/rounds
  (spec/coll-of :sd.round/round :kind vector?))


(spec/def :sd.round/filter
  :sd.filter/value)


(spec/def :sd.round.nth/filter
  (spec/keys :req-un [:sd.round/round
                      :sd.round/filter]))


(spec/def :sd.round.nth/sort
  (spec/and :sd.round.nth/filter
            (spec/keys :req-un [:sd.sort/sort])))


(spec/def :sd.round.nth/n
  nat-int?)


(spec/def :sd.round/nth
  (spec/and :sd.round.nth/sort
            (spec/keys :req-un [:sd.round.nth/n
                                :sd.player/factions
                                :sd.faction/icons])))


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


(defn unpair-player
  [round name]
  (update round :games #(mapv (fn [g] (game/unpair-player g name)) %)))


(defn pair-player
  [round field name]
  (-> round
      (unpair-player name)
      (assoc-in field name)))


(defn player-paired?
  [round name]
  (some #(game/player-paired? % name) (:games round)))


(spec/def :sd.round/options
  (spec/map-of :sd.player/name string?))


(defn players-options
  [round ;; rankings
   ]
  (->> (:players round)
       (map (fn [name]
              (let [rank-name (str name ;; " #" (get rankings name)
                                   )]
                [name
                 (if (player-paired? round name)
                   rank-name
                   (str "> " rank-name))])))
       (into {})))


(defn paired-players
  [{:keys [games] :as round}]
  (flatten (map game/player-names games)))


(defn game-for-player
  [round name]
  (first (filter #(game/player-paired? % name) (:games round))))


(defn games-for-player
  [name rounds]
  (mapv #(game-for-player % name) rounds))


(defn tables-for-player
  [name rounds]
  (->> rounds
       (games-for-player name)
       (map :table)))


(defn opponents-for-player
  [name rounds]
  (->> rounds
       (games-for-player name)
       (map #(game/opponent-for-player % name))
       (remove nil?)
       (set)))


(spec/def :sd.round/opponents
  (spec/map-of :sd.player/name :sd.player/names))


(defn opponents
  [names rounds]
  (->> names
       (map #(vector % (opponents-for-player % rounds)))
       (into {})))


(spec/def :sd.round.players-results/results
  (spec/coll-of :sd.game/result :kind vector?))


(spec/def :sd.round.players-results/player
  (spec/and :sd.player/player
            (spec/keys :req-un [:sd.round.players-results/results])))


(spec/def :sd.round.players-results/players
  (spec/coll-of :sd.round.players-results/player))


(spec/def :sd.round.players-results/lists
  :sd.player.lists/sub)


(spec/def :sd.round.players-results/n-rounds
  nat-int?)


(spec/def :sd.round/players-results
  (spec/keys :req-un [:sd.round.players-results/players
                      :sd.round.players-results/lists
                      :sd.round.players-results/n-rounds]))


(spec/def :sd.round.summary/filter
  :sd.filter/value)


(spec/def :sd.round/summary-filter
  (spec/and :sd.round/players-results
            (spec/keys :req-un [:sd.round.summary/filter])))


(spec/def :sd.round/summary-sort
  (spec/and :sd.round/summary-filter
            (spec/keys :req-un [:sd.sort/sort])))


(spec/def :sd.round/summary
  (spec/and :sd.round/summary-sort
            (spec/keys :req-un [:sd.faction/icons])))


(defn lists-for-player
  [name rounds]
  (->> rounds
       (games-for-player name)
       (map #(game/list-for-player % name))
       (remove nil?)
       (set)
       (vec)))


(defn results-for-player
  [name rounds]
  (->> rounds
       (games-for-player name)
       (mapv #(game/result-for-player % name))))


(defn normalize-score
  [score]
  (-> score
      (update :tournament #(if (nil? %) 0 %))
      (update :assassination #(if % 1 0))))


(defn total-score-for-player
  [name rounds]
  (->> rounds
       (results-for-player name)
       (map :score)
       (map normalize-score)
       (apply merge-with +)))


;; (defn total-scores-for-players
;;   [names rounds]
;;   (let [scores (into {}
;;                      (map (fn [n]
;;                             [n (-> (total-score-for-player n rounds)
;;                                    (assoc :opponents (set (opponents-for-player n rounds))))])
;;                           names))]
;;     (into {}
;;           (map (fn [[n s]]
;;                  [n (assoc s :sos (reduce + (map #(get-in scores [% :tournament])
;;                                                  (:opponents s))))])
;;                scores))))


(defn already-paired
  [{:keys [games]} opponents]
  (->> games
       (map game/player-names)
       (map vector (range))
       (filter (fn [[n [p1 p2]]]
                 (some #{p2} (get opponents p1 '()))))
       (reduce (fn [mem [n [p1 p2]]]
                 (assoc-in mem [n :players] [p1 p2]))
               {})))


(defn unpaired-players
  [{:keys [players] :as round}]
  (set/difference
    (set players)
    (set (paired-players round))))


(defn faction-mirrors
  [{:keys [games] :as round} factions]
  (->> games
       (map vector (range))
       (reduce (fn [warns [i game]]
                 (cond-> warns
                   (game/faction-mirror? game factions)
                   (assoc-in [i :faction] true)))
               {})))


(defn same-origins
  [{:keys [games] :as round} origins]
  (->> games
       (map vector (range))
       (reduce (fn [warns [i game]]
                 (cond-> warns
                   (game/same-origin? game origins)
                   (assoc-in [i :origin] true)))
               {})))


(spec/def :sd.round/next
  (spec/and :sd.form/validated
            (spec/keys :req-un [:sd.player/factions
                                :sd.faction/icons
                                :sd.round/options])))


(defn validate
  [form-state]
  (form/validate :sd.round/round form-state))


(defn already-paired->string
  [already-paired]
  (let [n-already (count already-paired)
        plural? (> n-already 1)
        many? (> n-already 3)
        pairings (map (fn [[n {:keys [players]}]] players) already-paired)]
    (if many?
      (str n-already " pairings have already been played.")
      (str "Pairing" (when plural? "s") " " (s/join ", " (map #(s/join "-" %) pairings))
           " " (if plural? "have" "has")" already been played."))))


(defn unpaired-players->string
  [unpaired-players]
  (let [n-unpaired (count unpaired-players)
        plural? (> n-unpaired 1)
        many? (> n-unpaired 3)]
    (if many?
      (str n-unpaired " players are not paired")
      (str "Player" (when plural? "s") " " (s/join ", " (sort-by #(.toLowerCase %) unpaired-players))
           " " (if plural? "are" "is")" not paired"))))


(defn faction-mirrors->string
  [faction-mirrors]
  (let [n-mirrors (count faction-mirrors)
        plural? (> n-mirrors 1)]
    (str "There " (if plural? "are" "is") " " n-mirrors
         " mirror game" (when plural? "s"))))


(defn same-origins->string
  [same-origins]
  (let [n-sames (count same-origins)
        plural? (> n-sames 1)]
    (str "There " (if plural? "are" "is") " " n-sames
         " same-origin pairing" (when plural? "s"))))


(def merge-warns
  (fnil
    (fn [base extend]
      (->> extend
           (map (fn [[k v]]
                  (if (contains? base k)
                    [k (merge (get base k) v)]
                    [k v])))
           (into base)))
    {}))


(defn validate-pairings
  [{:keys [edit] :as form-state}
   {:keys [factions opponents origins]}]
  (let [already-paired (already-paired edit opponents)
        unpaired-players (unpaired-players edit)
        faction-mirrors (faction-mirrors edit factions)
        same-origins (same-origins edit origins)]
    (cond-> form-state
      (not-empty unpaired-players)
      (assoc-in [:error nil :unpaired]
                (unpaired-players->string unpaired-players))
      (not-empty faction-mirrors)
      (-> (assoc-in [:warn nil :faction]
                    (faction-mirrors->string faction-mirrors))
          (update-in [:warn :games]
                     merge-warns faction-mirrors))
      (not-empty same-origins)
      (-> (assoc-in [:warn nil :origin]
                    (same-origins->string same-origins))
          (update-in [:warn :games]
                     merge-warns same-origins))
      (not-empty already-paired)
      (-> (assoc-in [:error nil :pairings]
                    (already-paired->string already-paired))
          (update-in [:error :games]
                     merge-warns already-paired)))))


;; (defn rename-player
;;   [round old-name new-name]
;;   (-> round
;;       (update :players #(vec (remove (fn [name] (= name old-name)) %)))
;;       (update :players conj new-name)
;;       (update :games #(mapv (fn [game] (game/rename-player game old-name new-name)) %))))


(defn random-score
  [round lists]
  (update round :games
          #(mapv (fn [game]
                   (game/random-score game lists))
                 %)))


(defn tournament-weight
  [[p s]]
  (- 0 (:tournament s)))


(defn faction-weight
  [faction [p s]]
  (if (and (some? faction)
           (= (:faction p) faction))
    1 0))


(defn origin-weight
  [origin [p s]]
  (if (and (some? origin)
           (= (:origin p) origin))
    1 0))


(defn sort-opponents
  [{:keys [faction origin] :as player} players-results]
  (sort-by (juxt tournament-weight
                 (partial origin-weight origin)
                 (partial faction-weight faction))
           players-results))


(defn choose-opponent
  [{:keys [name] :as players} old-opponents opponents]
  (let [old-opponents (set old-opponents)
        possible-opponents (remove (fn [[p s]] (old-opponents (:name p)))
                                   opponents)
        [next-opponent] (if (empty? possible-opponents)
                          (first opponents)
                          (first possible-opponents))]
    next-opponent))


(defn table->group
  [table tables-groups-size]
  (inc (mod table tables-groups-size)))


(defn choose-table
  [old-tables tables tables-groups-size]
  (let [old-groups (set (map #(table->group % tables-groups-size) old-tables))
        possible-tables-without-groups (remove (set old-tables) tables)
        possible-tables (remove #(old-groups (table->group % tables-groups-size))
                                possible-tables-without-groups)]
    (if-not (empty? possible-tables)
      (first possible-tables)
      (if-not (empty? possible-tables-without-groups)
        (first possible-tables-without-groups)
        (first tables)))))


(defn player-pairing
  [player players-results tables rounds
   {:keys [tables-groups-size]
    :or {tables-groups-size 1}}]
  (let [sorted-opponents (sort-opponents player players-results)
        next-opponent (choose-opponent
                        player
                        (opponents-for-player (:name player) rounds)
                        sorted-opponents)
        old-tables (concat (tables-for-player (:name player) rounds)
                           (tables-for-player (:name next-opponent) rounds))
        next-table (choose-table old-tables tables tables-groups-size)]
    [next-table (:name player) (:name next-opponent)]))


(defn sr-pairing
  [players rounds settings]
  (let [players-results (->> players
                             (map #(vector % (total-score-for-player (:name %) rounds)))
                             (sort-by tournament-weight))]
    (loop [[[current-player] & rest-players] players-results
           tables (range 1 (inc (players->ngames players)))
           pairings []]
      (let [pairing (player-pairing current-player rest-players tables rounds settings)
            [table _ opponent] pairing
            rest-players (remove (fn [[p s]] (= (:name p) opponent))
                                 rest-players)
            rest-tables (remove #{table} tables)
            pairings (conj pairings pairing)]
        (if (empty? rest-players)
          (mapv #(apply game/->game %) (sort-by first pairings))
          (recur rest-players rest-tables pairings))))))


(defn drop-nth
  [n rounds]
  (into (subvec rounds 0 n)
        (subvec rounds (inc n))))


(defn filter-with
  [round pattern]
  (update round :games #(vec (filter (fn [game] (game/match-pattern? game pattern)) %))))


(def sort-props
  {:table :table
   :player1 #(.toLowerCase (or (get-in % [:player1 :name]) ""))
   :player2 #(.toLowerCase (or (get-in % [:player2 :name]) ""))})


(defn sort-with
  [round {:keys [by reverse?]}]
  (let [games (as-> (:games round) $
                (sort-by (by sort-props) $)
                (cond-> $
                  reverse? (cljs.core/reverse))
                (vec $))]
    (assoc round :games games)))


;; (defn add-factions
;;   [round factions]
;;   (update round :games
;;           #(mapv
;;              (fn [game]
;;                (game/add-factions game factions))
;;              %)))
