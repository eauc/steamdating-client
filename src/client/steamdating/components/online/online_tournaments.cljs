(ns steamdating.components.online.online-tournaments
  (:require [cljs.pprint :as pprint]
            [re-frame.core :as re-frame]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.online.online-button :refer [online-button]]))


(defn tournaments-list
  [_ tournaments]
  [:table.sd-OnlineTournaments-list
   [:thead
    [:tr
     [:th "Date"]
     [:th "Name"]
     [:th.sd-OnlineTournaments-updatedAt
      "Updated At"]]]
   [:tbody
    (for [{:keys [_id date link name updatedAt]} tournaments]
      [:tr.sd-OnlineTournaments-item
       {:key _id
        :on-click #(re-frame/dispatch
                     [:steamdating.online/load-tournament link])}
       [:td date]
       [:td name]
       [:td.sd-OnlineTournaments-updatedAt.sd-text-muted
        updatedAt]])]])


(defn online-tournaments
  []
  (let [{:keys [status tournaments] :as state} @(re-frame/subscribe [:steamdating.online/tournaments])]
    [:div.sd-OnlineTournaments
     ;; (with-out-str (pprint/pprint state))
     (case status
       :offline [online-button]
       :loading [:div.sd-OnlineTournaments-loading
                 [icon "loader"] " Loading"]
       :failed [:div.sd-OnlineTournaments-retry
                [:p "Failed"]
                [:button.sd-OnlineTournaments-action
                 {:type :button
                  :on-click #(re-frame/dispatch [:steamdating.online/load-tournaments])}
                 [icon "refresh-cw"]
                 [:span
                  " Retry"]]]
       :loaded [tournaments-list {} tournaments])]))
