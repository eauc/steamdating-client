(ns steamdating.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [steamdating.components.nav.menu :as nav]
            [steamdating.components.page.root :as page]
            [steamdating.components.prompt.prompt :refer [prompt]]
            [steamdating.services.debug :as debug]
            [steamdating.services.db :as db]
            [steamdating.services.routes :as routes]))


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
  (re-frame/dispatch-sync [:initialize-db])
  (routes/init)
  (mount-root))
