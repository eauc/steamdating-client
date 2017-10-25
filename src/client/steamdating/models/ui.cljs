(ns steamdating.models.ui
  (:require [cljs.spec.alpha :as spec]))


(spec/def :sd.ui/menu
  (spec/nilable keyword?))


(spec/def :sd.ui/ui
  (spec/keys :req-un [:sd.ui/menu]))


(defn ->ui
  []
  {:menu nil})
