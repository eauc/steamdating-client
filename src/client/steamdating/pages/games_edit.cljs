(ns steamdating.pages.games-edit
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.page.content :refer [page-content]]
            [steamdating.components.page.menu :refer [page-menu-items]]
            [steamdating.components.page.menu-item :refer [page-menu-item]]
            [steamdating.components.game.edit :refer [game-edit]]
            [steamdating.models.form :as form]
            [steamdating.services.debug :as debug :refer [debug?]]))


(defroute games-edit "/games/edit" {}
  (re-frame/dispatch [:sd.routes/page :games-edit]))


(defmethod page-content :games-edit
  []
  [game-edit])


(defmethod page-menu-items :games-edit
  []
  (let [state @(re-frame/subscribe [:sd.games/edit])
        valid? (form/valid? state)]
    (list
      [page-menu-item
       {:key :save
        :disabled (not valid?)
        :icon "check"
        :label "Save game"
        :on-click #(re-frame/dispatch [:sd.games.edit/save])}]
      (when debug?
        [page-menu-item
         {:key :random
          :icon "shuffle"
          :label "Random score"
          :on-click #(re-frame/dispatch [:sd.games.edit/random-score])}])
      [page-menu-item
       {:key :cancel
        :icon "x"
        :label "Cancel"
        :on-click #(re-frame/dispatch [:sd.routes/back])}])))
