(ns steamdating.services.rankings
  (:require [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [steamdating.models.filter :as filter]
            [steamdating.models.player :as player]
            [steamdating.models.ranking :as ranking]
            [steamdating.services.debug :as debug]))


(defn rankings-sub
  [[players scores] _]
  {:pre [(debug/spec-valid? :sd.player/players players)
         (debug/spec-valid? :sd.round/players-scores scores)]
   :post [(debug/spec-valid? :sd.ranking/rankings %)]}
  (ranking/->ranking scores players))

(re-frame/reg-sub
  :sd.rankings/rankings
  :<- [:sd.players/players]
  :<- [:sd.rounds/players-scores]
  rankings-sub)


(defn list-filter-sub
  [[rankings f] _]
  {:pre [(debug/spec-valid? :sd.ranking/rankings rankings)
         (debug/spec-valid? :sd.filter/value f)]
   :post [(debug/spec-valid? :sd.ranking/list-filter %)]}
  {:rankings (ranking/filter-with (filter/->pattern f) rankings)
   :filter f})

(re-frame/reg-sub
  :sd.rankings/list-filter
  (fn list-filter-sub-inputs
    [[_ {:keys [filter]}]]
    [(re-frame/subscribe [:sd.rankings/rankings])
     (re-frame/subscribe [:sd.filters/filter filter])])
  list-filter-sub)


(defn list-sort-sub
  [[{:keys [rankings] :as input} s]]
  {:pre [(debug/spec-valid? :sd.ranking/list-filter input)
         (debug/spec-valid? :sd.sort/sort s)]
   :post [(debug/spec-valid? :sd.ranking/list-sort %)]}
  (assoc input
         :rankings (ranking/sort-with s rankings)
         :sort s))

(re-frame/reg-sub
  :sd.rankings/list-sort
  (fn list-sort-sub-inputs
    [[_ params]]
    [(re-frame/subscribe [:sd.rankings/list-filter params])
     (re-frame/subscribe [:sd.sorts/sort :rankings {:by [:rank]}])])
  list-sort-sub)


(defn list-sub
  [[input icons] _]
  {:pre [(debug/spec-valid? :sd.ranking/list-sort input)
         (debug/spec-valid? :sd.faction/icons icons)]
   :post [(debug/spec-valid? :sd.ranking/list %)]}
  (assoc input :icons icons))

(re-frame/reg-sub
  :sd.rankings/list
  (fn list-sub-inputs
    [[_ params] _]
    [(re-frame/subscribe [:sd.rankings/list-sort params])
     (re-frame/subscribe [:sd.factions/icons])])
  list-sub)


;; (re-frame/reg-sub
;;   :sd.rankings/bests
;;   :<- [:sd.rankings/ranking]
;;   (fn bests-sub
;;     [ranking _]
;;     (ranking/bests ranking)))


;; (re-frame/reg-sub
;;   :sd.rankings/players
;;   :<- [:sd.rankings/ranking]
;;   (fn players-sub
;;     [ranking _]
;;     (into {} (map (juxt :name :rank) ranking))))
