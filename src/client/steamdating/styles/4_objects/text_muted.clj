(ns steamdating.styles.4-objects.text-muted
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.colors :refer [colors]]))

(gdef/defstyles text-muted
  [:&-text-muted {:color (:text-muted colors)}])
