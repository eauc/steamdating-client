(ns steamdating.models.tournament
  (:require [cljs.spec.alpha :as spec]
            [steamdating.models.player]
            [steamdating.models.online]
            [steamdating.models.round]))


(spec/def :steamdating.tournament/online
  :steamdating.online/tournament)


(spec/def :steamdating.tournament/tournament
  (spec/keys :req-un [:steamdating.player/players
                      :steamdating.round/rounds]
             :opt-un [:steamdating.tournament/online]))


(defn ->tournament
  []
  {:players []
   :rounds []
   :version 1})
