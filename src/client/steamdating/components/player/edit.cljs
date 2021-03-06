(ns steamdating.components.player.edit
  (:require [re-frame.core :as re-frame]
            [steamdating.components.form.form :refer [form]]
            [steamdating.components.form.input :refer [form-input]]
            [steamdating.components.form.select :refer [form-select]]
            [steamdating.models.form :as form]
            [steamdating.models.player :as player]
            [steamdating.services.debug :as debug]
            [steamdating.services.factions]
            [steamdating.services.players]))


(defn player-edit-render
  [{:keys [label on-submit on-update-field state]}]
  (let [{:keys [factions casters]} state]
    [form {:label label
           :on-submit on-submit
           :state state}
     [:div
      ;; [:p (with-out-str (cljs.pprint/pprint state))]
      [form-input
       (form/field-input-props
         {:autofocus? true
          :field [:name]
          :form state
          :label "Name"
          :on-update on-update-field
          :order "1"
          :required "required"
          :type :text})]
      [form-input
       (form/field-input-props
         {:field [:origin]
          :form state
          :label "Origin"
          :on-update on-update-field
          :order "2"
          :type :text})]
      [form-select
       (form/field-input-props
         {:default-value ""
          :field [:faction]
          :form state
          :label "Faction"
          :on-update on-update-field
          :options factions
          :order "3"})]
      [form-select
       (form/field-input-props
         {:default-value []
          :field [:lists]
          :form state
          :label "Lists"
          :multiple "multiple"
          :on-update on-update-field
          :options casters
          :order "4"})]
      [form-input
       (form/field-input-props
         {:element :textarea
          :field [:notes]
          :form state
          :label "Notes"
          :on-update on-update-field
          :order "5"})]]]))


(defn player-edit
  [{:keys [label on-submit]}]
  (let [state (re-frame/subscribe [:sd.players/edit])
        on-submit #(re-frame/dispatch on-submit)
        on-update-field #(re-frame/dispatch [:sd.forms/update :player %1 %2])]
    (fn []
      [player-edit-render
       {:label label
        :on-submit on-submit
        :on-update-field on-update-field
        :state @state}])))
