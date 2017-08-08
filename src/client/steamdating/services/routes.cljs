(ns steamdating.services.routes
  (:import goog.History)
  (:require [goog.events :as events]
            [goog.history.EventType :as EventType]
            [secretary.core :as secretary]
            [steamdating.pages.file :as file]
            [steamdating.pages.home :as home]
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
  :page
  [(re-frame/path :page)]
  (fn page-handler [_ [page]]
    {:db page}))
