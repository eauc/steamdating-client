(ns steamdating.components.nav.toggle
  (:require [steamdating.components.generics.icon :refer [icon]]))

(defn toggle
  [{:keys [toggle-show]}]
  [:button.sd-NavToggle
   {:on-click toggle-show}
   [icon "menu"]])
