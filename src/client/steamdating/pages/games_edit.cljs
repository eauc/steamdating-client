(ns steamdating.pages.games-edit
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.game.edit :refer [edit]]
            [steamdating.components.page.menu :refer [menu menu-item]]
            [steamdating.components.page.page :refer [content page]]
            [steamdating.components.page.root :as page-root]
            [steamdating.services.debug :as debug]))


(defroute games-edit "/games/edit" {}
  (debug/log "routes games-edit")
  (re-frame/dispatch [:steamdating.routes/page :games-edit]))


(defmethod page-root/render :games-edit
  []
  [page
   [menu
    [menu-item
     {:on-click #(re-frame/dispatch [:steamdating.routes/back])}
     "Cancel "
     [icon "x"]]]
   [content
    [edit {:label "Edit game"
           :on-submit :steamdating.games/update-current-edit}]]])
