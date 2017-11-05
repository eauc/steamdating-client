(ns steamdating.models.ranking
  (:require [cljs.spec.alpha :as spec]
            [steamdating.models.filter]
            [steamdating.models.sort]
            [steamdating.services.debug :as debug]
            [steamdating.models.faction]
            [steamdating.models.player :as player]
            [steamdating.models.round]))


(spec/def :sd.ranking/rank
  nat-int?)


(spec/def :sd.ranking/ranking
  (spec/and :sd.player/player
            (spec/keys :req-un [:sd.round.players-scores/score
                                :sd.ranking/rank])))


(spec/def :sd.ranking/rankings
  (spec/coll-of :sd.ranking/ranking))


(defn score-empty?
  [score]
  (= 0
     (reduce (fn [sum [k v]] (if (number? v) (+ sum v) sum))
             0 score)))


(defn best-by
  [score rankings]
  (let [score-max (or (->> (mapv #(get-in % [:score score]) rankings)
                           (apply max)) 0)]
    (if (= 0 score-max)
      nil
      {:value score-max
       :names (mapv :name (filter #(= score-max (get-in % [:score score])) rankings))})))


(spec/def :sd.ranking.bests.score/value
  nat-int?)


(spec/def :sd.ranking.bests.score/names
  (spec/coll-of :sd.player/name))


(spec/def :sd.ranking.bests/score
  (spec/nilable
    (spec/keys :req-un [:sd.ranking.bests.score/names
                        :sd.ranking.bests.score/value])))


(spec/def :sd.ranking.bests/scores
  (spec/map-of keyword? :sd.ranking.bests/score))


(defn best-scores
  [rankings]
  (->> [:sos :scenario :army :assassination]
       (map #(vector % (best-by % rankings)))
       (remove nil?)
       (into {})))


(spec/def :sd.ranking.bests/in-faction
  (spec/map-of :sd.player/faction
               (spec/keys :req-un [:sd.player/name
                                   :sd.ranking/rank])))


(defn best-in-factions
  [rankings]
  (->> rankings
       (group-by :faction)
       (map (fn [[f ps]] [f (first (sort-by :rank ps))]))
       (map (fn [[f p]] [f (if (score-empty? (:score p))
                             nil
                             (select-keys p [:name :rank]))]))
       (filter (fn [[f p]] (some? p)))
       (into {})))


(spec/def :sd.ranking/bests
  (spec/keys :req-un [:sd.ranking.bests/in-faction
                      :sd.ranking.bests/scores]))


(spec/def :sd.ranking/bests-sub
  (spec/keys :req-un [:sd.ranking/bests
                      :sd.faction/icons]))


(defn bests
  [ranking]
  {:in-faction (best-in-factions ranking)
   :scores (best-scores ranking)})


(spec/def :sd.ranking.list/filter
  :sd.filter/value)


(spec/def :sd.ranking/list-filter
  (spec/keys :req-un [:sd.ranking/rankings
                      :sd.ranking.list/filter]))


(spec/def :sd.ranking/list-sort
  (spec/and :sd.ranking/list-filter
            (spec/keys :req-un [:sd.sort/sort])))


(spec/def :sd.ranking/list
  (spec/and :sd.ranking/list-sort
            (spec/keys :req-un [:sd.ranking/bests
                                :sd.faction/icons])))


(defn ->ranking
  [scores players]
  (->> players
       (map #(assoc % :score (get scores (:name %))))
       (sort-by (juxt #(get-in % [:score :tournament])
                      #(get-in % [:score :sos])
                      #(get-in % [:score :scenario])
                      #(get-in % [:score :army])))
       (reverse)
       (mapv (fn [n p] (assoc p :rank (inc n))) (range))))


(defn normalize
  [value]
  (if (string? value)
    (.toLowerCase value)
    value))


(defn filter-with
  [pattern rankings]
  (filter #(player/match-pattern (dissoc % :lists :score) pattern) rankings))


(defn sort-with
  [{:keys [by reverse?]} rankings]
  (as-> rankings $
    (sort-by (juxt #(normalize (get-in % by))
                   #(get-in % [:rank])) $)
    (cond-> $
      reverse? reverse)))
