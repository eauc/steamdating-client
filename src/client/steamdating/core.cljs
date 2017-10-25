(ns steamdating.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [steamdating.pages.home]
            [steamdating.components.nav.actions :refer [nav-actions]]
            [steamdating.components.nav.menu :refer [nav-menu]]
            [steamdating.components.page.content :refer [page]]
            [steamdating.services.debug :as debug]
            [steamdating.services.db :as db]
            [steamdating.services.routes :refer [routes-init]]
            [steamdating.services.ui :refer [ui-init]]))


(defn mount-root
  []
  (re-frame/clear-subscription-cache!)
  (reagent/render [nav-actions]
                  (.querySelector js/document ".sd .nav .actions"))
  (reagent/render [nav-menu]
                  (.querySelector js/document ".sd .nav .menu"))
  (reagent/render [page]
                  (.querySelector js/document ".sd .page")))


(defn ^:export init
  []
  (debug/setup)
  (re-frame/dispatch-sync [:steamdating.db/initialize])
  (routes-init)
  (ui-init)
  (mount-root))
