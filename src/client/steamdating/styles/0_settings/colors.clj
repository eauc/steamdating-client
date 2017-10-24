(ns steamdating.styles.0-settings.colors
  (:require [garden.color :as gcolor]))

(def base-colors
  {:primary (gcolor/hex->rgb "#2196F3")
   :primary-dark (gcolor/hex->rgb "#1976D2")
   :primary-light (gcolor/hex->rgb "#03A9F4")
   :accent (gcolor/hex->rgb "#4CAF50")
   :border (gcolor/rgba 0 0 0 0.4)
   :shadow (gcolor/rgba 0 0 0 0.6)
   :hover (gcolor/darken (gcolor/hex->rgb "#FFF") 15)
   :disabled (gcolor/darken (gcolor/hex->rgb "#FFF") 50)
   :error (gcolor/hex->rgb "#F44336")
   :warning (gcolor/hex->rgb "#FFC107")
   :valid (gcolor/hex->rgb "#4CAF50")
   :text (gcolor/rgba 0 0 0 0.75)
   :text-light (gcolor/rgba 0 0 0 0.6)
   :text-muted (gcolor/rgba 0 0 0 0.4)
   :text-inverted (gcolor/hex->rgb "#FFF")})

(def colors
  (merge base-colors
         {:primary-bckgnd (gcolor/lighten (:primary-light base-colors) 15)
          :focus (:primary-light base-colors)
          :focus-shadow (assoc (:primary-light base-colors) :alpha 0.75)
          :error-shadow (assoc (:error base-colors) :alpha 0.75)
          :error-bckgnd (gcolor/lighten (:error base-colors) 15)
          :warning-bckgnd (gcolor/lighten (:warning base-colors) 15)
          :info-bckgnd (gcolor/lighten (:primary base-colors) 15)
          :valid-shadow (assoc (:valid base-colors) :alpha 0.75)
          :valid-bckgnd (gcolor/lighten (:valid base-colors) 15)}))
