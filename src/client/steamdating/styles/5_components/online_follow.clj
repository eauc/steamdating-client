(ns steamdating.styles.5-components.online-follow
  (:require [garden.def :as gdef]
            [steamdating.styles.0-settings.break :refer [at-break]]
            [steamdating.styles.0-settings.box-model :refer [box-model]]
            [steamdating.styles.0-settings.colors :refer [colors]]
            [steamdating.styles.1-tools.button :refer [button]]))

(def control
  {:margin "0 0.5em"
   :flex-grow 1})

(gdef/defstyles online-follow
  [:&-OnlineFollow {:opacity 0
                    :width "100%"
                    :height "100%"}
   [:&-mask {:display "flex"
             :flex-direction "column"
             :justify-content "space-around"
             :align-items "center"
             :position "fixed"
             :width "100%"
             :height "100%"
             :background-color "rgba(0,0,0,0.5)"}]
   [:&-content {:display "flex"
                :flex-direction "column"
                :max-width "90%"
                :padding (:padding box-model)
                :background-color "white"
                :border (:border box-model)
                :text-align "center"}]
   [:&-link {:word-wrap :break-word}]
   [:&-qrCode {:flex-grow 1}]
   (button [:&-close])
   [:&-close {:border :none}]
   (button [:&-toggle])
   [:&-toggle {:display :flex
               :flex-direction :row
               :align-items :center
               :width "100%"}
    (at-break
      :tablet
      [:& {:width :auto}])]
   [:&-qr-code-icon {:width "2em"}]
   [:&-toggle-text {:margin-left "0.5em"}]
   [:&-show {:opacity 1
             :pointer-events "all"}]]
  (button [:&-OnlineSubscribe]))
