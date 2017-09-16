(ns steamdating.models.player
  (:require [cljs.spec.alpha :as spec]
            [clojure.string :as s]
            [steamdating.models.form :as form]
            [steamdating.services.debug :as debug]))


(spec/def :steamdating.player/name
  (spec/and string? not-empty))


(spec/def :steamdating.player/origin
  (spec/and string?))


(spec/def :steamdating.player/faction
  (spec/and string?
            not-empty))


(spec/def :steamdating.player/list
  (spec/and string?
            not-empty))


(spec/def :steamdating.player/lists
  (spec/coll-of :steamdating.player/list :kind vector?))


(spec/def :steamdating.player/notes
  string?)


(spec/def :steamdating.player/droped-after
  (spec/and integer? #(> % 0)))


(spec/def :steamdating.player/player
  (spec/keys :req-un [:steamdating.player/name]
             :opt-un [:steamdating.player/origin
                      :steamdating.player/faction
                      :steamdating.player/lists
                      :steamdating.player/notes
                      :steamdating.player/droped-after]))


(spec/def :steamdating.player/players
  (spec/coll-of :steamdating.player/player :kind vector?))


(defn add
  [players player]
  (if (not-empty (find #(= (:name %) (:name player)) players))
    players
    (conj players player)))


(defn names
  [players]
  (set (map :name players)))


(defn factions
  [players]
  (into {} (map (juxt :name :faction) players)))


(defn origins
  [players]
  (into {} (map (juxt :name :origin) players)))


(defn lists
  [players]
  (into {} (map (juxt :name :lists) players)))


(defn on-board
  [players]
  (remove #(some? (:droped-after %)) players))


(defn delete
  [players {:keys [name] :as player}]
  (vec (remove #(= (:name %) name) players)))


(defn toggle-drop
  [player after]
  (if (:droped-after player)
    (dissoc player :droped-after)
    (assoc player :droped-after after)))


(defn edit
  [players {:keys [base edit]}]
  (-> players
      (delete base)
      (add edit)))


(def list-columns
  [:name :origin :faction :lists])


(def player->columns
  (apply juxt list-columns))


(defn filter-with
  [players pattern]
  (let [matches (->> players
                     (map (fn [p] [p, (select-keys p list-columns)]))
                     (map (fn [[p cs]] [p, (filter (fn [[key value]]
                                                     (->> value
                                                          clj->js
                                                          (.stringify js/JSON)
                                                          (re-find pattern)))
                                                   cs)]))
                     (remove (fn [[p cs]] (empty? cs))))]
    {:list (mapv first matches)
     :columns (vec (set (apply conj (->> matches
                                         (map #(nth % 1))
                                         (map #(map first %))
                                         (flatten)
                                         (apply conj [:name]))
                               list-columns)))}))


(defn sort-prop
  [by]
  (fn [player]
    (let [prop (by player)]
      (if (string? prop)
        (.toLowerCase prop)
        prop))))


(defn sort-with
  [players {:keys [by reverse]}]
  (-> players
      (->> (sort-by (juxt (sort-prop by) (sort-prop :name))))
      (cond-> reverse (cljs.core/reverse))))


(defn validate
  [form-state]
  (form/validate form-state :steamdating.player/player))


(defn player->title
  [player]
  (str "Name: " (:name player) "\n"
       "Origin: " (:origin player) "\n"
       "Faction: " (:faction player) "\n"
       "Lists: " (s/join ", " (:lists player)) "\n"
       "Notes: " (:notes player) "\n"))


(def t3-factions->sd-factions
  {"Cryx" "Cryx"
   "Cygnar" "Cygnar"
   "Khador" "Khador"
   "Protectorat de Menoth" "Protectorate"
   "Retribution of Scyrah" "Retribution"
   "Convergence of Cyriss" "Convergence"
   "Mercenaires" "Mercenaries"
   "Trollblood" "Trollbloods"
   "Cercle d'Orboros" "Circle"
   "Légion d'Everblight" "Legion"
   "Skorne" "Skorne"
   "Grymkin" "Grymkin"
   "Minions" "Minions"})


(defn parse-t3-csv
  [data]
  (debug/spy
    "data"
    (->> data
      (s/split-lines)
      (rest)
      (map #(s/split % #";"))
      (map #(subvec % 3 6))
      (map (fn [[name faction origin]]
             {:name name
              :faction (get t3-factions->sd-factions faction)
              :origin origin})))))
