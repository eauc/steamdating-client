(ns steamdating.models.sort
  (:require [cljs.spec.alpha :as spec]))


(spec/def :sd.sort/reverse?
  boolean?)


(spec/def :sd.sort/by
  some?)


(spec/def :sd.sort/sort
  (spec/keys :opt-un [:sd.sort/by
                      :sd.sort/reverse?]))


(spec/def :sd.sort/sorts
  (spec/map-of keyword? :sd.sort/sort))


(defn toggle-by
  [{:keys [by reverse?]} new-by defaut]
  {:by new-by
   :reverse? (if (= (or by defaut) new-by) (not reverse?) false)})
