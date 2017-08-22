(ns steamdating.models.page
  (:require [cljs.spec.alpha :as spec]))


(spec/def :steamdating.page/route
  keyword?)


(spec/def :steamdating.page/params
  (spec/nilable map?))


(spec/def :steamdating.page/page
  (spec/keys :req-un [:steamdating.page/route]
             :opt-un [:steamdating.page/params]))
