(ns steamdating.models.ui
  (:require [cljs.spec.alpha :as spec]
            [steamdating.models.route]
            [clojure.string :as s]))


(spec/def :sd.ui/menu
  (spec/nilable keyword?))


(spec/def :sd.ui/ui
  (spec/keys :req-un [:sd.ui/menu]))


(spec/def :sd.ui/menu-route
  (spec/keys :req-un [:sd.ui/menu
                      :sd.route/route]))


(defn ->ui
  []
  {:menu nil})


(defn classes
  [& cs]
  (s/join " " (filter identity cs)))
