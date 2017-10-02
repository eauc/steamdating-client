(ns steamdating.pages.players-list
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.filter.input :refer [filter-input]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.page.menu :refer [menu menu-item]]
            [steamdating.components.page.page :refer [content page]]
            [steamdating.components.page.root :as page-root]
            [steamdating.components.player.list :refer [players-list]]
            [steamdating.services.debug :as debug]))


(defroute players-list-route "/players" {}
  (debug/log "route players-list")
  (re-frame/dispatch [:steamdating.routes/page :players-list]))


(defmethod page-root/render :players-list
  []
  [page
   [menu
    [menu-item
     {:on-click #(re-frame/dispatch [:steamdating.players/start-create])}
     "Create Player "
     [icon "user-plus"]]]
   [content
    [:div.sd-PlayersList
     [:h4 "Players"]
     [filter-input {:name :player}]
     [players-list]]]])
