(ns steamdating.services.routes
  (:import goog.History)
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [secretary.core :as secretary]
            [steamdating.pages.file :as file]
            [steamdating.pages.home :as home]
            [steamdating.pages.players-create :as players-create]
            [steamdating.pages.players-list :as players-list]
            [steamdating.services.db :as db]
            [re-frame.core :as re-frame]))


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
  [(re-frame/path :page)]
  (fn routes-page [_ [page]]
    {:db page}))


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
