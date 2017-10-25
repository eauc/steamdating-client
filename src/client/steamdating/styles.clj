(ns steamdating.styles
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]
            [steamdating.styles.2-generics.fonts :refer [fonts]]
            [steamdating.styles.3-elements.text :refer [text]]
            [steamdating.styles.4-objects.icon :refer [icon]]
            [steamdating.styles.4-objects.layout :refer [layout]]
            [steamdating.styles.4-objects.nav :refer [nav]]
            [steamdating.styles.4-objects.page :refer [page]]
            [steamdating.styles.5-components.toaster :refer [toaster]]
            [steamdating.styles.6-pages.loading :refer [page-loading]]
            [steamdating.styles.6-pages.unknown :refer [page-unknown]]))


(gdef/defstyles screen
  fonts
  text
  [:.sd
   icon
   layout
   nav
   page
   page-loading
   page-unknown
   toaster])
