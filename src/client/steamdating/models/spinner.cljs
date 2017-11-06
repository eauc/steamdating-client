(ns steamdating.models.spinner
  (:require [cljs.spec.alpha :as spec]))


(spec/def :sd.spinner/message
  (spec/and string? not-empty))


(spec/def :sd.spinner/spinner
  (spec/keys :req-un [:steamdating.spinner/message]))
