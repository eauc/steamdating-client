(ns steamdating.pages.ranking
  (:require [clojure.string :as s]
            [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.filter.input :refer [filter-input]]
            [steamdating.components.generics.faction-icon :refer [faction-icon]]
            [steamdating.components.page.menu :refer [menu menu-item]]
            [steamdating.components.page.page :refer [content page]]
            [steamdating.components.page.root :as page-root]
            [steamdating.components.ranking.bests :refer [bests-component]]
            [steamdating.components.ranking.list :refer [ranking-list-component]]
            [steamdating.services.debug :as debug]))


(defroute ranking-route "/ranking" {}
  (debug/log "route ranking")
  (re-frame/dispatch [:steamdating.routes/page :ranking]))


(defmethod page-root/render :ranking
  []
  [page
   [content
    [:div.sd-Ranking
     [:h4 "Ranking"]
     [filter-input {:name :ranking}]
     [ranking-list-component]
     [:h4 "Bests"]
     [bests-component]]]])
