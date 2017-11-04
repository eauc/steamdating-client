(ns steamdating.models.ranking
  (:require [cljs.spec.alpha :as spec]
            [steamdating.models.filter]
            [steamdating.models.sort]
            [steamdating.services.debug :as debug]
            [steamdating.models.player :as player]))


(spec/def :sd.ranking/rank
  nat-int?)


(spec/def :sd.ranking/ranking
  (spec/and :sd.player/player
            (spec/keys :req-un [:sd.round.players-scores/score
                                :sd.ranking/rank])))


(spec/def :sd.ranking/rankings
  (spec/coll-of :sd.ranking/ranking))


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
            (spec/keys :req-un [:sd.faction/icons])))


;; (defn score-empty?
;;   [score]
;;   (= 0
;;      (reduce (fn [sum [k v]] (if (number? v) (+ sum v) sum))
;;              0 score)))


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


;; (defn best-by
;;   [score ranking]
;;   (let [score-max (->> (mapv #(get-in % [:score score]) ranking)
;;                        (apply max))]
;;     (if (= 0 score-max)
;;       nil
;;       {:value score-max
;;        :names (mapv :name (filter #(= score-max (get-in % [:score score])) ranking))})))


;; (defn best-in-factions
;;   [ranking]
;;   (->> ranking
;;        (group-by :faction)
;;        (map (fn [[f ps]] [f (first (sort-by :rank ps))]))
;;        (map (fn [[f p]] [f (if (score-empty? (:score p))
;;                              nil
;;                              (select-keys p [:name :rank]))]))
;;        (filter (fn [[f p]] (some? p)))
;;        (into {})))


;; (defn bests
;;   [ranking]
;;   (into {:faction (best-in-factions ranking)}
;;         (remove nil?
;;                 (map (fn [score] [score (best-by score ranking)])
;;                      [:sos :scenario :army :assassination]))))


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
