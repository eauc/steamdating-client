(ns steamdating.components.page.content
	(:require [re-frame.core :as re-frame]
						[steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.page.menu :refer [page-menu]]
            [steamdating.services.debug :as debug]))


(defmulti page-content #(get-in % [:route :page]))


(defn page
	[]
	[:div.container
	 [:div.content
    (page-content @(re-frame/subscribe [:sd.ui/menu-route]))]
	 [page-menu]])


(defmethod page-content :default
  []
  [:div.sd-page-unknown
   [:p "Unkown page"]])
