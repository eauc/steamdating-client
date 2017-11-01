(ns steamdating.models.tournament
  (:require [cljs.spec.alpha :as spec]
            [steamdating.models.player]))


(spec/def :sd.tournament/version
  nat-int?)


(spec/def :sd.tournament/tournament
  (spec/keys :req-un [:sd.player/players
                      :sd.rounds/rounds
                      :sd.tournament/version]))


(defn ->tournament
  []
  {:players []
   :rounds []
   :version 1})
