(ns steamdating.models.tournament
  (:require [cljs.spec.alpha :as spec]
            [steamdating.models.player]
            [steamdating.models.online]))


(spec/def :sd.tournament/version
  nat-int?)


(spec/def :sd.tournament/online
  :sd.online/tournament)


(spec/def :sd.tournament/tournament
  (spec/keys :req-un [:sd.player/players
                      :sd.rounds/rounds
                      :sd.tournament/version]
             :opt-un [:sd.tournament/online]))


(defn ->tournament
  []
  {:players []
   :rounds []
   :version 1})
