(ns steamdating.pages.settings
  (:require [re-frame.core :as re-frame]
            [secretary.core :refer [defroute]]
            [steamdating.components.page.content :refer [page-content]]
            [steamdating.components.page.menu :refer [page-menu-items]]
            [steamdating.components.page.menu-item :refer [page-menu-item]]
            [steamdating.components.tournament.settings-edit :refer [tournament-settings-edit]]
            [steamdating.models.form :as form]
            [steamdating.models.tournament :as tournament]
            [steamdating.services.db :as db]
            [steamdating.services.debug :as debug]
            [steamdating.services.tournament]))


(defroute settings "/settings" {}
  (re-frame/dispatch [:sd.tournament.settings/start-edit])
  (re-frame/dispatch [:sd.routes/page :settings]))


(defmethod page-content :settings
  []
  [:div.sd-settings
   [tournament-settings-edit]])


(defmethod page-menu-items :settings
  []
  (let [valid? (form/valid? @(re-frame/subscribe
                               [:sd.forms/validate :settings tournament/validate-settings]))]
    (list
      [page-menu-item
       {:key :save
        :disabled? (not valid?)
        :icon "check"
        :label "Save"
        :on-click #(re-frame/dispatch [:sd.tournament.settings/save])}]
      [page-menu-item
       {:key :cancel
        :icon "x"
        :label "Cancel"
        :on-click #(re-frame/dispatch [:sd.routes/back])}])))
