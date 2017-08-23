(ns steamdating.components.nav.toggle)

(defn toggle
  [{:keys [toggle-show]}]
  [:button.sd-NavToggle
   {:on-click toggle-show}
   "☰"])
