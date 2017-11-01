(ns steamdating.pages.players-create
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.page.content :refer [page-content]]
            [steamdating.components.page.menu :refer [page-menu-items]]
            [steamdating.components.page.menu-item :refer [page-menu-item]]
            [steamdating.components.player.edit :refer [player-edit]]
            [steamdating.models.form :as form]
            [steamdating.services.debug :as debug]))


(defroute players-create "/players/create" {}
  (re-frame/dispatch [:sd.routes/page :players-create]))


(defmethod page-content :players-create
  []
  [player-edit {:label "Create player"
                :on-submit [:sd.players.edit/create]}])


(defmethod page-menu-items :players-create
  []
  (let [state @(re-frame/subscribe [:sd.players/edit])
        valid? (form/valid? state)]
    (list
      [page-menu-item
       {:key :create
        :disabled? (not valid?)
        :icon "check"
        :label "Create"
        :on-click #(re-frame/dispatch [:sd.players.edit/create])}]
      [page-menu-item
       {:key :cancel
        :icon "x"
        :label "Cancel"
        :on-click #(re-frame/dispatch [:sd.routes/back])}])))
