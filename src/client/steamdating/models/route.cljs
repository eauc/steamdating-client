(ns steamdating.models.route
  (:require [cljs.spec.alpha :as spec]))


(spec/def :sd.route/page
  keyword?)


(spec/def :sd.route/hash
  string?)


(spec/def :sd.route/params
  map?)


(spec/def :sd.route/route
  (spec/keys :req-un [:sd.route/page
                      :sd.route/hash]
             :opt-un [:sd.route/params]))

(defn ->route
  []
  {:page :default
   :hash (.-hash js/location)})
