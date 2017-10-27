(ns steamdating.styles
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]
            [steamdating.styles.2-generics.fonts :refer [fonts]]
            [steamdating.styles.3-elements.text :refer [text]]
            [steamdating.styles.4-objects.button :refer [button]]
            [steamdating.styles.4-objects.section-header :refer [section-header]]
            [steamdating.styles.5-components.icon :refer [icon]]
            [steamdating.styles.5-components.file-open-button :refer [file-open-button]]
            [steamdating.styles.5-components.form :refer [form]]
            [steamdating.styles.5-components.form-input :refer [form-input]]
            [steamdating.styles.5-components.layout :refer [layout]]
            [steamdating.styles.5-components.nav :refer [nav]]
            [steamdating.styles.5-components.page :refer [page]]
            [steamdating.styles.5-components.prompt :refer [prompt]]
            [steamdating.styles.5-components.toaster :refer [toaster]]
            [steamdating.styles.5-components.tournament-file :refer [tournament-file]]
            [steamdating.styles.6-pages.data :refer [page-data]]
            [steamdating.styles.6-pages.loading :refer [page-loading]]
            [steamdating.styles.6-pages.unknown :refer [page-unknown]]))


(gdef/defstyles screen
  fonts
  text
  [:.sd
   button
   file-open-button
   form
   form-input
   icon
   layout
   nav
   page
   page-data
   page-loading
   page-unknown
   prompt
   section-header
   toaster
   tournament-file])
