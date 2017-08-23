(ns steamdating.pages.players-edit
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.player.edit :refer [edit]]
            [steamdating.components.page.menu :refer [menu menu-item]]
            [steamdating.components.page.page :refer [content page]]
            [steamdating.components.page.root :as page-root]))


(defroute players-edit "/players/edit" {}
  (println "route players-edit")
  (re-frame/dispatch [:steamdating.routes/page :players-edit]))


(defmethod page-root/render :players-edit
  []
  [page
   [menu
    [menu-item
     {:on-click #(re-frame/dispatch [:steamdating.routes/back])}
     "Cancel "
     [icon "close"]]
    [menu-item
     {:on-click #(re-frame/dispatch
                   [:steamdating.prompt/set
                    {:type :confirm
                     :message "Delete this player ?"
                     :on-validate [:steamdating.players/delete-current-edit]}])}
     "Delete "
     [icon "trash"]]]
   [content
    [edit {:label "Edit player"
           :on-submit :steamdating.players/update-current-edit}]]])
