(ns steamdating.components.round.edit
  (:require [re-frame.core :as re-frame]
            [steamdating.components.faction.icon :refer [faction-icon]]
            [steamdating.components.generics.form :refer [form]]
            [steamdating.components.generics.input :refer [input]]
            [steamdating.models.round :as round]))


(defn edit
  [{:keys [label on-submit]}]
  (let [state @(re-frame/subscribe [:steamdating.rounds/edit])
        update-field #(re-frame/dispatch [:steamdating.forms/update :round %1 %2])]
    [form state {:label label
                 :on-submit #(re-frame/dispatch [on-submit])}
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
                      :field [:games n :player1 :name]
                      :state state
                      :options (get-in state [:edit :players])
                      :on-update update-field
                      :order (* 3 n)}]]
             [:td.sd-RoundGamesEdit-faction
              [faction-icon {:faction (get-in state [:edit :games n :player1 :faction])}]]
             [:td.sd-RoundGamesEdit-table
              [input {:type "number"
                      :field [:games n :table]
                      :state state
                      :on-update update-field
                      :order (+ (* 3 n) 1)}]]
             [:td.sd-RoundGamesEdit-faction
              [faction-icon {:faction (get-in state [:edit :games n :player2 :faction])}]]
             [:td
              [input {:type "select"
                      :field [:games n :player2 :name]
                      :state state
                      :options (get-in state [:edit :players])
                      :on-update update-field
                      :order (+ (* 3 n) 2)}]]]))]]]]))
