(ns steamdating.components.generics.icon)


(defn icon
  [name]
  [:span {:class (str "fa fa-" name)}])
