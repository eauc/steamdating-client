(ns steamdating.styles.4-objects.nav
  (:require [garden.color :as gcolor]
            [garden.stylesheet :as gstyle]
            [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]
            [steamdating.styles.1-tools.button :refer [button]]))

(def padding "0.2em")

(gdef/defstyles nav
  [:&-Nav {:z-index 500
           :position "relative"
           :background-color (:primary colors)
           :box-shadow (:box_shadow box-model)}
   [:&Brand {:font-family "Cookie"
             :font-size "1.8em"
             :color (:text-inverted colors)
             :padding padding}]
   [:&Menu-actions {:position "absolute"
                    :top 0
                    :right 0
                    :height "2.8em"
                    :display "flex"
                    :align-items "center"
                    :border 0}
    (button
      [:.sd-FileSaveButton]
      {:height "1.4em"
       :width "1.4em"
       :padding "0.1em 0"
       :font-size "2em"
       :border 0
       :background-color (:primary colors)
       :color (:text-inverted colors)})
    [:.sd-FileSaveButton-text {:display :none}]
    ;; [:.sd-FileSaveButton
    ;;  ;; :.sd-AuthToggleButton
    ;;  {:height "1.4em"
    ;;   :width "1.4em"
    ;;   :padding 0
    ;;   :font-size "2em"
    ;;   :line-height "1.4em"
    ;;   :border 0
    ;;   :text-align "center"}]
    ;; ;; [:.sd-AuthToggleButton {:color (gcolor/darken (gcolor/hex->rgb "#FFF") 40)}]
    ;; ;; [:.sd-AuthToggleButton.sd-AuthToggleButton-active {:color "white"}]
    ]
   (button
     [:&Toggle]
     {:height "1.4em"
      :width "1.4em"
      :padding 0
      :font-size "2em"
      :font-weight "bold"
      :border 0
      :background-color (:primary colors)
      :color (:text-inverted colors)})
   [:&Item {:display "none"
            :color (:text-inverted colors)
            :text-decoration "none"
            :padding (:padding box-model)
            :line-height "1.8em"}

    [:&:hover
     :&:active
     :&-active {:background-color (:primary-dark colors)}]]
   [:&Menu-show
    [:.sd-NavItem {:display "block"}]]
   (at-break
     :tablet
     [:& {:display "flex"
          :flex-direction "row"}
      [:&Menu {:display "flex"
               :flex-direction "row"}]
      [:&Item {:display "block"}]
      [:&Toggle {:display "none"}]])])
