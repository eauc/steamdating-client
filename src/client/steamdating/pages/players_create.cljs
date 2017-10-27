(ns steamdating.pages.players-create
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.page.content :refer [page-content]]
            [steamdating.components.page.menu :refer [page-menu-items]]
            [steamdating.components.player.edit :refer [player-edit]]
            [steamdating.services.debug :as debug]
            [steamdating.models.form :as form]))


(defroute players-create "/players/create" {}
  (re-frame/dispatch [:sd.routes/page :players-create]))


(defmethod page-content :players-create
  []
  [:h4 "Create Player"]
  [player-edit {:label "Create player"
                :on-submit [:sd.players/create-current-edit]}])


(defmethod page-menu-items :players-create
  []
  (let [state @(re-frame/subscribe [:sd.players/edit])
        valid? (form/valid? state)]
    (list
      [:button.sd-page-menu-item
       {:key :create
        :class (when-not valid? "disabled")
        :on-click #(re-frame/dispatch [:sd.players.edit/create])}
       [:span "Create "]
       [icon {:name "check"}]]
      [:button.sd-page-menu-item
       {:key :cancel
        :on-click #(re-frame/dispatch [:sd.routes/back])}
       [:span "Cancel "]
       [icon {:name "x"}]])))
