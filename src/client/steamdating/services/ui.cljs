(ns steamdating.services.ui
  (:require [cljs.spec.alpha :as spec]
            [re-frame.core :as re-frame]
            [steamdating.services.db :as db]))


(defn ui-init
  []
  (some-> js.document
          (.querySelector ".sd")
          (.addEventListener "click" #(re-frame/dispatch [:sd.ui.menu/set nil]) true)))


(db/reg-event-fx
  :sd.ui.menu/set
  [(re-frame/path [:ui :menu])]
  (fn menu-set
    [_ [menu]]
    {:db menu}))


(defn menu-sub
  [db]
  {:pre [(spec/valid? :sd.db/db db)]
   :post [(spec/valid? :sd.ui/menu %)]}
  (get-in db [:ui :menu]))

(re-frame/reg-sub
  :sd.ui/menu
  menu-sub)


(defn menu-route-sub
  [[menu route]]
  {:pre [(spec/valid? :sd.ui/menu menu)
         (spec/valid? :sd.route/route route)]
   :post [(spec/valid? :sd.ui/menu-route %)]}
  {:menu menu
   :route route})

(re-frame/reg-sub
  :sd.ui/menu-route
  :<- [:sd.ui/menu]
  :<- [:sd.routes/route]
  menu-route-sub)
