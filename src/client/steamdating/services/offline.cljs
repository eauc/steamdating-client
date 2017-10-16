(ns steamdating.services.offline
  (:require [re-frame.core :as re-frame]
            [steamdating.services.db :as db]
            [steamdating.services.debug :refer [debug?]]
            [steamdating.services.toaster]
            [steamdating.services.prompt]))


(defn on-worker-state-change
  [worker]
  (when (= (aget worker "state") "installed")
    ;; (js/console.log "change")
    (if (some? (aget js/navigator "serviceWorker" "controller"))
      (re-frame/dispatch [:steamdating.prompt/set
                          {:type :confirm
                           :message "New app version available. Reload app ?"
                           :on-validate [:steamdating.offline/reload]}])
      (re-frame/dispatch [:steamdating.toaster/set
                          {:type :success
                           :message "Application available offline"}]))))


(defn on-worker-installing
  [registration]
  (when-let [worker (aget registration "installing")]
    (aset worker "onstatechange" #(on-worker-state-change worker))))


(defn on-worker-registration
  [registration]
  (aset registration "onupdatefound" #(on-worker-installing registration)))


(defn on-worker-failed
  [error]
  (js/console.error "Error during service worker registration" error)
  (re-frame/dispatch [:steamdating.toaster/set
                      {:type :error
                       :message "Offline installation failed"}]))

(defn init
  []
  (if-not debug?
    (when-let [serviceWorker (aget js/navigator "serviceWorker")]
      (-> serviceWorker
          (.register "service-worker.js")
          (.then on-worker-registration
                 on-worker-failed)))))


(db/reg-event-fx
  :steamdating.offline/reload
  (fn reload
    []
    ;; (js/console.log "reload")
    (.reload js/location)
    {}))
