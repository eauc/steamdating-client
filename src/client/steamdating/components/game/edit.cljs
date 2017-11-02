(ns steamdating.components.game.edit
  (:require [steamdating.components.form.form :refer [form]]
            [steamdating.components.form.checkbox :refer [form-checkbox]]
            [steamdating.components.form.input :refer [form-input]]
            [steamdating.components.form.select :refer [form-select]]
            [steamdating.models.form :as form-model]
            [steamdating.models.ui :as ui]
            [steamdating.services.games]
            [re-frame.core :as re-frame]))


(defn player-cell
  [{:keys [on-field-update player state tab-index]}]
  (let [{:keys [options]} state]
    [:td
     [form-select
      (form-model/field-input-props
        {:field [player :name]
         :form state
         :on-update on-field-update
         :options options
         :tab-index tab-index})]]))


(defn players-row
  [{:keys [on-field-update state] :as props}]
  [:tr
   [player-cell
    (assoc props
           :player :player1
           :tab-index 1)]
   [:td.sd-game-edit-center
    [form-input
     (form-model/field-input-props
       {:field [:table]
        :form state
        :on-update on-field-update
        :tab-index 6
        :type :number})]]
   [player-cell
    (assoc props
           :player :player2
           :tab-index 7)]])


(defn result-cell
  [{:keys [on-toggle-win-loss player state]}]
  (let [tp (get-in state [:edit player :score :tournament])]
    [:td {:on-click #(on-toggle-win-loss player)}
     [:div.sd-game-edit-result
      {:class (ui/classes (when (= 0 tp) "loss")
                          (when (= 1 tp) "win"))}
      [:div.sd-game-edit-result-status
       (case tp
         0 "Loser"
         1 "Winner"
         "Unknown")]
      [:div.sd-game-edit-result-hint
       (case tp
         0 "(Click to set Winner)"
         1 "(Click to set Draw)"
         "(Click to set Winner)")]]]))


(defn results-row
  [props]
  [:tr
   [result-cell
    (assoc props :player :player1)]
   [:th.sd-game-edit-center
    "Result"]
   [result-cell
    (assoc props :player :player2)]])


(defn list-cell
  [{:keys [autofocus? on-field-update player state tab-index]}]
  (let [{:keys [lists]} state
        plists (get lists (get-in state [:edit player :name]))]
    [:td
     [form-select
      (form-model/field-input-props
        {:autofocus? autofocus?
         :field [player :list]
         :form state
         :on-update on-field-update
         :options (into {} (map vector plists plists))
         :tab-index tab-index})]]))


(defn lists-row
  [props]
  [:tr
   [list-cell
    (assoc props
           :autofocus? true
           :player :player1
           :tab-index 2)]
   [:th.sd-game-edit-center
    "List"]
   [list-cell
    (assoc props
           :player :player2
           :tab-index 8)]])


(defn ck-row
  [{:keys [on-field-update state]}]
  [:tr
   [:td
    [form-checkbox
     (form-model/field-input-props
       {:field [:player1 :score :assassination]
        :form state
        :label "assassination"
        :on-update on-field-update
        :tab-index 3})]]
   [:th.sd-game-edit-center
    "CK"]
   [:td
    [form-checkbox
     (form-model/field-input-props
       {:field [:player2 :score :assassination]
        :form state
        :label "assassination"
        :on-update on-field-update
        :tab-index 9})]]])


(defn score-row
  [{:keys [label on-field-update score state tab-index]}]
  [:tr
   [:td
    [form-input
     (form-model/field-input-props
       {:field [:player1 :score score]
        :form state
        :min 0
        :on-update on-field-update
        :tab-index tab-index
        :type :number})]]
   [:th.sd-game-edit-center
    label]
   [:td
    [form-input
     (form-model/field-input-props
       {:field [:player2 :score score]
        :form state
        :min 0
        :on-update on-field-update
        :tab-index (+ 6 tab-index)
        :type :number})]]])


(defn game-edit-render
  [{:keys [on-submit state] :as props}]
  [form {:label "Edit game"
         :on-submit on-submit
         :state state}
   [:div.sd-game-edit
    [:table.sd-table
     [:thead
      [:tr
       [:th "Player1"]
       [:th.sd-game-edit-center "Table"]
       [:th "Player2"]]]
     [:tbody
      [players-row props]
      [results-row props]
      [lists-row props]
      [ck-row props]
      [score-row
       (assoc props
              :label "CP"
              :score :scenario
              :tab-index 4)]
      [score-row
       (assoc props
              :label "AP"
              :score :army
              :tab-index 5)]]]]])


(defn game-edit
  []
  (let [on-field-update #(re-frame/dispatch [:sd.forms/update :game %1 %2])
        on-submit #(re-frame/dispatch [:sd.games.edit/save])
        on-toggle-win-loss #(re-frame/dispatch [:sd.games.edit/toggle-win-loss %])
        state @(re-frame/subscribe [:sd.games/edit])]
    [game-edit-render {:on-field-update on-field-update
                       :on-submit on-submit
                       :on-toggle-win-loss on-toggle-win-loss
                       :state state}]))
