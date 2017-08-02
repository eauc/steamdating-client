(ns steamdating.styles.3-elements.text
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.colors :refer [colors]]))

(gdef/defstyles text
  [:* :html :body {:font-family "Droid Sans"}]
  [:html :body {:color (:text colors)}])
