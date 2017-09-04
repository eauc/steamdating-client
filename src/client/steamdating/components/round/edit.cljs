(ns steamdating.components.round.edit
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.faction-icon :refer [faction-icon]]
            [steamdating.components.generics.form :refer [form]]
            [steamdating.components.generics.input :refer [input]]
            [steamdating.components.generics.select :refer [select]]
            [steamdating.models.round :as round]))


(defn game-row
  [{:keys [n update-player update-table]} state]
  [:tr
   [:td {:class (when (get-in state [:warn :games n :pairing])
                  "sd-RoundGamesEdit-warning")}
    [select {:field [:games n :player1 :name]
             :form-state state
             :options (get-in state [:edit :players])
             :on-update update-player
             :order (* 3 n)}]]
   [:td.sd-RoundGamesEdit-faction
    {:class (when (get-in state [:warn :games n :faction])
              "sd-RoundGamesEdit-warning")}
    [faction-icon (get-in state [:edit :games n :player1 :faction])]
    [:p]]
   [:td.sd-RoundGamesEdit-table
    [input {:type :number
            :field [:games n :table]
            :form-state state
            :on-update update-table
            :order (+ (* 3 n) 1)}]]
   [:td.sd-RoundGamesEdit-faction
    {:class (when (get-in state [:warn :games n :faction])
              "sd-RoundGamesEdit-warning")}
    [faction-icon (get-in state [:edit :games n :player2 :faction])]
    [:p]]
   [:td {:class (when (get-in state [:warn :games n :pairing])
                  "sd-RoundGamesEdit-warning")}
    [select {:field [:games n :player2 :name]
             :form-state state
             :options (get-in state [:edit :players])
             :on-update update-player
             :order (+ (* 3 n) 2)}]]])


(defn info
  [state]
  [:div.sd-RoundEdit-info
   (for [[key error] (get-in state [:error :global])]
     [:p.sd-RoundEdit-error {:key key}
      error])
   (for [[key warn] (get-in state [:warn :global])]
     [:p.sd-RoundEdit-warning.sd-text-muted {:key key}
      warn])])


(defn edit
  [{:keys [label on-submit]}]
  (let [state @(re-frame/subscribe [:steamdating.rounds/edit])
        update-player #(re-frame/dispatch [:steamdating.rounds/update-edit-player %1 %2])
        update-table #(re-frame/dispatch [:steamdating.forms/update :round %1 %2])]
    [form state {:label label
                 :on-submit #(re-frame/dispatch [on-submit])}
     [:div.sd-RoundEdit
      ;; (pr-str state)
      [:table.sd-RoundEdit-gamesList
       {:style {:border-collapse "collapse"}}
       [:thead
        [:tr
         [:th "Player1"]
         [:th]
         [:th "Table"]
         [:th]
         [:th "Player2"]]]
       [:tbody.sd-RoundGamesEdit
        (for [[n g] (map vector (range) (:games (:edit state)))]
          [game-row {:key n :n n
                     :update-player update-player
                     :update-table update-table} state])]]
      [info state]]]))
