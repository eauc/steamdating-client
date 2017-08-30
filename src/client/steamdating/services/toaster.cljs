(ns steamdating.services.toaster
  (:require [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame.core :as re-frame]
            [re-frame-tracer.core :refer [tracer]]
            [steamdating.services.db :as db]))


(defonce timeout
  (atom nil))


;; (trace-forms
;;   {:tracer (tracer :color "brown")}

(re-frame/reg-fx
  :steamdating.toaster/timeout
  (fn []
    (when @timeout (js/clearTimeout @timeout))
    (reset! timeout (js/setTimeout
                      #(re-frame/dispatch
                         [:steamdating.toaster/clear]) 1000))))


(db/reg-event-fx
  :steamdating.toaster/set
  [(re-frame/path :toaster)]
  (fn toaster-set
    [_ [toaster]]
    {:db toaster
     :steamdating.toaster/timeout nil}))


(db/reg-event-fx
  :steamdating.toaster/clear
  [(re-frame/path :toaster)]
  (fn toaster-clear
    []
    {:db nil}))


(re-frame/reg-sub
  :toaster
  (fn toaster-sub
    [db _]
    (:toaster db)))

;; )