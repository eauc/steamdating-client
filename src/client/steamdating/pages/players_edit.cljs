(ns steamdating.pages.players-edit
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.page.content :refer [page-content]]
            [steamdating.components.page.menu :refer [page-menu-items]]
            [steamdating.components.page.menu-item :refer [page-menu-item]]
            [steamdating.components.player.edit :refer [player-edit]]
            [steamdating.models.form :as form]
            [steamdating.services.debug :as debug]))


(defroute players-edit "/players/edit" {}
  (re-frame/dispatch [:sd.routes/page :players-edit]))


(defmethod page-content :players-edit
  []
  [player-edit {:label "Edit player"
                :on-submit [:sd.players.edit/save]}])


(defmethod page-menu-items :players-edit
  []
  (let [state @(re-frame/subscribe [:sd.players/edit])
        valid? (form/valid? state)]
    (list
      [page-menu-item
       {:key :create
        :disabled (not valid?)
        :icon "check"
        :label "Save"
        :on-click #(re-frame/dispatch [:sd.players.edit/save])}]
      [page-menu-item
       {:key :delete
        :icon "trash-2"
        :label "Delete"
        :on-click #(re-frame/dispatch
                     [:sd.prompt/set
                      {:type :confirm
                       :message "Delete player.\nYou sure ?"
                       :on-validate [:sd.players.edit/delete]}])}]
      [page-menu-item
       {:key :cancel
        :icon "x"
        :label "Cancel"
        :on-click #(re-frame/dispatch [:sd.routes/back])}])))
