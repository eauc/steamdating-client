(ns steamdating.components.nav.actions
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [steamdating.components.nav.toggle :refer [nav-toggle]]
            [steamdating.components.online.login-button :refer [online-login-button]]
            [steamdating.components.online.upload-button :refer [online-upload-button]]
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
  (let [tournament-status @(re-frame/subscribe [:sd.online.tournament/status])]
    [:div.sd-nav-actions-content
     (when (= :online tournament-status)
       [online-upload-button])
     [online-login-button]
     [tournament-save-button]
     [nav-toggle {:menu menu}]]))
