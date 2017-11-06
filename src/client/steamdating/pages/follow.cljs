(ns steamdating.pages.follow
	(:require [re-frame.core :as re-frame]
						[secretary.core :as secretary :refer-macros [defroute]]
						[steamdating.components.form.input :refer [form-input]]
            [steamdating.components.generics.button :refer [button]]
            [steamdating.components.nav.actions :refer [nav-actions-content]]
            [steamdating.components.nav.menu :refer [nav-menu-content]]
						[steamdating.components.page.content :refer [page-content]]
            [steamdating.components.page.menu :refer [page-menu-items]]
            [steamdating.components.player.list :refer [player-list-follow]]
            [steamdating.components.ranking.list :refer [ranking-list-follow]]
            [steamdating.components.round.nth :refer [round-nth-follow]]
            [steamdating.components.round.summary :refer [round-summary-follow]]
						[steamdating.services.debug :as debug]))


(defroute follow "/follow/:id" {id :id}
	(re-frame/dispatch [:sd.routes/page :follow {:id id}])
	(re-frame/dispatch [:sd.online.follow/refresh]))


(defmethod page-content :follow
	[]
	(let [online @(re-frame/subscribe [:sd.tournament/online])
        filter @(re-frame/subscribe [:sd.filters/filter :follow])
        on-filter-update #(re-frame/dispatch [:sd.filters/set :follow %])
        n-rounds @(re-frame/subscribe [:sd.rounds/count])]
		[:div.sd-page-follow
		 [:h3 "Follow " (:name online)]
     [form-input {:on-update on-filter-update
                  :placeholder "Filter"
                  :value filter}]
     [ranking-list-follow]
     (for [n (reverse (range n-rounds))]
       [round-nth-follow {:n n}])
     [round-summary-follow]
     [player-list-follow]]))


(defmethod page-menu-items :follow
	[]
	(list [:button.sd-page-menu-item
         {:key :follow
          :on-click #(re-frame/dispatch [:sd.online.follow/show])}
         [:span.sd-page-menu-item-label "Follow online"]
         [:img.sd-icon {:src "/icons/qr-code.png"}]]))


(defmethod nav-menu-content :follow
  [{ {:keys [hash]} :route}]
  [:div.sd-nav-menu-content])


(defmethod nav-actions-content :follow
  [{:keys [menu]}]
  [:div.sd-nav-actions-content
   [button {:icon "refresh-cw"
            :label "Refresh"
            :on-click #(re-frame/dispatch [:sd.online.follow/refresh])}]])
