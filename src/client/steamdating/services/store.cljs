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
    ">>> get local-storage"
    (-> js/localStorage
        (.getItem local-storage-key)
        (reader/read-string))))


(re-frame/reg-cofx
  :steamdating.storage/local
  (fn local-storage-cofx
    [coeffects]
    (assoc coeffects :local-storage (get-local-storage))))


(defn set-local-storage
  [state]
  (debug/log "<<< set local-storage" state)
  (->> (pr-str state)
       (.setItem js/localStorage local-storage-key)))


(defn store-db
  [{:keys [online filters tournament]}]
  (set-local-storage {:filters filters
                      :online (select-keys online [:token])
                      :tournament tournament}))


(def store-db-interceptor
  (re-frame/after store-db))


(defn on-change
  [e]
  (let [key (-> e .-key)
        value (-> e .-newValue)]
    (when (= local-storage-key key)
      (re-frame/dispatch [:steamdating.storage/update
                          (reader/read-string value)]))))


(defonce on-change-listener
  (.addEventListener js/window "storage" on-change))
