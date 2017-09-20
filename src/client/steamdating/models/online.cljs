(ns steamdating.models.online
  (:require [cljs.spec.alpha :as spec]))


(spec/def :steamdating.online/token
  string?)


(spec/def :steamdating.online/online
  (spec/keys :opt-un [:steamdating.online/token]))
