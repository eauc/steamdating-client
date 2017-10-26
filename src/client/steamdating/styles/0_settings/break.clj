(ns steamdating.styles.0-settings.break
  (:require [garden.stylesheet :as gstyle]))


(def break
  {:tablet "600px"
   :pc "900px"})


(defn at-break
  [brk styles]
  (gstyle/at-media {:min-width (get break brk)} styles))
