(ns steamdating.components.player.edit
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.form :refer [form]]
            [steamdating.components.generics.input :refer [input]]
            [steamdating.models.player]))


(defn edit
  [{:keys [label on-submit]}]
  (let [factions-names (re-frame/subscribe [:steamdating.factions/names])
        casters-names (re-frame/subscribe [:steamdating.players/edit-casters])
        name-error (re-frame/subscribe [:steamdating.players/edit-name-error])]
    (fn edit-component
      []
      [form {:name :player
             :label label
             :on-submit on-submit
             :spec :steamdating.player/player
             :error (boolean @name-error)}
       [input {:name :name
               :label "Name"
               :type "text"
               :required "required"
               :autofocus "autofocus"
               :order "1"
               :error @name-error}]
       [input {:name :origin
               :label "Origin"
               :type "text"
               :order "2"}]
       [input {:name :faction
               :label "Faction"
               :type "select"
               :options @factions-names
               :order "3"}]
       [input {:name :lists
               :label "Lists"
               :type "select"
               :options @casters-names
               :multiple "multiple"
               :order "4"}]
       [input {:name :notes
               :label "Notes"
               :type "textarea"
               :order "6"}]])))
