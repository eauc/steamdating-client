(ns steamdating.pages.rounds-nth
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.page.page :refer [content page]]
            [steamdating.components.page.root :as page-root]
            [steamdating.components.round.menu :refer [rounds-menu]]
            [steamdating.components.round.round :refer [round-component]]
            [steamdating.services.debug :as debug]))


(defroute rounds-next "/rounds/nth/:n" {n :n}
  (debug/log "route rounds-nth" n)
  (re-frame/dispatch [:steamdating.routes/page
                      :rounds-nth {:n (js/parseInt n 10)}]))


(defmethod page-root/render :rounds-nth
  [{{:keys [n]} :params}]
  [page
   [rounds-menu]
   [content
    [round-component n]]])
