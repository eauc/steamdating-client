(ns steamdating.models.toaster
  (:require [cljs.spec.alpha :as spec]))


(spec/def :sd.toaster/type
  #{:success :warn :error :info})


(spec/def :sd.toaster/message
  (spec/and string? not-empty))


(spec/def :sd.toaster/toaster
  (spec/keys :req-un [:steamdating.toaster/type
                      :steamdating.toaster/message]))
