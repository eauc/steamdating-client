(ns steamdating.models.tournament
  (:require [cljs.spec.alpha :as spec]
            [steamdating.models.player]
            [steamdating.models.round]))


(spec/def :steamdating.tournament/tournament
  (spec/keys :req-un [:steamdating.player/players
                      :steamdating.round/rounds]))


(defn ->tournament
  []
  {:players []
   :rounds []
   :version 1})
