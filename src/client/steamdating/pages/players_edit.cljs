(ns steamdating.pages.players-edit
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.page.content :refer [page-content]]
            [steamdating.components.page.menu :refer [page-menu-items]]
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
      [:button.sd-page-menu-item
       {:key :create
        :class (when-not valid? "disabled")
        :on-click #(re-frame/dispatch [:sd.players.edit/save])}
       [:span "Save "]
       [icon {:name "save"}]]
      [:button.sd-page-menu-item
       {:key :delete
        :on-click #(re-frame/dispatch
                     [:sd.prompt/set
                      {:type :confirm
                       :message "Delete player.\nYou sure ?"
                       :on-validate [:sd.players.edit/delete]}])}
       [:span "Delete "]
       [icon {:name "trash-2"}]]
      [:button.sd-page-menu-item
       {:key :cancel
        :on-click #(re-frame/dispatch [:sd.routes/back])}
       [:span "Cancel "]
       [icon {:name "x"}]])))
