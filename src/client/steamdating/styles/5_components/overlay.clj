(ns steamdating.styles.5-components.overlay
	(:require [garden.def :as gdef]
						[steamdating.styles.0-settings.box-model :refer [box-model]]
						[steamdating.styles.0-settings.colors :refer [colors]]))


(gdef/defstyles overlay
	[:&-overlay {:align-items :center
               :background-color "rgba(0,0,0,0.3)"
               :bottom 0
               :display :flex
               :flex-direction :column
               :justify-content :space-around
               :left 0
               :opacity 0
               :pointer-events :none
               :position :fixed
               :right 0
               :top 0
               :z-index 2000}

	 [:&.show {:opacity 1
						 :pointer-events :initial
						 :transition "opacity 0.25s"}]])
