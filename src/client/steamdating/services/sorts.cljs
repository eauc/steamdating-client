(ns steamdating.services.sorts
  (:require [re-frame.core :as re-frame]
            [steamdating.services.db :as db]
            [steamdating.models.sort :as sort]
            [cljs.spec.alpha :as spec]))


(db/reg-event-fx
  :sd.sorts/toggle
  [(re-frame/path :sorts)]
  (fn set
    [{:keys [db]} [key by]]
    {:db (update db key sort/toggle-by by)}))


(defn sort-sub
  [db [_ field def]]
  {:pre [(spec/valid? :sd.db/db db)
         (spec/valid? keyword? field)
         (spec/valid? :sd.sort/sort def)]
   :post [(spec/valid? :sd.sort/sort %)]}
  (get-in db [:sorts field] def))

(re-frame/reg-sub
  :sd.sorts/sort
  sort-sub)
