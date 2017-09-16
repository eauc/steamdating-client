(ns steamdating.services.db
  (:require [cljs.spec.alpha :as spec]
            [expound.alpha :refer [expound-str]]
            [re-frame.core :as re-frame]
            [steamdating.models.faction]
            [steamdating.models.filter]
            [steamdating.models.form]
            [steamdating.models.prompt]
            [steamdating.models.route :refer [->route]]
            [steamdating.models.sort]
            [steamdating.models.toaster]
            [steamdating.models.tournament :refer [->tournament]]
            [steamdating.services.debug :refer [debug?]]
            [steamdating.services.store]))


(spec/def ::factions
  (spec/nilable :steamdating.faction/factions))


(spec/def ::filters
  (spec/nilable :steamdating.filter/filters))


(spec/def ::forms
  (spec/map-of keyword? :steamdating.form/form))


(spec/def ::route
  (spec/nilable :steamdating.route/route))


(spec/def ::prompt
  (spec/nilable :steamdating.prompt/prompt))


(spec/def ::sorts
  (spec/nilable :steamdating.sort/sorts))


(spec/def ::toaster
  (spec/nilable :steamdating.toaster/toaster))


(spec/def ::tournament
  :steamdating.tournament/tournament)


(spec/def ::db
  (spec/keys :req-un [::factions
                      ::filters
                      ::forms
                      ::prompt
                      ::route
                      ::sorts
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
   :filters nil
   :forms {}
   :prompt nil
   :route nil
   :sorts nil
   :toaster nil
   :tournament (->tournament)})


(reg-event-fx
  :steamdating.db/initialize
  [(re-frame/inject-cofx :steamdating.storage/local)]
  (fn initialize-db
    [{:keys [local-storage]}]
    {:db (merge default-db local-storage)}))


(reg-event-fx
  :steamdating.storage/update
  (fn storage-update
    [{:keys [db]} [value]]
    {:db (merge db value)}))
