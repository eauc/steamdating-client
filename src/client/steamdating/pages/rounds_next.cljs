(ns steamdating.pages.rounds-next
  (:require [re-frame.core :as re-frame]
            [secretary.core :refer [defroute]]
            [steamdating.components.page.content :refer [page-content]]
            [steamdating.components.page.menu :refer [page-menu-items]]
            [steamdating.components.page.menu-item :refer [page-menu-item]]
            [steamdating.components.round.next :refer [round-next]]
            [steamdating.components.round.page-menu :refer [round-page-menu]]
            [steamdating.models.form :as form]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug]
            [steamdating.services.rounds]))


(defroute rounds-next "/rounds/next" {}
  (re-frame/dispatch [:sd.routes/page :rounds-next]))


(defmethod page-content :rounds-next
  []
  [:div.sd-page-rounds-next
   [round-next]])


(defmethod page-menu-items :rounds-next
  []
  (let [n-rounds @(re-frame/subscribe [:sd.rounds/count])
        next? (< 0 @(re-frame/subscribe [:sd.players/count]))
        valid? (form/valid? @(re-frame/subscribe [:sd.rounds/next]))]
    (list
      (round-page-menu {:n-rounds n-rounds
                        :next? next?
                        :page :next})
      [:hr {:key :hr}]
      [page-menu-item
       {:key :start
        :disabled? (not valid?)
        :icon "check"
        :label "Start round"
        :on-click #(re-frame/dispatch [:sd.rounds.next/create])}]
      [page-menu-item
       {:key :sr
        :icon "users"
        :label "Suggest SR pairing"
        :on-click #(re-frame/dispatch [:sd.rounds.next/suggest-sr-pairing])}])))
