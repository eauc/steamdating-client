(ns steamdating.styles
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]
            [steamdating.styles.0-settings.colors :refer [colors]]
            [steamdating.styles.2-generics.fonts :refer [fonts]]
            [steamdating.styles.3-elements.text :refer [text]]
            [steamdating.styles.4-objects.layout :refer [layout]]
            [steamdating.styles.4-objects.menu :refer [menu]]
            [steamdating.styles.4-objects.nav :refer [nav]]
            [steamdating.styles.4-objects.text-muted :refer [text-muted]]
            [steamdating.styles.5-components.prompt :refer [prompt]]))

(gdef/defstyles screen
  fonts
  text
  [:.sd
   layout
   menu
   nav
   prompt
   text-muted])
