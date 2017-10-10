(ns steamdating.components.nav.menu
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [steamdating.components.nav.link :refer [link]]
            [steamdating.components.nav.toggle :refer [toggle]]
            [steamdating.components.online.online-button :refer [online-button]]
            [steamdating.components.online.online-follow :refer [online-follow-toggle]]
            [steamdating.components.tournament.save-button :refer [save-button]]
            [steamdating.services.debug :refer [debug?]]))


(defn menu
  []
  (let [show (reagent/atom false)
        toggle-show #(swap! show not)]
    (fn menu-component
      []
      (let [{:keys [hash page] :or {hash ""}} @(re-frame/subscribe [:steamdating.routes/route])]
        (if (= :follow page)
          [:div.sd-NavMenu]
          [:div.sd-NavMenu {:class (when @show "sd-NavMenu-show")}
           (when debug?
             [link {:current-hash hash
                    :path "/home"
                    :on-click toggle-show}
              "Home"])
           [link {:current-hash hash
                  :path "/file"
                  :on-click toggle-show}
            "File"]
           [link {:current-hash hash
                  :path "/online"
                  :on-click toggle-show}
            "Online"]
           [link {:current-hash hash
                  :path "/players"
                  :active "#/players"
                  :on-click toggle-show}
            "Players"]
           [link {:current-hash hash
                  :path "/ranking"
                  :on-click toggle-show}
            "Ranking"]
           [link {:current-hash hash
                  :path "/rounds/all"
                  :active "#/rounds"
                  :on-click toggle-show}
            "Rounds"]
           [:div.sd-NavMenu-actions
            [online-follow-toggle]
            [online-button]
            [save-button]
            [toggle {:toggle-show toggle-show}]]])))))
