(ns steamdating.components.prompt.spec
  (:require [cljs.spec.alpha :as spec]
            [clojure.string :as str]))


(spec/def :steamdating.prompt/type
  #{:alert :confirm :prompt})


(spec/def :steamdating.prompt/message
  (spec/and string?
            (comp not empty? str/trim)))


(spec/def :steamdating.prompt/on-cancel
  (spec/coll-of keyword? :kind vector?))


(spec/def :steamdating.prompt/on-validate
  (spec/coll-of keyword? :kind vector?))


(spec/def :steamdating.prompt/value
  (spec/or :string-value string?
           :number-value number?))


(spec/def :steamdating.prompt/prompt
  (spec/keys :req-un [:steamdating.prompt/type
                      :steamdating.prompt/message]
             :opt-un [:steamdating.prompt/on-cancel
                      :steamdating.prompt/on-validate
                      :steamdating.prompt/value]))
