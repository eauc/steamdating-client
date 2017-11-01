(ns steamdating.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [steamdating.pages.data]
            [steamdating.pages.home]
            [steamdating.pages.players-create]
            [steamdating.pages.players-edit]
            [steamdating.pages.players-list]
            [steamdating.pages.rounds-all]
            [steamdating.pages.rounds-next]
            [steamdating.pages.rounds-nth]
            [steamdating.components.nav.actions :refer [nav-actions]]
            [steamdating.components.nav.menu :refer [nav-menu]]
            [steamdating.components.page.content :refer [page]]
            [steamdating.components.prompt.prompt :refer [prompt]]
            [steamdating.components.toaster.toaster :refer [toaster]]
            [steamdating.services.debug :as debug]
            [steamdating.services.db :as db]
            [steamdating.services.factions]
            [steamdating.services.routes :refer [routes-init]]
            [steamdating.services.ui :refer [ui-init]]))


(defn mount-root
  []
  (re-frame/clear-subscription-cache!)
  (reagent/render [nav-actions]
                  (.querySelector js/document ".sd-nav-actions"))
  (reagent/render [nav-menu]
                  (.querySelector js/document ".sd-nav-menu"))
  (reagent/render [page]
                  (.querySelector js/document ".sd-page"))
  (reagent/render [prompt]
                  (.querySelector js/document ".sd-overlay"))
  (reagent/render [toaster]
                  (.querySelector js/document ".sd-toaster")))


(defn ^:export init
  []
  (debug/setup)
  (re-frame/dispatch-sync [:steamdating.db/initialize])
  (routes-init)
  (ui-init)
  (mount-root))
