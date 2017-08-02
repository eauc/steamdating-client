(ns steamdating.components.nav.menu
  (:require [reagent.core :as reagent]
            [steamdating.components.nav.toggle :refer [toggle]]))


(defn menu
  []
  (let [show (reagent/atom false)
        toggle-show #(swap! show not)]
    (fn menu-component
      []
      [:div.sd-NavMenu {:class (when @show "sd-NavMenu-show")}
       [:div.sd-NavMenu-actions
        [toggle {:toggle-show toggle-show}]]])))
