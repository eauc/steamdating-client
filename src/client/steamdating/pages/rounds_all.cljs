(ns steamdating.pages.rounds-all
  (:require [re-frame.core :as re-frame]
            [secretary.core :refer [defroute]]
            [steamdating.components.page.content :refer [page-content]]
            [steamdating.components.page.menu :refer [page-menu-items]]
            [steamdating.components.round.page-menu :refer [round-page-menu]]
            [steamdating.components.round.summary :refer [round-summary]]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug]
            [steamdating.services.rounds]))


(defroute rounds-all "/rounds/all" {}
  (re-frame/dispatch [:sd.routes/page :rounds-all]))


(defmethod page-content :rounds-all
  []
  [:div.sd-page-rounds-all
   [round-summary]])


(defmethod page-menu-items :rounds-all
  []
  (let [n-rounds @(re-frame/subscribe [:sd.rounds/count])
        next? (< 0 @(re-frame/subscribe [:sd.players/count]))]
    (round-page-menu {:next? next?
                      :n-rounds n-rounds
                      :page :all})))
