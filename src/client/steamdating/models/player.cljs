(ns steamdating.models.player
  (:require [cljs.spec.alpha :as spec]
            [clojure.string :as s]
            [steamdating.models.faction]
            [steamdating.models.form :as form]
            [steamdating.services.debug :as debug]))


(spec/def :sd.player/name
  (spec/and string? not-empty))


(spec/def :sd.player/origin
  (spec/and string?))


(spec/def :sd.player/faction
  (spec/and string? not-empty))


(spec/def :sd.player/list
  (spec/and string? not-empty))


(spec/def :sd.player/lists
  (spec/coll-of :sd.player/list :kind vector?))


(spec/def :sd.player/notes
  string?)


(spec/def :sd.player/droped-after
  (spec/and integer? #(> % 0)))


(spec/def :sd.player/player
  (spec/keys :req-un [:sd.player/name]
             :opt-un [:sd.player/origin
                      :sd.player/faction
                      :sd.player/lists
                      :sd.player/notes
                      :sd.player/droped-after]))


(spec/def :sd.player/players
  (spec/coll-of :sd.player/player :kind vector?))


(spec/def :sd.player/names
  (spec/coll-of :sd.player/name :kind set?))


(defn ->player
  [data factions]
  (let [faction (get data :faction)
        casters (as-> factions $
                  (get-in $ [(keyword faction) :casters])
                  (keys $)
                  (map name $)
                  (set $))
        lists (->> (get data :lists [])
                   (filter casters)
                   (into []))]
    (assoc data :lists lists)))


(defn names
  [players]
  (set (map :name players)))


(defn add
  [players player]
  (if (not-empty (find #(= (:name %) (:name player)) players))
    players
    (conj players player)))


;; (defn factions
;;   [players]
;;   (into {} (map (juxt :name :faction) players)))


;; (defn origins
;;   [players]
;;   (into {} (map (juxt :name :origin) players)))


;; (defn lists
;;   [players]
;;   (into {} (map (juxt :name :lists) players)))


;; (defn on-board
;;   [players]
;;   (remove #(some? (:droped-after %)) players))


;; (defn delete
;;   [players {:keys [name] :as player}]
;;   (vec (remove #(= (:name %) name) players)))


;; (defn toggle-drop
;;   [player after]
;;   (if (:droped-after player)
;;     (dissoc player :droped-after)
;;     (assoc player :droped-after after)))


;; (defn edit
;;   [players {:keys [base edit]}]
;;   (-> players
;;       (delete base)
;;       (add edit)))


;; (def player->columns
;;   (apply juxt list-columns))


(defn prop->string
  [player-prop]
  (-> player-prop
      (#(cond->> % (array? %) (s/join ",")))
      str
      (.toLowerCase)))


(defn match-pattern
  [player pattern]
  (some?
    (first
      (filter
        (fn [[k v]]
          (re-find pattern (prop->string v)))
        player))))


(defn filter-with
  [pattern players]
  (filter #(match-pattern % pattern) players))


(defn sort-prop
  [by]
  (fn [player]
    (prop->string (by player))))


(defn sort-with
  [{:keys [by reverse?]} players]
  (-> players
      (->> (sort-by (juxt (sort-prop by) (sort-prop :name))))
      (cond-> reverse? (cljs.core/reverse))))


(spec/def :sd.player.form/factions
  :sd.faction/names)


(spec/def :sd.player/form
  (spec/and :sd.form/validated
            (spec/keys :req-un [:sd.player.form/factions
                                :sd.factions/casters])))


(defn validate
  [form-state]
  (form/validate :sd.player/player form-state))


;; (defn player->title
;;   [player]
;;   (str "Name: " (:name player) "\n"
;;        "Origin: " (:origin player) "\n"
;;        "Faction: " (:faction player) "\n"
;;        "Lists: " (s/join ", " (:lists player)) "\n"
;;        "Notes: " (:notes player) "\n"))


;; (defn parse-t3-csv
;;   [t3-factions data]
;;   (->> data
;;        (s/split-lines)
;;        (rest)
;;        (map #(s/split % #";"))
;;        (map #(subvec % 3 6))
;;        (map (fn [[name faction origin]]
;;               {:name name
;;                :faction (get t3-factions faction)
;;                :origin origin}))))


;; (defn extract-cc-lists
;;   [cc-player]
;;   (mapv (fn [[key value]]
;;           (s/replace (first (:list value)) #"\s" ""))
;;         (sort
;;           (filter (fn [[key value]]
;;                     (.startsWith key "list"))
;;                   (map (fn [[key value]]
;;                          [(cljs.core/name key) value])
;;                        cc-player)))))


;; (defn convert-cc-json
;;   [cc-factions data]
;;   (map (fn [{:keys [name faction] :as player}]
;;          {:name name
;;           :faction (get cc-factions faction)
;;           :lists (extract-cc-lists player)}) data))
