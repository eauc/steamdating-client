(ns steamdating.pages.rounds-summary
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.page.menu :refer [menu menu-item]]
            [steamdating.components.page.page :refer [content page]]
            [steamdating.components.page.root :as page-root]
            [steamdating.services.rounds]))


(defroute rounds-summary-route "/rounds/all" {}
  (println "routes rounds-summary")
  (re-frame/dispatch [:steamdating.routes/page :rounds-summary]))


(defmethod page-root/render :rounds-summary
  []
  [page
   [menu
    [menu-item
     {:on-click #(re-frame/dispatch [:steamdating.rounds/start-next])}
     "Next round"]]
   [content
    [:button "Create first round"]]])
