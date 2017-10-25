(ns steamdating.services.ui
  (:require [steamdating.services.db :as db]
            [re-frame.core :as re-frame]))


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


(re-frame/reg-sub
  :sd.ui/menu
  (fn menu-sub
    [db]
    (get-in db [:ui :menu])))


(re-frame/reg-sub
  :sd.ui.nav/menu
  :<- [:sd.ui/menu]
  :<- [:sd.routes/route]
  (fn nav-menu-sub
    [[menu route]]
    {:menu menu
     :route route}))
