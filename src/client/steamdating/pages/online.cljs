(ns steamdating.pages.online
  (:require [re-frame.core :as re-frame]
            [secretary.core :refer [defroute]]
            [steamdating.components.page.content :refer [page-content]]
            [steamdating.components.page.menu :refer [page-menu-items]]
            [steamdating.components.page.menu-item :refer [page-menu-item]]
            [steamdating.components.online.login-button :refer [online-login-button]]
            [steamdating.components.online.tournaments :refer [online-tournaments]]
            [steamdating.services.debug :as debug]
            [steamdating.services.online]))


(defroute online "/online" {}
  (re-frame/dispatch [:sd.routes/page :online]))


(defmethod page-content :online
  []
  [:div.sd-page-online
   (let [status @(re-frame/subscribe [:sd.online.user/status])]
     (case status
       :logged [online-tournaments]
       [:div.sd-page-online-login
        [online-login-button]]))])
