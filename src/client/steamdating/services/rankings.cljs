(ns steamdating.services.rankings
  (:require [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame.core :as re-frame]
            [re-frame-tracer.core :refer [tracer]]
            [steamdating.models.ranking :as ranking]))


(trace-forms
  {:tracer (tracer :color "green")}


  (re-frame/reg-sub
    :steamdating.rankings/ranking
    :<- [:steamdating.players/players]
    :<- [:steamdating.rounds/players-scores]
    (fn ranking-sub
      [[players scores] _]
      (ranking/->ranking scores players)))

  (re-frame/reg-sub
    :steamdating.rankings/list
    :<- [:steamdating.rankings/ranking]
    :<- [:steamdating.filters/pattern :ranking]
    :<- [:steamdating.sorts/sort :ranking {:by [:rank]}]
    (fn list-sub
      [[ranking pattern sort] _]
      (->> ranking
           (ranking/filter-with pattern)
           (ranking/sort-with sort))))


  (re-frame/reg-sub
    :steamdating.rankings/bests
    :<- [:steamdating.rankings/ranking]
    (fn bests-sub
      [ranking _]
      (ranking/bests ranking)))

  )
