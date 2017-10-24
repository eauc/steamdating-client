(ns steamdating.styles.2-generics.fonts
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]))


(gdef/defstyles fonts
  (gstyle/at-font-face
    {:font-family "Droid Sans"
     :font-style :normal
     :font-weight 400
     :src "local(\"Droid Sans\"), local(\"DroidSans\")"})
  (gstyle/at-font-face
    {:font-family "Cookie"
     :font-style :normal
     :font-weight 400
     :src "local(\"Cookie-Regular\"), url(../fonts/Cookie-Regular.ttf) format(\"truetype\")"}))
