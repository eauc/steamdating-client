(ns steamdating.components.tournament.settings-edit
  (:require [re-frame.core :as re-frame]
            [steamdating.components.form.form :refer [form]]
            [steamdating.components.form.input :refer [form-input]]
            [steamdating.models.form :as form-model]
            [steamdating.models.tournament :as tournament]))


(defn tournament-settings-edit-render
  [{:keys [on-submit on-update state]}]
  [form {:label "Edit settings"
         :on-submit on-submit
         :state state}
   [:div.sd-tournament-settings-edit
    [form-input
     (form-model/field-input-props
       {:field [:tables-groups-size]
        :form state
        :label "Tables groups size"
        :min 1
        :on-update on-update
        :type :number})]]])


(defn tournament-settings-edit
  []
  (let [state @(re-frame/subscribe
                 [:sd.forms/validate :settings tournament/validate-settings])
        on-submit #(re-frame/dispatch [:sd.tournament.settings/save])
        on-update #(re-frame/dispatch [:sd.forms/update :settings %1 %2])]
    [tournament-settings-edit-render
     {:on-submit on-submit
      :on-update on-update
      :state state}]))
