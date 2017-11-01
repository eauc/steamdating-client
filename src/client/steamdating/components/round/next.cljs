(ns steamdating.components.round.next
  (:require [re-frame.core :as re-frame]
            [steamdating.components.form.form :refer [form]]
            [steamdating.components.form.input :refer [form-input]]
            [steamdating.components.form.select :refer [form-select]]
            [steamdating.components.generics.button :refer [button]]
            [steamdating.components.generics.faction-icon :refer [faction-icon]]
            [steamdating.models.form :refer [field-input-props]]
            [steamdating.services.debug :as debug]
            [steamdating.models.ui :as ui]))


(defn game-row
  [{:keys [error form game n on-player-update on-table-update warn] :as props}]
  (let [{:keys [factions icons options]} form]
    [:tr (-> props
             (dissoc :error :form :game :n :on-player-update :on-table-update :warn))
     [:td.sd-round-next-player
      {:class (ui/classes (when (get error :players) "error")
                          (when (get warn :players) "warn"))}
      [form-select
       (field-input-props
         {:default-value ""
          :field [:games n :player1 :name]
          :form form
          :on-update on-player-update
          :options options})]]
     [:td.sd-round-next-faction
      {:class (when (get-in warn [:faction]) "warn")}
      [faction-icon {:icons icons
                     :name (get factions (get-in game [:player1 :name]))}]]
     [:td.sd-round-next-table
      {:class (when (get-in warn [:table]) "warn")}
      [form-input
       (field-input-props
         {:field [:games n :table]
          :form form
          :min 1
          :on-update on-table-update
          :type :number})]]
     [:td.sd-round-next-faction
      {:class (when (get-in warn [:faction]) "warn")}
      [faction-icon {:icons icons
                     :name (get factions (get-in game [:player2 :name]))}]]
     [:td.sd-round-next-player
      {:class (ui/classes (when (get error :players) "error")
                          (when (get warn :players) "warn"))}
      [form-select
       (field-input-props
         {:default-value ""
          :field [:games n :player2 :name]
          :form form
          :on-update on-player-update
          :options options})]]]))


(defn round-next-render
  [{:keys [on-player-update on-suggest-sr-pairing on-table-update state]}]
  [:div.sd-round-next
   ;; [:p (with-out-str (cljs.pprint/pprint state))]
   [form {:label "Next round"
          :on-submit #(println "submit")
          :state state}
    [:table.sd-table
     [:thead
      [:tr {:style {:text-align :center}}
       [:th.sd-round-next-player "Player1"]
       [:th.sd-round-next-faction]
       [:th.sd-round-next-table "Table"]
       [:th.sd-round-next-faction]
       [:th.sd-round-next-player "Player2"]]]
     [:tbody
      (for [[n game] (map vector (range) (get-in state [:edit :games]))]
        [game-row {:key n
                   :error (get-in state [:error :games n])
                   :form state
                   :game game
                   :n n
                   :on-player-update on-player-update
                   :on-table-update on-table-update
                   :warn (get-in state [:warn :games n])}])]]]])


(defn round-next
  []
  (let [on-player-update #(re-frame/dispatch [:sd.rounds.next/update-player %1 %2])
        on-table-update #(re-frame/dispatch [:sd.forms/update :round %1 %2])
        state @(re-frame/subscribe [:sd.rounds/next])]
    [round-next-render {:on-player-update on-player-update
                        :on-table-update on-table-update
                        :state state}]))
