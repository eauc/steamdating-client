(ns steamdating.models.filter
  (:require [cljs.spec.alpha :as spec]
            [clojure.string :as s]))


(spec/def :sd.filter/value
  (spec/nilable string?))


(spec/def :sd.filter/name
  keyword?)


(spec/def :sd.filter/filters
  (spec/map-of :sd.filter/name :sd.filter/value))


(defn ->pattern
  [filter]
  (let [filter-value (if (or (nil? filter) (empty? filter))
                       ".*"
                       filter)]
    (-> filter-value
        (s/trim)
        (s/replace #"\s+" "|")
        (->> (str "(?i)"))
        (re-pattern))))
