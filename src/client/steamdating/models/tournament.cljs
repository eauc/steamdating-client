(ns steamdating.models.tournament
  (:require [cljs.spec.alpha :as spec]))


(spec/def :sd.tournament/version
  nat-int?)


(spec/def :sd.tournament/tournament
  (spec/keys :req-un [:sd.tournament/version]))


(defn ->tournament
  []
  {:version 1})
