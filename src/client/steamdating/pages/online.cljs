(ns steamdating.pages.online
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.page.page :refer [content page]]
            [steamdating.components.page.root :as page-root]
            [steamdating.components.online.online-follow :refer [online-follow-toggle]]
            [steamdating.components.online.online-button :refer [online-button]]
            [steamdating.components.online.online-edit :refer [online-edit]]
            [steamdating.components.online.online-tournaments :refer [online-tournaments]]
            [steamdating.services.debug :as debug]))


(defroute online "/online" {}
  (debug/log "route online")
  (re-frame/dispatch [:steamdating.routes/page :online]))


(defmethod page-root/render :online
  []
  [page {:class "sd-OnlinePage"}
   [content
    [:p [online-follow-toggle]]
    [online-edit]
    [online-tournaments]]])
