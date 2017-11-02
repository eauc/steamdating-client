(ns steamdating.components.game.edit-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [steamdating.components.game.edit :refer [game-edit-render]]
            [steamdating.components.form.form :refer [form]]
            [steamdating.components.form.checkbox :refer [form-checkbox]]
            [steamdating.components.form.input :refer [form-input]]
            [steamdating.components.form.select :refer [form-select]]
            [steamdating.models.form :as form-model]
            [steamdating.models.game :as game]
            [steamdating.models.ui :as ui]))


(defcard-rg game-edit-test
  "Game edit component"


  (fn [state]
    [:div
     [:button.sd-button
      {:type :button
       :on-click #(swap! state update-in [:form :edit]
                         game/random-score (get-in @state [:form :lists]))}
      "Random"]
     [game-edit-render
      {:on-field-update #(swap! state update :form form-model/assoc-field %1 %2)
       :on-submit #(println "submit")
       :on-toggle-win-loss #(swap! state update-in [:form :edit] game/toggle-win-loss %)
       :state (:form @state)}]])


  (reagent/atom
    {:form {:base {}
            :edit {:table 4
                   :player1 {:name "tete"}
                   :player2 {:name "tyty"}}
            :options {"tete" "tete"
                      "teuteu" "teuteu"
                      "Toto" "Toto"
                      "toutou" "toutou"
                      "tyty" "tyty"}
            :lists {"tyty" #{"Malekus1" "Thyra1"}
                    "toto" #{"Bethayne1" "Saeryn1"}
                    "tete" #{"Koslov1" "Vladimir1"}
                    "titi" #{"Feora1" "Harbinger1"}
                    "teuteu" #{"Thyron1" "Viros1"}
                    "tutu" #{"Bartolo1" "Magnus2"}
                    "toutou" #{"Absylonia1" "Thagrosh1"}}}})


  {:inspect-data true
   :history true})
