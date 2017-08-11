(ns steamdating.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [steamdating.components.nav.menu :as nav]
            [steamdating.components.page.root :as page]
            [steamdating.components.prompt.prompt :refer [prompt]]
            [steamdating.services.debug :as debug]
            [steamdating.services.db :as db]
            [steamdating.services.factions :as factions]
            [steamdating.services.forms :as forms]
            [steamdating.services.routes :as routes]
            [steamdating.services.players :as players]
            [steamdating.services.tournament :as tournament]))


(defn mount-root
  []
  (re-frame/clear-subscription-cache!)
  (reagent/render [nav/menu]
                  (.querySelector js/document ".sd-NavMenu-container"))
  (reagent/render [page/root]
                  (.querySelector js/document ".sd-Page-container"))
  (reagent/render [prompt]
                  (.querySelector js/document ".sd-Prompt-container")))


(defn ^:export init
  []
  (debug/setup)
  (re-frame/dispatch-sync [:steamdating.db/initialize])
  (re-frame/dispatch [:steamdating.factions/initialize])
  (routes/init)
  (mount-root))
