(ns steamdating.components.player.edit
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.form :refer [form]]
            [steamdating.components.generics.input :refer [input]]
            [steamdating.components.generics.select :refer [select]]
            [steamdating.components.generics.textarea :refer [textarea]]
            [steamdating.models.player :as player]
            [steamdating.services.debug :as debug]))


(defn edit
  [{:keys [label on-submit save-label]}]
  (let [state @(re-frame/subscribe [:steamdating.players/edit])
        factions-names @(re-frame/subscribe [:steamdating.factions/names])
        casters-names @(re-frame/subscribe [:steamdating.players/edit-casters])
        update-field #(re-frame/dispatch [:steamdating.forms/update :player %1 %2])]
    [form state
     {:label label
      :save-label save-label
      :on-submit #(re-frame/dispatch [on-submit])}
     [:div
      [input {:type :text
              :label "Name"
              :field [:name]
              :form-state state
              :on-update update-field
              :required "required"
              :autofocus "autofocus"
              :order "1"}]
      [input {:type :text
              :label "Origin"
              :field [:origin]
              :form-state state
              :on-update update-field
              :order "2"}]
      [select {:label "Faction"
               :field [:faction]
               :form-state state
               :on-update update-field
               :options factions-names
               :order "3"}]
      [select {:label "Lists"
               :field [:lists]
               :form-state state
               :on-update update-field
               :options casters-names
               :multiple "multiple"
               :order "4"}]
      [textarea {:label "Notes"
                 :field [:notes]
                 :form-state state
                 :on-update update-field
                 :order "6"}]]]))
