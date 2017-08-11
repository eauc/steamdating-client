(ns steamdating.services.db
  (:require [cljs.spec.alpha :as spec]
            [expound.alpha :refer [expound-str]]
            [re-frame.core :as re-frame]
            [steamdating.components.prompt.spec]
            [steamdating.components.toaster.spec]
            [steamdating.models.faction]
            [steamdating.models.form]
            [steamdating.models.tournament :refer [->tournament]]
            [steamdating.services.debug :refer [debug?]]))


(spec/def ::factions
  (spec/nilable :steamdating.faction/factions))


(spec/def ::forms
  (spec/map-of keyword? :steamdating.form/form))


(spec/def ::page
  (spec/nilable keyword?))


(spec/def ::prompt
  (spec/nilable :steamdating.prompt/prompt))


(spec/def ::toaster
  (spec/nilable :steamdating.toaster/toaster))


(spec/def ::tournament
  :steamdating.tournament/tournament)


(spec/def ::db
  (spec/keys :req-un [::factions
                      ::forms
                      ::page
                      ::prompt
                      ::toaster
                      ::tournament]))


(defn check-db-schema
  [db]
  (when (not (spec/valid? ::db db))
    (throw (ex-info "db spec check failed"
                    (expound-str ::db db)))))


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
  {:factions nil
   :forms {}
   :page :home
   :prompt nil
   :toaster nil
   :tournament (->tournament)})


(reg-event-fx
  :steamdating.db/initialize
  (fn initialize-db
    []
    {:db default-db}))
