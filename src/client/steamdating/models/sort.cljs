(ns steamdating.models.sort
  (:require [cljs.spec.alpha :as spec]))


(spec/def :steamdating.sort/reverse
  boolean?)


(spec/def :steamdating.sort/by
  some?)


(spec/def :steamdating.sort/sort
  (spec/keys :req-un[:steamdating.sort/by
                     :steamdating.sort/reverse]))


(spec/def :steamdating.sort/sorts
  (spec/map-of keyword? :steamdating.sort/sort))
