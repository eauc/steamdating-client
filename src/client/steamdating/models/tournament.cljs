(ns steamdating.models.tournament
  (:require [cljs.spec.alpha :as spec]
            [steamdating.models.player]))


(spec/def :steamdating.tournament/tournament
  (spec/keys :req-un [:steamdating.player/players]))


(defn ->tournament
  []
  {:players []})
