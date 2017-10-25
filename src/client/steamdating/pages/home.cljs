(ns steamdating.pages.home
  (:require [re-frame.core :as re-frame]
            [secretary.core :refer [defroute]]
            [steamdating.components.page.content :refer [page-content]]
            [steamdating.components.page.menu :refer [page-menu-items]]
            [steamdating.services.debug :as debug]))


(defroute home "/home" {}
  (re-frame/dispatch [:sd.routes/page :home]))


(defmethod page-content :home
  []
  [:div.sd-page-home
   [:h4 "Home"]
   [:p "Welcome Home ! 1"]
   [:p "Welcome Home ! 2"]
   [:p "Welcome Home ! 3"]
   [:p "Welcome Home ! 4"]
   [:p "Welcome Home ! 5"]
   [:p "Welcome Home ! 6"]
   [:p "Welcome Home ! 7"]
   [:p "Welcome Home ! 8"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]
   [:p "Welcome Home !"]])


(defmethod page-menu-items :home
  []
  (list [:div.item {:key 1} "Item 1"]
        [:div.item {:key 2} "Item 2"]
        [:div.item {:key 3} "Item 3"]
        [:div.item {:key 4} "Item 4"]
        [:div.item {:key 5} "Item 5"]
        [:div.item {:key 6} "Item 6"]
        [:div.item {:key 7} "Item 7"]
        [:div.item {:key 8} "Item 8"]))
