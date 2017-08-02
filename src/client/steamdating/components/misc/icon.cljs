(ns steamdating.components.misc.icon)

(defn icon
  [{:keys [name]}]
  [:span {:class (str "fa fa-" name)}])
