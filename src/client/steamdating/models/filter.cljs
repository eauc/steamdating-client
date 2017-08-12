(ns steamdating.models.filter
  (:require [cljs.spec.alpha :as spec]
            [clojure.string :as s]))


(spec/def :steamdating.filter/value
  string?)


(spec/def :steamdating.filter/name
  keyword?)


(spec/def :steamdating.filter/filters
  (spec/map-of :steamdating.filter/name :steamdating.filter/value))


(defn filter->regexp
  [filter]
  (let [filter-value (if (or (nil? filter) (empty? filter))
                       ".*"
                       filter)]
    (-> filter-value
        (s/replace #"\s+" "|")
        (->> (str "(?i)"))
        (re-pattern))))
