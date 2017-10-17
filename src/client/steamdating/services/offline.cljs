(ns steamdating.services.offline
  (:require [re-frame.core :as re-frame]
            [steamdating.services.db :as db]
            [steamdating.services.debug :refer [debug?]]
            [steamdating.services.toaster]
            [steamdating.services.prompt]))


(defn on-worker-state-change
  [worker]
  ;; (js/console.info "Serviceworker state change" (aget worker "state"))
  (when (= (aget worker "state") "installed")
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
  ;; (js/console.info "Serviceworker installing")
  (when-let [worker (aget registration "installing")]
    (aset worker "onstatechange" #(on-worker-state-change worker))))


(defn on-worker-registration
  [registration]
  ;; (js/console.info "Serviceworker registered")
  (when-let [push-manager (aget registration "pushManager")]
    (re-frame/dispatch [:steamdating.online.push/set-manager push-manager]))
  (aset registration "onupdatefound" #(on-worker-installing registration)))


(defn on-worker-failed
  [error]
  (js/console.error "Error during service worker registration" error)
  (re-frame/dispatch [:steamdating.toaster/set
                      {:type :error
                       :message "Offline installation failed"}]))


(defn on-worker-message
  [event]
  ;; (js/console.log "serviceWorker message" event)
  (when (= "follow-refresh" (aget event "data"))
    (re-frame/dispatch [:steamdating.online.follow/refresh])))


(defn init
  []
  (when-let [serviceWorker (aget js/navigator "serviceWorker")]
    (doto serviceWorker
      (.addEventListener "message" on-worker-message)
      (-> (.register "service-worker.js")
          (.then on-worker-registration
                 on-worker-failed)))))


(db/reg-event-fx
  :steamdating.offline/reload
  (fn reload
    []
    (.reload js/location)
    {}))
