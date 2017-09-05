(ns steamdating.pages.players-create
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.player.edit :refer [edit]]
            [steamdating.components.page.menu :refer [menu menu-item]]
            [steamdating.components.page.page :refer [content page]]
            [steamdating.components.page.root :as page-root]
            [steamdating.services.debug :as debug]))


(defroute players-create "/players/create" {}
  (debug/log "route players-create")
  (re-frame/dispatch [:steamdating.routes/page :players-create]))


(defmethod page-root/render :players-create
  []
  [page
   [menu
    [menu-item
     {:on-click #(re-frame/dispatch [:steamdating.routes/back])}
     "Cancel "
     [icon "x"]]]
   [content
    [edit {:label "Create player"
           :save-label "Create"
           :on-submit :steamdating.players/create-current-edit}]]])
