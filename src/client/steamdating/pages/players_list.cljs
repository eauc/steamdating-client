(ns steamdating.pages.players-list
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.page.menu :refer [menu menu-item]]
            [steamdating.components.page.page :refer [content page]]
            [steamdating.components.page.root :as page-root]
            [steamdating.components.player.list :refer [players-list]]))


(defroute players-list-route "/players" {}
  (println "route players-list")
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
    [players-list]]])
