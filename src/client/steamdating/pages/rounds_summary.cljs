(ns steamdating.pages.rounds-summary
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.page.page :refer [content page]]
            [steamdating.components.page.root :as page-root]
            [steamdating.components.round.menu :refer [rounds-menu]]
            [steamdating.components.round.summary :refer [summary-component]]
            [steamdating.services.debug :as debug]))


(defroute rounds-summary-route "/rounds/all" {}
  (debug/log "routes rounds-summary")
  (re-frame/dispatch [:steamdating.routes/page :rounds-summary]))


(defmethod page-root/render :rounds-summary
  []
  [page
   [rounds-menu]
   [content
    [summary-component]]])
