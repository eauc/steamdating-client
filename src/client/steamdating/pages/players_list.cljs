(ns steamdating.pages.players-list
  (:require [re-frame.core :as re-frame]
            [secretary.core :refer [defroute]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.page.content :refer [page-content]]
            [steamdating.components.page.menu :refer [page-menu-items]]
            [steamdating.components.page.menu-item :refer [page-menu-item]]
            [steamdating.components.player.list :refer [player-list]]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug]
            [steamdating.services.players]))


(defroute players-list "/players" {}
  (re-frame/dispatch [:sd.routes/page :players-list]))


(defmethod page-content :players-list
  []
  [:div.sd-page-players-list
   [player-list]])


(defmethod page-menu-items :players-list
  []
  (list
    [page-menu-item
     {:key :create
      :icon "user-plus"
      :label "Create player"
      :on-click #(re-frame/dispatch [:sd.players.edit/start-create])}]))
