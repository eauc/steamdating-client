(ns steamdating.pages.rounds-nth
  (:require [re-frame.core :as re-frame]
            [secretary.core :refer [defroute]]
            [steamdating.components.page.content :refer [page-content]]
            [steamdating.components.page.menu :refer [page-menu-items]]
            [steamdating.components.page.menu-item :refer [page-menu-item]]
            [steamdating.components.round.nth :refer [round-nth]]
            [steamdating.components.round.page-menu :refer [round-page-menu]]
            [steamdating.services.debug :as debug :refer [debug?]]
            [steamdating.services.rounds]))


(defroute rounds-nth "/rounds/nth/:n" {n :n}
  (re-frame/dispatch [:sd.routes/page
                      :rounds-nth {:n (js/parseInt n 10)}]))


(defmethod page-content :rounds-nth
  [{{{:keys [n]} :params} :route}]
  [:div.sd-page-rounds-nth
   [round-nth {:n n}]])


(defmethod page-menu-items :rounds-nth
  [{{{:keys [n]} :params} :route}]
  (let [n-rounds @(re-frame/subscribe [:sd.rounds/count])
        next? (< 0 @(re-frame/subscribe [:sd.players/count]))]
    (list
      (round-page-menu {:n-rounds n-rounds
                        :next? next?
                        :page n})
      [:hr {:key :hr}]
      (when debug?
        [page-menu-item
         {:key :random
          :icon "shuffle"
          :label "Random scores"
          :on-click #(re-frame/dispatch
                       [:sd.rounds.nth/random-score n])}])
      [page-menu-item
       {:key :delete
        :icon "trash-2"
        :label "Delete round"
        :on-click #(re-frame/dispatch
                     [:sd.prompt/set
                      {:type :confirm
                       :message (str "Drop round #" (inc n) "\nYou sure ?")
                       :on-validate [:sd.rounds/drop-nth n]}])}])))
