(ns steamdating.components.generics.icon
  (:require feather-icons))


(defn icon
  [name]
  (let [attrs (clj->js {:stroke-width "2" :width "1.2em" :height "1.2em"})]
    [:svg.sd-Icon
     {:width "1.2em"
      :height "1.2em"
      :fill "none"
      :stroke "currentcolor"
      :stroke-width 2
      :stroke-linecap "round"
      :stroke-linejoin "round"
      :viewBox "0 0 24 24"
      :style {:vertical-align "sub"}
      :dangerouslySetInnerHTML {:__html (aget feather-icons/icons name)}}]))
