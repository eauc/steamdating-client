(ns steamdating.styles.5-components.input
  (:require [garden.compiler :as gcomp]
            [garden.def :as gdef]
            [garden.selectors :as gsel]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))

(gdef/defstyles input
  [:&-input {:display "flex"
             :flex-flow "column nowrap"
             :margin "0.5em 0"
             :padding "0 1em"}
   [:.value {:background-color "white"
             :border (:border box-model)
             :padding "0.5em"
             :line-height "1.35em"}
    [(gsel/& (gsel/attr :required)) {:border-width "1px 1px 1px 5px"}]
    [:&:focus {:border-color (:focus colors)
               :box-shadow (str "0 0 4px " (gcomp/render-css (:focus-shadow colors)))
               :outline "none"}]
    [:&.error {:border-color "red"}
     [:&:focus { :outline-color "red"}]]]])
