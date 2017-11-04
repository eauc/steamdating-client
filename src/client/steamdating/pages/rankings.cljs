(ns steamdating.pages.rankings
  (:require [re-frame.core :as re-frame]
            [secretary.core :refer [defroute]]
            [steamdating.components.page.content :refer [page-content]]
            [steamdating.components.page.menu :refer [page-menu-items]]
            [steamdating.components.page.menu-item :refer [page-menu-item]]
            [steamdating.components.ranking.bests :refer [ranking-bests]]
            [steamdating.components.ranking.list :refer [ranking-list]]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug]
            [steamdating.services.rankings]))


(defroute rankings "/rankings" {}
  (re-frame/dispatch [:sd.routes/page :rankings]))


(defmethod page-content :rankings
  []
  [:div.sd-page-rankings
   [ranking-list]
   [ranking-bests]])
