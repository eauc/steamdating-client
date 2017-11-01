(ns steamdating.components.nav.actions
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [steamdating.components.nav.toggle :refer [nav-toggle]]
            [steamdating.components.tournament.save-button :refer [tournament-save-button]]
            [steamdating.services.routes]
            [steamdating.services.ui]))


(defmulti nav-actions-content #(get-in % [:route :page]))


(defn nav-actions
  []
  (let [state (re-frame/subscribe [:sd.ui/menu-route])]
    (fn nav-actions-render
      []
      (nav-actions-content @state))))


(defmethod nav-actions-content :default
  [{:keys [menu]}]
  [:div.sd-nav-actions-content
   [tournament-save-button]
   [nav-toggle {:menu menu}]])
