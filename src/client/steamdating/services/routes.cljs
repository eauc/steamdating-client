(ns steamdating.services.routes
  (:import goog.History)
  (:require [clairvoyant.core :refer-macros [trace-forms]]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [re-frame-tracer.core :refer [tracer]]
            [re-frame.core :as re-frame]
            [secretary.core :as secretary]
            [steamdating.services.db :as db]))


(defonce history (History.))


(defn hook-browser-navigation!
  []
  (events/listen history EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
  (.setEnabled history true))


(defn init
  []
  (secretary/set-config! :prefix "#")
  (hook-browser-navigation!))


;; (trace-forms
;;   {:tracer (tracer :color "brown")}

(re-frame/reg-cofx
  :steamdating.routes/current-hash
  (fn current-hash
    [coeffects]
    (assoc coeffects :steamdating.routes/current-hash (.-hash js/location))))


(db/reg-event-fx
  :steamdating.routes/page
  [(re-frame/path :route)
   (re-frame/inject-cofx :steamdating.routes/current-hash)]
  (fn routes-page [{:keys [db :steamdating.routes/current-hash]} [page params]]
    {:db (merge db {:page page
                    :hash current-hash
                    :params params})}))


(re-frame/reg-fx
  :steamdating.routes/navigate
  (fn routes-navigate-fx
    [to]
    (.setToken history to)))


(db/reg-event-fx
  :steamdating.routes/navigate
  (fn routes-navigate
    [_ [to]]
    {:steamdating.routes/navigate to}))


(re-frame/reg-fx
  :steamdating.routes/back
  (fn routes-back-fx
    []
    (.back js/window.history)))


(db/reg-event-fx
  :steamdating.routes/back
  (fn routes-back
    [_ _]
    {:steamdating.routes/back nil}))


(re-frame/reg-sub
  :steamdating.routes/route
  (fn page-sub
    [db _]
    (:route db)))


(re-frame/reg-sub
  :steamdating.routes/hash
  :<- [:steamdating.routes/route]
  (fn hash-sub
    [route _]
    (get route :hash "")))

;; )
