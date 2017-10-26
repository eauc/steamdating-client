(ns steamdating.styles.6-pages.data
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break break]]
            [steamdating.styles.0-settings.box-model :refer [box-model]]))


(gdef/defstyles page-data
  [:&-page-data {:max-width (:tablet break)}])
