(ns steamdating.services.routes
  (:import goog.History)
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
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


(db/reg-event-fx
  :steamdating.routes/page
  [(re-frame/path :route)]
  (fn routes-page [_ [page params]]
    {:db {:page page
          :params params}}))


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
