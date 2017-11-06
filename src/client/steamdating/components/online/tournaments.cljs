(ns steamdating.components.online.tournaments
  (:require [re-frame.core :as re-frame]
            [steamdating.components.form.input :refer [form-input]]
            [steamdating.components.generics.button :refer [button]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.generics.sort-header :refer [sort-header]]
            [steamdating.services.debug :as debug]))


(defn online-tournaments-render
  [{:keys [on-filter-update on-sort-by on-tournament-click state]}]
  (let [{:keys [filter list sort]} state]
    [:div.sd-online-tournaments
     [:table.sd-table
      [:caption
       [:div.sd-table-caption
        [:div.sd-table-caption-label "Online tournaments"]
        [form-input {:on-update on-filter-update
                     :placeholder "Filter"
                     :value filter}]]]
      [:thead
       [:tr
        [sort-header {:col :date
                      :on-sort-by on-sort-by
                      :state sort}]
        [sort-header {:col :name
                      :on-sort-by on-sort-by
                      :state sort}]
        [:th.sd-online-tournaments-updated-at
         "Updated at"]]]
      [:tbody
       (for [{:keys [date _id name updatedAt] :as tournament} list]
         [:tr {:key _id
               :on-click #(on-tournament-click tournament)}
          [:td date]
          [:td name]
          [:td.sd-online-tournaments-updated-at
           updatedAt]])]]
     [:p.sd-online-tournaments-hint
      "Click on a tournament to download it"]]))


(defn load-error
  []
  [:div.sd-online-tournaments
   [:div.sd-online-tournaments-error
    [:p "Failed to load online tournaments"]
    [button {:icon "refresh-cw"
             :label "Try again"
             :on-click #(re-frame/dispatch [:sd.online.tournaments/load])}]]])


(defn loading
  []
  [:div.sd-online-tournaments
   [:div.sd-online-tournaments-info
    [icon {:name "loader"}]
    [:p "Loading online tournaments"]]])


(defn online-tournaments
  []
  (let [{:keys [status] :as state} @(re-frame/subscribe [:sd.online/tournaments])
        on-filter-update #(re-frame/dispatch [:sd.filters/set :online-tournaments %])
        on-sort-by #(re-frame/dispatch [:sd.sorts/toggle :online-tournaments % :date])
        on-tournament-click #(re-frame/dispatch [:sd.online.tournament/load (assoc % :confirm? true)])]
    (case status
      :error [load-error]
      :loading [loading]
      :success [online-tournaments-render
                {:on-filter-update on-filter-update
                 :on-sort-by on-sort-by
                 :on-tournament-click on-tournament-click
                 :state state}]
      [:div])))
