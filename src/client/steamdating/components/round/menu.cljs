(ns steamdating.components.round.menu
  (:require [re-frame.core :as re-frame]
            [steamdating.components.page.menu :refer [menu menu-item]]
            [steamdating.services.rounds]))


(defn rounds-menu
  []
  (let [rounds @(re-frame/subscribe [:steamdating.rounds/rounds])]
    [menu
     (for [[n round] (map vector (range) rounds)]
       [menu-item
        {:key n
         :on-click #(re-frame/dispatch
                      [:steamdating.routes/navigate
                       (str "/rounds/nth/" n)])}
        (str "Round #" (+ n 1))])
     [menu-item
      {:key "next"
       :on-click #(re-frame/dispatch [:steamdating.rounds/start-next])}
      "Next round"]]))
