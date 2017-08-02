(ns steamdating.styles.1-tools.button
  (:require [garden.color :as gcolor]
            [garden.compiler :as gcomp]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]))

(def default-background-color
  (gcolor/hex->rgb "#FFF"))

(defn button
  [selectors {:keys [background-color] :as css}]
  (let [color (or background-color default-background-color)
        border (str "1px solid " (gcomp/render-css (gcolor/darken color 20)))
        focus-color (gcolor/darken color 10)]
    (vec (concat
           selectors
           [(merge {:display "block"
                    :box-sizing "border-box"
                    :cursor "pointer"
                    :background-color color
                    :color (:text colors)
                    :padding (:padding box-model)
                    :border border
                    :border-radius (:border-radius box-model)
                    :text-align "center"
                    :text-decoration "none"}
                   css)
            [:&:hover
             :&:focus {:background-color focus-color
                       :outline "none"}]]))))
