(ns steamdating.styles.0-settings.box-model
  (:require [garden.compiler :as gcomp]
            [steamdating.styles.0-settings.colors :refer [colors]]))

(def box-model
  {:border (str "1px solid " (gcomp/render-css (:border colors)))
   :border-radius "3px"
   :box_shadow (str "0px 1px 3px 0px " (gcomp/render-css (:shadow colors)))
   :padding "0.5em"
   :margin "0.5em"})
