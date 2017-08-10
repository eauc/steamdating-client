(ns steamdating.components.nav.menu
  (:require [reagent.core :as reagent]
            [steamdating.components.nav.link :refer [link]]
            [steamdating.components.nav.toggle :refer [toggle]]))


(defn menu
  []
  (let [current-hash (reagent/atom (.-hash js/location))
        show (reagent/atom false)
        toggle-show #(swap! show not)]
    (js/addEventListener "hashchange" #(reset! current-hash (.-hash js/location)))
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
              :on-click toggle-show}
        "Players"]
       [:div.sd-NavMenu-actions
        [toggle {:toggle-show toggle-show}]]])))
