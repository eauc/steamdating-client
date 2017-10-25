(ns steamdating.services.db
  (:require [cljs.spec.alpha :as spec]
            [expound.alpha :refer [expound-str]]
            [re-frame.core :as re-frame]
            [steamdating.models.route :refer [->route]]
            [steamdating.models.ui :refer [->ui]]
            [steamdating.services.debug :refer [debug?]]
            [steamdating.services.store :refer [store-db-interceptor]]))


(spec/def :sd.db/db
  (spec/keys :req-un [:sd.route/route
                      :sd.ui/ui]))


(defn check-db-schema
  [db]
  (when (not (spec/valid? :sd.db/db db))
    (throw (ex-info "db spec check failed"
                    (expound-str :sd.db/db db)))))


(def check-spec-interceptor
  (re-frame/after check-db-schema))


(def default-interceptors
  [store-db-interceptor
   check-spec-interceptor
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
  {:route (->route)
   :ui (->ui)})


(reg-event-fx
  :steamdating.db/initialize
  [(re-frame/inject-cofx :steamdating.storage/local)]
  (fn initialize-db
    [{:keys [local-storage]}]
    (let [stored-db (->> local-storage
                         (remove (fn [[k v]] (nil? v)))
                         (reduce (fn [m [k v]] (assoc m k v)) default-db))]
      (if (spec/valid? :sd.db/db stored-db)
        {:db stored-db}
        {:db default-db}))))


(reg-event-fx
  :steamdating.storage/update
  (fn storage-update
    [{:keys [db]} [value]]
    {:db (merge db value)}))
