(ns steamdating.components.page.root
  (:require [re-frame.core :as re-frame]
            [steamdating.services.routes]))


(defmulti render :page)


(defmethod render :default
  [state]
  [:div.sd-Page
   ;; (pr-str state)
   [:div.sd-PageContent
    [:div.sd-PageContent-insider
     [:p "Unknown page"]]]])


(defn root
  []
  (let [state @(re-frame/subscribe [:steamdating.routes/route])]
    [render state]))
