(ns steamdating.db
  (:require [re-frame.core :as re-frame]
            [cljs.spec.alpha :as spec]
            [steamdating.components.prompt.spec]
            [steamdating.debug :refer [debug?]]))


(spec/def ::prompt
  (spec/nilable :steamdating.prompt/prompt))


(spec/def ::db
  (spec/keys :req-un [::prompt]))


(defn check-db-schema
  [db]
  (when (not (spec/valid? ::db db))
    (throw (ex-info "db spec check failed"
                    (spec/explain-data ::db db)))))


(def check-spec-interceptor
  (re-frame/after check-db-schema))


(def default-interceptors
  [check-spec-interceptor
   (when debug? re-frame/debug)
   re-frame/trim-v])


(defn reg-event-fx
  ([name interceptors handler]
   (re-frame/reg-event-fx
     name
     (concat default-interceptors interceptors)
     handler))
  ([name handler]
   (reg-event-fx name [] handler)))


(def default-db
  {:prompt {:type :prompt :message "Alert" :value 42}})


(reg-event-fx
  :initialize-db
  (fn initialize-db
    []
    {:db default-db}))
