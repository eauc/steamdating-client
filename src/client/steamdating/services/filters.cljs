(ns steamdating.services.filters
  (:require [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame.core :as re-frame]
            [re-frame-tracer.core :refer [tracer]]
            [steamdating.models.filter :as filter]
            [steamdating.services.db :as db]))


;; (trace-forms
;;   {:tracer (tracer :color "gold")}

(db/reg-event-fx
  :steamdating.filters/set
  [(re-frame/path :filters)]
  (fn set
    [{:keys [db]} [field value]]
    {:db (assoc db field value)}))


(re-frame/reg-sub
  :steamdating.filters/filter
  (fn filter-sub
    [db [_ field]]
    (get-in db [:filters field])))


(re-frame/reg-sub
  :steamdating.filters/pattern
  (fn regexp-sub-inputs
    [[_ field] _]
    (re-frame/subscribe [:steamdating.filters/filter field]))
  (fn regexp-sub
    [state _]
    (filter/filter->regexp state)))

;; )
