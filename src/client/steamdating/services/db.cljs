(ns steamdating.services.db
  (:require [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [steamdating.components.prompt.spec]
            [steamdating.services.debug :refer [debug?]]))


(spec/def ::page
  (spec/nilable keyword?))


(spec/def ::prompt
  (spec/nilable :steamdating.prompt/prompt))


(spec/def ::db
  (spec/keys :req-un [::page
                      ::prompt]))


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
  {:page nil
   :prompt nil})


(reg-event-fx
  :initialize-db
  (fn initialize-db
    []
    {:db default-db}))
