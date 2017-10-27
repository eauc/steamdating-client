(ns steamdating.components.nav.toggle
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.icon :refer [icon]]))


(defn nav-toggle
  [{:keys [menu]}]
  [:button.sd-nav-actions-toggle
   {:on-click #(when-not (= :nav menu) (re-frame/dispatch [:sd.ui.menu/set :nav]))}
   [icon {:name "more-vertical"}]])
