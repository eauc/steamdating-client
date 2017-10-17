(ns steamdating.pages.follow
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.filter.input :refer [filter-input]]
            [steamdating.components.page.page :refer [content page]]
            [steamdating.components.page.root :as page-root]
            [steamdating.components.player.list :refer [players-list]]
            [steamdating.components.online.subscribe-button :refer [online-subscribe-button]]
            [steamdating.components.ranking.list :refer [ranking-list-component]]
            [steamdating.components.round.round :refer [round-component]]
            [steamdating.services.debug :as debug]))


(defroute follow "/follow/:id" {id :id}
  (debug/log "route followw")
  (re-frame/dispatch [:steamdating.routes/page :follow {:id id}])
  (re-frame/dispatch [:steamdating.online/load-tournament (str "/tournaments/" id) false]))


(defmethod page-root/render :follow
  [{{:keys [id]} :params}]
  [page
   [content
    (let [online @(re-frame/subscribe [:steamdating.tournament/online])]
      [:h3 "Follow " (:name online)])
    [online-subscribe-button]
    [filter-input {:name :follow}]
    [:h4 "Ranking"]
    [ranking-list-component {:edit? false
                             :filter :follow}]
    (let [n-rounds @(re-frame/subscribe [:steamdating.rounds/n-rounds])]
      (for [n (reverse (range n-rounds))]
        [:div {:key n}
         [:h4 "Round #" (inc n)]
         [round-component {:edit? false
                           :filter :follow
                           :n-round n}]]))
    [:h4 "Players"]
    [players-list {:edit? false
                   :filter :follow}]]])
