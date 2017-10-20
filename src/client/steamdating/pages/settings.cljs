(ns steamdating.pages.settings
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.page.menu :refer [menu menu-item]]
            [steamdating.components.page.page :refer [content page]]
            [steamdating.components.page.root :as page-root]
            [steamdating.components.tournament.settings-edit :refer [settings-edit]]
            [steamdating.services.debug :as debug]
            [steamdating.services.tournament]))


(defroute settings "/settings" {}
  (debug/log "routes settings")
  (re-frame/dispatch [:steamdating.tournament/settings-start-edit])
  (re-frame/dispatch [:steamdating.routes/page :settings]))


(defmethod page-root/render :settings
  []
  [page
   [content
    [settings-edit]]])
