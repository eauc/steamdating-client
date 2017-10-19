(ns steamdating.components.round.summary
  (:require [re-frame.core :as re-frame]
            [steamdating.components.filter.input :refer [filter-input]]
            [steamdating.components.generics.faction-icon :refer [faction-icon]]
            [steamdating.components.sort.header :refer [sort-header]]
            [steamdating.models.game :as game]
            [clojure.string :as s]
            [steamdating.models.player :as player]))


(defn rounds-headers
  [n-rounds]
  (for [n (range n-rounds)]
    [:th
     {:key n
      :class "sd-RoundsSummary-nth-header"}
     [:a {:href (str "#/rounds/nth/" n)}
      (str "Round #" (+ n 1))]]))


(defn player-result
  [n result on-click]
  [:td.sd-RoundsSummary-game
   {:key n
    :title (game/game->title (:game result))
    :class (case (get-in result [:score :tournament])
             0 "sd-RoundsSummary-loss"
             1 "sd-RoundsSummary-win"
             nil nil)
    :on-click #(on-click n (:game result))}
   [:div
    (:table result)
    ". "
    (or (:opponent result) "Phantom")]
   [:div.sd-RoundsSummary-list-name.sd-text-muted
    (:list result)]])


(defn lists-status
  [{:keys [played-lists lists]}]
  [:td.sd-RoundsSummary-lists
   {:class (if (= (count played-lists) (count lists))
             "sd-RoundsSummary-lists-ok"
             "sd-RoundsSummary-lists-ko")}
   (s/join ", " played-lists) " / " (count lists)])


(defn player-summary
  [{:keys [n-rounds on-player-click on-result-click]}
   {:keys [name faction lists droped-after played-lists rank results] :as player}]
  [:tr.sd-RoundsSummary-results
   {:key name
    :class (when (some? droped-after)
             "sd-RoundsSummary-droped")}
   [:td rank]
   [:td.sd-RoundsSummary-name
    {:on-click #(on-player-click name)
     :title (player/player->title player)}
    [faction-icon faction]
    " " name]
   [lists-status player]
   (for [[n result] (map vector (range n-rounds) results)]
     (if (and (some? droped-after)
              (> (inc n) droped-after))
       [:td.sd-RoundsSummary-game
        {:key n}
        [:div "Droped"]]
       (player-result n result on-result-click)))])


(defn summary
  [state {:keys [n-rounds sort on-sort-by] :as props}]
  (when (not-empty state)
    [:table.sd-RoundsSummary-list
     [:thead
      [:tr
       [sort-header sort
        {:label "#"
         :name [:rank]
         :on-sort-by on-sort-by}]
       [sort-header sort
        {:label "Player"
         :name [:name]
         :on-sort-by on-sort-by}]
       [:th "Lists"]
       (rounds-headers n-rounds)]]
     [:tbody
      (for [player state]
        (player-summary props player))]]))


(defn summary-component
  []
  (let [n-rounds @(re-frame/subscribe [:steamdating.rounds/n-rounds])
        state @(re-frame/subscribe [:steamdating.rounds/summary])
        sort @(re-frame/subscribe [:steamdating.sorts/sort :rounds-all {:by [:name]}])]
    [:div.sd-RoundsSummary
     [:h4 "Rounds summary"]
     [filter-input
      {:name :rounds-all}]
     [summary state
      {:n-rounds n-rounds
       :sort sort
       :on-sort-by #(re-frame/dispatch [:steamdating.sorts/set :rounds-all %])
       :on-player-click #(re-frame/dispatch [:steamdating.players/start-edit-name %])
       :on-result-click #(re-frame/dispatch [:steamdating.games/start-edit %1 %2])}]
     ;; [:pre (with-out-str (cljs.pprint/pprint state))]
     ]))
