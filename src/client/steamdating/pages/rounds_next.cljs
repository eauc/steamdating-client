(ns steamdating.pages.rounds-next
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.page.page :refer [content page]]
            [steamdating.components.page.root :as page-root]
            [steamdating.components.round.edit :refer [edit]]
            [steamdating.components.round.menu :refer [rounds-menu]]
            [steamdating.services.debug :as debug]))


(defroute rounds-next "/rounds/next" {}
  (debug/log "route rounds-next")
  (re-frame/dispatch [:steamdating.routes/page :rounds-next]))


(defmethod page-root/render :rounds-next
  []
  [page
   [rounds-menu]
   [content
    [edit {:label "Next round"
           :save-label "Start"
           :on-submit :steamdating.rounds/create-next}]]])
