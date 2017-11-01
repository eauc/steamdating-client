(ns steamdating.components.page.menu-item
  (:require [clojure.string :as s]
            [steamdating.components.generics.icon :as icon]
            [steamdating.models.ui :as ui]))


(defn page-menu-item
  [{:keys [active? disabled? icon label] :as props}]
  [:button.sd-page-menu-item
   (-> props
       (dissoc :active? :disabled? :icon :label)
       (assoc :class (ui/classes (when active? "active")
                                 (when disabled? "disabled"))))
   [:span.sd-page-menu-item-label label]
   (when (some? icon)
     [icon/icon {:name icon}])])
