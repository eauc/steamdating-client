(ns steamdating.components.round.menu
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.page.menu :refer [menu menu-item]]
            [steamdating.services.rounds]))


(defn rounds-menu
  [n-round]
  (let [rounds @(re-frame/subscribe [:steamdating.rounds/rounds])]
    [menu
     [menu-item
      {:key "all"
       :href "#/rounds/all"}
      "Summary"]
     (for [[n round] (map vector (range) rounds)]
       [menu-item
        {:key n
         :href (str "#/rounds/nth/" n)}
        (str "Round #" (+ n 1))])
     [menu-item
      {:key "next"
       :active "#/rounds/next"
       :on-click #(re-frame/dispatch [:steamdating.rounds/start-next])}
      "Next round"]
     (when (some? n-round)
       [menu-item
        {:key "delete"
         :on-click #(re-frame/dispatch
                     [:steamdating.prompt/set
                      {:type :confirm
                       :message "Are you sure you want to drop this round ?"
                       :on-validate [:steamdating.rounds/drop-nth n-round]}])}
        [icon "trash-2"]
        " Delete round"])]))
