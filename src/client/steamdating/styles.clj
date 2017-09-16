(ns steamdating.styles
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]
            [steamdating.styles.0-settings.colors :refer [colors]]
            [steamdating.styles.2-generics.fonts :refer [fonts]]
            [steamdating.styles.3-elements.text :refer [text]]
            [steamdating.styles.4-objects.file-open :refer [file-open]]
            [steamdating.styles.4-objects.file-save :refer [file-save]]
            [steamdating.styles.4-objects.form :refer [form]]
            [steamdating.styles.4-objects.input :refer [input]]
            [steamdating.styles.4-objects.layout :refer [layout]]
            [steamdating.styles.4-objects.menu :refer [menu]]
            [steamdating.styles.4-objects.nav :refer [nav]]
            [steamdating.styles.4-objects.text-muted :refer [text-muted]]
            [steamdating.styles.5-components.faction-icon :refer [faction-icon]]
            [steamdating.styles.5-components.game-edit :refer [game-edit]]
            [steamdating.styles.5-components.players-list :refer [players-list]]
            [steamdating.styles.5-components.prompt :refer [prompt]]
            [steamdating.styles.5-components.ranking-bests :refer [ranking-bests]]
            [steamdating.styles.5-components.ranking-list :refer [ranking-list]]
            [steamdating.styles.5-components.round :refer [round]]
            [steamdating.styles.5-components.round-edit :refer [round-edit]]
            [steamdating.styles.5-components.round-summary :refer [round-summary]]
            [steamdating.styles.5-components.sort-header :refer [sort-header]]
            [steamdating.styles.5-components.toaster :refer [toaster]]
            [steamdating.styles.5-components.tournament :refer [tournament]]
            [steamdating.styles.6-pages.file :refer [file-page]]))


(gdef/defstyles screen
  fonts
  text
  [:.sd
   faction-icon
   file-open
   file-page
   file-save
   form
   game-edit
   input
   layout
   menu
   nav
   players-list
   prompt
   ranking-bests
   ranking-list
   round
   round-edit
   round-summary
   sort-header
   text-muted
   toaster
   tournament])
