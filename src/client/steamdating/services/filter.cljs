(ns steamdating.services.filters
  (:require [re-frame.core :as re-frame]
            [steamdating.models.filter :as filter]
            [steamdating.services.db :as db]))

(db/reg-event-fx
  :sd.filters/set
  [(re-frame/path :filters)]
  (fn set
    [{:keys [db]} [field value]]
    {:db (assoc db field value)}))


(defn filter-sub
  [db [_ field]]
  (get-in db [:filters field]))

(re-frame/reg-sub
  :sd.filters/filter
  filter-sub)

(defn pattern-sub
  [state]
  (filter/->pattern state))

(re-frame/reg-sub
  :sd.filters/pattern
  (fn regexp-sub-inputs
    [[_ field] _]
    (re-frame/subscribe [:sd.filters/filter field]))
  pattern-sub)
