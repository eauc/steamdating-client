(ns steamdating.pages.file
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.page.page :refer [content page]]
            [steamdating.components.page.root :as page-root]
            [steamdating.components.tournament.new-button :refer [new-button]]
            [steamdating.components.tournament.open-button :refer [open-button]]
            [steamdating.services.debug :as debug]))


(defroute file "/file" {}
  (debug/log "route file")
  (re-frame/dispatch [:steamdating.routes/page :file]))


(defmethod page-root/render :file
  []
  [page {:class "sd-FilePage"}
   [content
    [:h3.sd-FilePage-header "Files"]
    [:div.sd-FilePage-fileActions
     [new-button]
     [open-button]]]])
