(ns steamdating.components.round.page-menu
  (:require [re-frame.core :as re-frame]
            [steamdating.components.page.menu-item :refer [page-menu-item]]
            [steamdating.services.debug :as debug]
            [steamdating.services.rounds]))


(defn round-page-menu
  [{:keys [next? n-rounds page]}]
  (list
    [page-menu-item
     {:key :all
      :active? (= page :all)
      :label "Summary"
      :on-click #(re-frame/dispatch [:sd.routes/navigate "/rounds/all"])}]
    (for [n (range n-rounds)]
      [page-menu-item
       {:key n
        :active? (= page n)
        :label (str "Round #" (inc n))
        :on-click #(re-frame/dispatch [:sd.routes/navigate (str "rounds/nth/" n)])}])
    (when next?
      [page-menu-item
       {:key :next
        :active? (= page :next)
        :label "Next round"
        :on-click #(re-frame/dispatch [:sd.rounds.next/start])}])))
