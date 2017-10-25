(ns steamdating.components.generics.icon
  (:require feather-icons))


(defn icon
  [{:keys [name]}]
  [:svg.sd-icon
   {:dangerouslySetInnerHTML {:__html (aget feather-icons/icons name)}
    :viewBox "0 0 24 24"}])
