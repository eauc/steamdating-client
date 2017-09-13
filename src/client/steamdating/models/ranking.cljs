(ns steamdating.models.ranking)


(defn ->ranking
	[scores players]
	(->> players
			 (map (fn [p]
							(assoc p :score (get scores (:name p)))))
			 (sort-by (juxt #(get-in % [:score :tournament])
											#(get-in % [:score :sos])
											#(get-in % [:score :scenario])
											#(get-in % [:score :army])))
			 (reverse)
			 (map (fn [n p] (assoc p :rank (inc n))) (range))))


(defn best-by
	[score ranking]
	(let [score-max (->> (mapv #(get-in % [:score score]) ranking)
											 (apply max))]
		{:value score-max
		 :names (mapv :name (filter #(= score-max (get-in % [:score score])) ranking))}))


(defn best-in-factions
	[ranking]
	(->> ranking
			 (group-by :faction)
			 (mapv (fn [[f ps]] [f (select-keys (first (sort-by :rank ps)) [:name :rank])]))
			 (sort-by (fn [[f p]] (:rank p)))
			 (into {})))


(defn bests
	[ranking]
	(into {:faction (best-in-factions ranking)}
				(map (fn [score] [score (best-by score ranking)])
						 [:sos :scenario :army :assassination])))


(defn normalize
	[value]
	(if (string? value)
		(.toLowerCase value)
		value))


(defn filter-with
	[pattern rankings]
	(filter #(re-find pattern (:name %)) rankings))


(defn sort-with
	[{:keys [by reverse]} rankings]
	(as-> rankings $
		(sort-by (juxt #(normalize (get-in % by))
									 #(get-in % [:rank])) $)
		(cond-> $
			reverse clojure.core/reverse)))
