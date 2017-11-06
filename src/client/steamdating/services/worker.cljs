(ns steamdating.services.worker
  (:require [re-frame.core :as re-frame]
            [steamdating.services.db :as db]
            [steamdating.services.debug :refer [debug?]]
            [steamdating.services.notifications]
            [steamdating.services.prompt]
            [steamdating.services.toaster]))


(defn on-worker-state-change
  [worker]
  ;; (js/console.info "Serviceworker state change" (aget worker "state"))
  (when (= (aget worker "state") "installed")
    (if (some? (aget js/navigator "serviceWorker" "controller"))
      (re-frame/dispatch [:sd.prompt/set
                          {:type :confirm
                           :message "New app version available. Reload app ?"
                           :on-validate [:sd.worker/reload]}])
      (re-frame/dispatch [:sd.toaster/set
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
    (re-frame/dispatch [:sd.notification.push.manager/set push-manager]))
  (aset registration "onupdatefound" #(on-worker-installing registration)))


(defn on-worker-failed
  [error]
  (js/console.error "Service worker registration error" error)
  (re-frame/dispatch [:sd.toaster/set
                      {:type :error
                       :message "Offline installation failed"}]))


(defn on-worker-message
  [event]
  ;; (js/console.log "serviceWorker message" event)
  (when (= "follow-refresh" (aget event "data"))
    (re-frame/dispatch [:sd.online.follow/refresh])))


(defn worker-init
  []
  (when-let [service-worker (aget js/navigator "serviceWorker")]
    (doto service-worker
      (.addEventListener "message" on-worker-message)
      (-> (.register "service-worker.js")
          (.then on-worker-registration
                 on-worker-failed)))))


(db/reg-event-fx
  :sd.worker/reload
  (fn reload
    []
    (.reload js/location)
    {}))
