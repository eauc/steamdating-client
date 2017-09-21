(ns steamdating.services.store
  (:require [cljs.reader :as reader]
            [re-frame.core :as re-frame]
            [steamdating.services.debug :as debug]))


(defonce
  local-storage-key
  "sd.state")


(defn get-local-storage
  []
  (debug/spy
    "get local-storage"
    (-> js/localStorage
        (.getItem local-storage-key)
        (reader/read-string))))


(re-frame/reg-cofx
  :steamdating.storage/local
  (fn local-storage-cofx
    [coeffects]
    (assoc coeffects :local-storage (get-local-storage))))


(re-frame/reg-sub
  :steamdating.storage/tournament
  (fn tournament-sub
    [db]
    (get db :tournament)))


(re-frame/reg-sub
  :steamdating.storage/factions
  (fn factions-sub
    [db]
    (get db :factions)))


(defn set-local-storage
  [state]
  (debug/spy "<<< set local-storage" nil)
  (->> (pr-str state)
       (.setItem js/localStorage local-storage-key)))


(re-frame/reg-sub
  :steamdating.storage/local
  :<- [:steamdating.online/online]
  :<- [:steamdating.storage/tournament]
  (fn local-storage-sub
    [[online tournament]]
    (set-local-storage {:online (select-keys online [:token])
                        :tournament tournament})))


(defn on-change
  [e]
  (let [key (-> e .-key)
        value (-> e .-newValue)]
    (when (= local-storage-key key)
      (re-frame/dispatch [:steamdating.storage/update
                          (reader/read-string value)]))))


(defonce on-change-listener
  (.addEventListener js/window "storage" on-change))
