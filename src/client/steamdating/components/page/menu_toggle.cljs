(ns steamdating.components.page.menu-toggle
	(:require [re-frame.core :as re-frame]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.services.debug :as debug]))


(defn page-menu-toggle
  [{:keys [menu-width show?]}]
  [:div.toggle {:style {:right (if show? menu-width "0px")}
                :on-click #(when-not show? (re-frame/dispatch [:sd.ui.menu/set :page]))}
   [icon {:name "more-horizontal"}]])
