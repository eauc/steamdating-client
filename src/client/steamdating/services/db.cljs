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
            [steamdating.models.ui :refer [->ui]]
            [steamdating.services.debug :as debug :refer [debug?]]
            [steamdating.services.store :refer [store-db-interceptor]]))


(spec/def :sd.db/db
  (spec/keys :req-un [:sd.faction/factions
                      :sd.filter/filters
                      :sd.form/forms
                      :sd.route/route
                      :sd.sort/sorts
                      :sd.tournament/tournament
                      :sd.ui/ui]
             :opt-un [:sd.prompt/prompt
                      :sd.toaster/toaster]))


(defn check-db-schema
  [db]
  (when (not (debug/spec-valid? :sd.db/db db))
    (throw (ex-info "db spec check failed"
                    (expound-str :sd.db/db db)))))


(def check-spec-interceptor
  (re-frame/after check-db-schema))


(def default-interceptors
  [store-db-interceptor
   (when debug? check-spec-interceptor)
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
  {:factions {}
   :filters {}
   :forms {}
   :route (->route)
   :sorts {}
   :tournament (->tournament)
   :ui (->ui)})


(reg-event-fx
  :steamdating.db/initialize
  [(re-frame/inject-cofx :steamdating.storage/local)]
  (fn initialize-db
    [{:keys [local-storage]}]
    (let [stored-db (->> local-storage
                         (remove (fn [[k v]] (nil? v)))
                         (reduce (fn [m [k v]] (assoc m k v)) default-db))]
      {:db (if (spec/valid? :sd.db/db stored-db)
             stored-db
             default-db)
       :dispatch-n [[:sd.factions/initialize]]})))


(reg-event-fx
  :steamdating.storage/update
  (fn storage-update
    [{:keys [db]} [value]]
    {:db (merge db value)}))
