(ns steamdating.components.tournament.settings-edit
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.form :refer [form]]
            [steamdating.components.generics.input :refer [input]]
            [steamdating.models.tournament :as tournament]
            [steamdating.services.debug :as debug]))


(defn settings-edit
  []
  (let [state @(re-frame/subscribe [:steamdating.forms/validate :settings tournament/validate-settings])]
    [:div
     [form state {:label "Settings"
                  :on-submit #(re-frame/dispatch [:steamdating.tournament/settings-save])}
      [input {:label "Tables groups size"
              :type :number
              :field [:tables-groups-size]
              :form-state state
              :on-update #(re-frame/dispatch [:steamdating.forms/update :settings %1 %2])
              :order 1
              :min 1}]]]))
