(ns steamdating.components.nav.toggle
  (:require [steamdating.components.misc.icon :refer [icon]]))

(defn toggle
  [{:keys [toggle-show]}]
  [:button.sd-NavToggle {:on-click toggle-show}
   [icon {:name "bars"}]])
