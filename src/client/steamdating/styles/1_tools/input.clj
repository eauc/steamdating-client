(ns steamdating.styles.1-tools.input
  (:require [garden.compiler :as gcomp]
            [garden.selectors :as gsel]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))

(defn input
  [selectors]
  (vec (concat
         selectors
         [{:background-color "white"
           :border (:border box-model)
           :padding "0.5em"
           :line-height "1.35em"}
          [(gsel/& (gsel/attr :required)) {:border-width "1px 1px 1px 5px"}]
          [:&:focus {:border-color (:focus colors)
                     :box-shadow (str "0 0 4px " (gcomp/render-css (:focus-shadow colors)))
                     :outline "none"}]])))
