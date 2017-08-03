(ns steamdating.components.page.page
  (:require [steamdating.components.toaster.toaster :refer [toaster]]))


(defn page
  [& children]
  (apply conj [:div.sd-Page] children))


(defn content
  [& children]
  [:div.sd-PageContent
   (apply conj [:div.sd-PageContent-insider] children)
   [toaster]])
