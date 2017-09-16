(ns steamdating.styles.6-pages.file
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.box-model :refer [box-model]]))


(gdef/defstyles file-page
  [:&-FilePage
   [:.sd-PageContent-insider {:display "flex"
                              :flex-direction "column"
                              :align-items "stretch"
                              :padding "1em"}]
   [:&-header {:padding "0 0 1em 0"
               :border-bottom (:border box-model)
               :margin "0 0 1em 0"
               :text-align "center"}]
   [:&-fileActions {:flex-shrink 0
                    :display "flex"
                    :flex-direction "column"
                    :align-items "stretch"
                    :margin-bottom "1em"}
    [:.sd-TournamentNewButton
     :.sd-FileSaveButton
     :.sd-FileOpenButton {:flex-shrink 0
                          :margin "0 0 1em"}]]

   (at-break
     :tablet
     [:&-header {:text-align "left"}]
     [:&-fileActions {:flex-direction "row"
                      :align-items "flex-start"}
      [:.sd-TournamentNewButton
       :.sd-FileSaveButton
       :.sd-FileOpenButton {:max-width "8em"
                            :flex-grow 1
                            :margin "0 1em 1em 1em"}]])])
