(ns steamdating.models.route
  (:require [cljs.spec.alpha :as spec]))


(spec/def :steamdating.route/page
  keyword?)


(spec/def :steamdating.route/hash
  (spec/nilable string?))


(spec/def :steamdating.route/params
  (spec/nilable map?))


(spec/def :steamdating.route/route
  (spec/keys :req-un [:steamdating.route/page]
             :opt-un [:steamdating.route/hash
                      :steamdating.route/params]))

(defn ->route
  []
  {:page :default
   :hash (.-hash js/location)})
