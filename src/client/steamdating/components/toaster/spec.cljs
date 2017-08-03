(ns steamdating.components.toaster.spec
  (:require [cljs.spec.alpha :as spec]))


(spec/def :steamdating.toaster/type
  #{:success :warning :error :info})


(spec/def :steamdating.toaster/message
  (spec/and string?
            (comp not empty?)))


(spec/def :steamdating.toaster/toaster
  (spec/keys :req-un [:steamdating.toaster/type
                      :steamdating.toaster/message]))
