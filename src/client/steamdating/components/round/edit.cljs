(ns steamdating.components.round.edit
  (:require [re-frame.core :as re-frame]
            [steamdating.components.faction.icon :refer [faction-icon]]
            [steamdating.components.generics.form :refer [form]]
            [steamdating.components.generics.input :refer [input]]
            [steamdating.models.round :as round]))


(defn edit
  [{:keys [label on-submit]}]
  (let [state @(re-frame/subscribe [:steamdating.forms/form :round round/validate])
        update-field #(re-frame/dispatch [:steamdating.forms/update :round %1 %2])
        players-options (into {} (map vector (get-in state [:base :players]) (get-in state [:base :players])))]
    [form state {:label label
                 :on-submit on-submit}
     [:div.sd-RoundEdit
      [:table.sd-RoundEdit-gamesList
       [:thead
        [:tr
         [:th "Player1"]
         [:th]
         [:th "Table"]
         [:th]
         [:th "Player2"]]]
       [:tbody.sd-RoundGamesEdit
        (doall
          (for [[n g] (map vector (range) (:games (:edit state)))]
            [:tr {:key n}
             [:td
              [input {:type "select"
                      :field [:player1 :name]
                      :state state
                      :options players-options
                      :on-update update-field}]]
             [:td.faction
              [faction-icon {:name nil}]]
             [:td..sd-RoundGamesEdit-table
              [input {:type "number"
                      :field [:table]
                      :state state
                      :on-update update-field}]]
             [:td.faction
              [faction-icon {:name nil}]]
             [:td
              [input {:type "select"
                      :field [:player2 :name]
                      :state state
                      :options players-options
                      :on-update update-field}]]]))]]]]))
