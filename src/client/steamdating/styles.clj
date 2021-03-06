(ns steamdating.styles
  (:require [garden.def :as gdef]
            [garden.stylesheet :as gstyle]
            [steamdating.styles.2-generics.fonts :refer [fonts]]
            [steamdating.styles.3-elements.box-model :refer [box-model]]
            [steamdating.styles.3-elements.input :refer [input]]
            [steamdating.styles.3-elements.text :refer [text]]
            [steamdating.styles.4-objects.button :refer [button]]
            [steamdating.styles.4-objects.section-header :refer [section-header]]
            [steamdating.styles.4-objects.table :refer [table]]
            [steamdating.styles.5-components.icon :refer [icon]]
            [steamdating.styles.5-components.file-open-button :refer [file-open-button]]
            [steamdating.styles.5-components.filter-input :refer [filter-input]]
            [steamdating.styles.5-components.form :refer [form]]
            [steamdating.styles.5-components.form-input :refer [form-input]]
            [steamdating.styles.5-components.game-edit :refer [game-edit]]
            [steamdating.styles.5-components.layout :refer [layout]]
            [steamdating.styles.5-components.nav :refer [nav]]
            [steamdating.styles.5-components.online-follow :refer [online-follow]]
            [steamdating.styles.5-components.online-tournaments :refer [online-tournaments]]
            [steamdating.styles.5-components.overlay :refer [overlay]]
            [steamdating.styles.5-components.page :refer [page]]
            [steamdating.styles.5-components.player-file-imports :refer [player-file-imports]]
            [steamdating.styles.5-components.player-list :refer [player-list]]
            [steamdating.styles.5-components.prompt :refer [prompt]]
            [steamdating.styles.5-components.ranking-bests :refer [ranking-bests]]
            [steamdating.styles.5-components.ranking-list :refer [ranking-list]]
            [steamdating.styles.5-components.round-next :refer [round-next]]
            [steamdating.styles.5-components.round-nth :refer [round-nth]]
            [steamdating.styles.5-components.round-summary :refer [round-summary]]
            [steamdating.styles.5-components.sort-header :refer [sort-header]]
            [steamdating.styles.5-components.spinner :refer [spinner]]
            [steamdating.styles.5-components.toaster :refer [toaster]]
            [steamdating.styles.5-components.tournament-file :refer [tournament-file]]
            [steamdating.styles.6-pages.data :refer [page-data]]
            [steamdating.styles.6-pages.follow :refer [page-follow]]
            [steamdating.styles.6-pages.loading :refer [page-loading]]
            [steamdating.styles.6-pages.online :refer [page-online]]
            [steamdating.styles.6-pages.unknown :refer [page-unknown]]))


(gdef/defstyles screen
  fonts
  box-model
  input
  text
  [:.sd
   button
   file-open-button
   filter-input
   form
   form-input
   game-edit
   icon
   layout
   nav
   online-follow
   online-tournaments
   overlay
   page
   page-data
   page-follow
   page-loading
   page-online
   page-unknown
   player-file-imports
   player-list
   prompt
   ranking-bests
   ranking-list
   round-next
   round-nth
   round-summary
   section-header
   sort-header
   spinner
   table
   toaster
   tournament-file])
