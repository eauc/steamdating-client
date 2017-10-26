(ns steamdating.models.prompt
  (:require [cljs.spec.alpha :as spec]
            [clojure.string :as s]))


(spec/def :sd.prompt/type
  #{:alert :confirm :prompt})


(spec/def :sd.prompt/message
  (spec/and string? not-empty))


(spec/def :sd.prompt/on-cancel
  vector?)


(spec/def :sd.prompt/on-validate
  vector?)


(spec/def :sd.prompt/value
  (spec/or :string-value string?
           :number-value number?))


(spec/def :sd.prompt/prompt
  (spec/keys :req-un [:sd.prompt/type
                      :sd.prompt/message]
             :opt-un [:sd.prompt/on-cancel
                      :sd.prompt/on-validate
                      :sd.prompt/value]))
