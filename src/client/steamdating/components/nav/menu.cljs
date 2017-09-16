(ns steamdating.components.nav.menu
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [steamdating.components.nav.link :refer [link]]
            [steamdating.components.nav.toggle :refer [toggle]]
            [steamdating.components.tournament.save-button :refer [save-button]]))


(defn menu
  []
  (let [current-hash (re-frame/subscribe [:steamdating.routes/hash])
        show (reagent/atom false)
        toggle-show #(swap! show not)]
    (fn menu-component
      []
      [:div.sd-NavMenu {:class (when @show "sd-NavMenu-show")}
       [link {:current-hash @current-hash
              :path "/home"
              :on-click toggle-show}
        "Home"]
       [link {:current-hash @current-hash
              :path "/file"
              :on-click toggle-show}
        "File"]
       [link {:current-hash @current-hash
              :path "/players"
              :active "#/players"
              :on-click toggle-show}
        "Players"]
       [link {:current-hash @current-hash
              :path "/ranking"
              :on-click toggle-show}
        "Ranking"]
       [link {:current-hash @current-hash
              :path "/rounds/all"
              :active "#/rounds"
              :on-click toggle-show}
        "Rounds"]
       [:div.sd-NavMenu-actions
        [save-button]
        [toggle {:toggle-show toggle-show}]]])))
