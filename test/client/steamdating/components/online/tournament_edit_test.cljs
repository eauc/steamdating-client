(ns steamdating.components.online.tournament-edit-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [steamdating.components.online.tournament-edit :refer [online-tournament-edit-render]]
            [steamdating.components.form.form :refer [form]]
            [steamdating.components.form.input :refer [form-input]]
            [steamdating.components.generics.button :refer [button]]
            [steamdating.models.form :as form-model]
            [steamdating.models.online :as online]
            [steamdating.models.ui :as ui]
            [steamdating.services.debug :as debug]))


(defcard-rg online-tournament-edit-test.
  "Online tournament edit component"


  (fn [state]
    (let [on-field-update #(swap! state update :form form-model/assoc-field %1 %2)
          on-submit #(println "submit!")
          form-state (form-model/validate :sd.online.tournament/edit (:form @state))]

      [online-tournament-edit-render
       {:on-field-update on-field-update
        :on-submit on-submit
        :state state}]))


  (reagent/atom
    {:form {:base {}
            :edit {}}})


  {:inspect-data true
   :history true})
