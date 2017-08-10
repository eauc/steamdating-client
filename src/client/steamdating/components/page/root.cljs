(ns steamdating.components.page.root
  (:require [re-frame.core :as re-frame]
            [steamdating.components.page.sub]))


(defmulti render :page)


(defmethod render :default
  []
  [:div.sd-Page
   [:div.sd-PageContent
    [:div.sd-PageContent-insider
     [:p "Unknown page"]]]])


(defn root
  []
  (let [page (re-frame/subscribe [:steamdating.routes/page])]
    (fn root-component
      []
      [render {:page @page}])))
