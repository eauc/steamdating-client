(ns steamdating.components.nav.toggle
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.icon :refer [icon]]))


(defn nav-toggle
  [{:keys [menu]}]
  [:button.toggle
   {:on-click #(re-frame/dispatch [:sd.ui.menu/set (if-not (= :nav menu) :nav nil)])}
   [icon {:name "more-vertical"}]])
