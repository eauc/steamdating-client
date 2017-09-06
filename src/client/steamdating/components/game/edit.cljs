(ns steamdating.components.game.edit
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.checkbox :refer [checkbox]]
            [steamdating.components.generics.form :refer [form]]
            [steamdating.components.generics.input :refer [input]]
            [steamdating.components.generics.select :refer [select]]
            [steamdating.models.game :as game]
            [steamdating.services.debug :as debug :refer [debug?]]
            [steamdating.services.games]))


(defn win-loss
  [{:keys [on-click]} score]
  [:td.sd-GameEdit-win-loss
   {:class (case score
             1 "sd-GameEdit-win"
             0 "sd-GameEdit-loss"
             nil nil)
    :on-click on-click}
   (case score
     1 (list [:div {:key "status"} "Winner"]
             [:div {:key "hint"} "(Click to set Draw)"])
     0 (list [:div {:key "status"} "Looser"]
             [:div {:key "hint"} "(Click to set Winner)"])
     nil "Click to set Winner")])


(defn edit
  [{:keys [label on-submit]}]
  (let [state @(re-frame/subscribe [:steamdating.games/edit])
        update-field #(re-frame/dispatch [:steamdating.forms/update :game %1 %2])]
    [:div
     [form state {:label label
                  :on-submit #(re-frame/dispatch [on-submit])}
      [:table.sd-GameEdit
       [:thead
        [:tr
         [:th "Player1"]
         [:th.sd-GameEdit-table
          "Table"]
         [:th "Player2"]]]
       [:tbody
        [:tr
         [:td
          [select {:field [:player1 :name]
                   :form-state state
                   :options (:players state)
                   :on-update update-field
                   :order 0}]]
         [:td.sd-GameEdit-table
          [input {:type :number
                  :field [:table]
                  :form-state state
                  :on-update update-field
                  :order 1}]]
         [:td
          [select {:field [:player2 :name]
                   :form-state state
                   :options (:players state)
                   :on-update update-field
                   :order 2}]]]
        [:tr
         [:td
          [select {:field [:player1 :list]
                   :form-state state
                   :options (get-in state [:lists (get-in state [:edit :player1 :name])])
                   :on-update update-field
                   :order 3}]]
         [:th.sd-GameEdit-table
          [:p "List"]]
         [:td
          [select {:field [:player2 :list]
                   :form-state state
                   :options (get-in state [:lists (get-in state [:edit :player2 :name])])
                   :on-update update-field
                   :order 3}]]]
        [:tr
         [win-loss {:on-click #(re-frame/dispatch
                                 [:steamdating.games/edit-toggle-win-loss :player1])}
          (get-in state [:edit :player1 :score :tournament])]
         [:th.sd-GameEdit-table]
         [win-loss {:on-click #(re-frame/dispatch
                                 [:steamdating.games/edit-toggle-win-loss :player2])}
          (get-in state [:edit :player2 :score :tournament])]]
        [:tr
         [:td
          [checkbox {:field [:player1 :score :assassination]
                     :form-state state
                     :label "assassination"
                     :on-update update-field
                     :order 5}]]
         [:th.sd-GameEdit-table]
         [:td
          [checkbox {:field [:player2 :score :assassination]
                     :form-state state
                     :label "assassination"
                     :on-update update-field
                     :order 6}]]]
        [:tr
         [:td
          [input {:type :number
                  :field [:player1 :score :scenario]
                  :form-state state
                  :on-update update-field
                  :order 7
                  :min 0}]]
         [:th.sd-GameEdit-table
          [:p "CP"]]
         [:td
          [input {:type :number
                  :field [:player2 :score :scenario]
                  :form-state state
                  :on-update update-field
                  :order 8
                  :min 0}]]]
        [:tr
         [:td
          [input {:type :number
                  :field [:player1 :score :army]
                  :form-state state
                  :on-update update-field
                  :order 9
                  :min 0}]]
         [:th.sd-GameEdit-table
          [:p "AP"]]
         [:td
          [input {:type :number
                  :field [:player2 :score :army]
                  :form-state state
                  :on-update update-field
                  :order 10
                  :min 0}]]]]]]
     (when debug?
       [:button {:type "button"
                 :on-click #(re-frame/dispatch [:steamdating.games/edit-random])}
        "Random"])
     ;; [:div (pr-str state)]
     ]))
