(ns steamdating.services.store
  (:require [re-frame.core :as re-frame]
            [steamdating.services.debug :as debug]
            [cljs.reader :as reader]))


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
  (println "<<< set local-storage")
  (->> (pr-str state)
       (.setItem js/localStorage local-storage-key)))


(re-frame/reg-sub
  :steamdating.storage/local
  :<- [:steamdating.storage/tournament]
  :<- [:steamdating.storage/factions]
  (fn local-storage-sub
    [[tournament factions]]
    (set-local-storage {:tournament tournament})))


(defn on-change
  [e]
  (let [key (-> e .-key)
        value (-> e .-newValue)]
    (when (= local-storage-key key)
      (re-frame/dispatch [:steamdating.storage/update
                          (reader/read-string value)]))))


(defonce on-change-listener
  (.addEventListener js/window "storage" on-change))
