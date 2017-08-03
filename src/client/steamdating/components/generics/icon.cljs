(ns steamdating.components.generics.icon)


(defn icon
  [{:keys [name]}]
  [:span {:class (str "fa fa-" name)}])
