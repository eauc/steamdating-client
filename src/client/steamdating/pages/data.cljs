(ns steamdating.pages.data
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary :refer-macros [defroute]]
            [steamdating.components.page.content :refer [page-content]]
            [steamdating.components.tournament.file-actions :refer [tournament-file-actions]]
            [steamdating.services.debug :as debug]))


(defroute data "/data" {}
  (re-frame/dispatch [:sd.routes/page :data]))


(defmethod page-content :data
  []
  [:div.sd-page-data
   [tournament-file-actions]])
