(ns steamdating.pages.online
	(:require [re-frame.core :as re-frame]
						[secretary.core :refer [defroute]]
						[steamdating.components.page.content :refer [page-content]]
						[steamdating.components.page.menu :refer [page-menu-items]]
						[steamdating.components.page.menu-item :refer [page-menu-item]]
						[steamdating.components.online.login-button :refer [online-login-button]]
						[steamdating.components.online.tournament-edit :refer [online-tournament-edit]]
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
			 :logged [:div
								[online-tournament-edit]
								[online-tournaments]]
			 [:div.sd-page-online-login
				[online-login-button]]))])


(defmethod page-menu-items :online
	[]
	(let [tournament-status @(re-frame/subscribe [:sd.online.tournament/status])]
		(if (= :online tournament-status)
      (list
        [:button.sd-page-menu-item
         {:key :follow
          :on-click #(re-frame/dispatch [:sd.online.follow/show])}
         [:span.sd-page-menu-item-label "Follow online"]
         [:img.sd-icon {:src "/icons/qr-code.png"}]])
      [])))
