(ns steamdating.components.generics.faction-icon
  (:require [re-frame.core :as re-frame]
            [steamdating.models.faction :as faction]
            [steamdating.services.factions]))


(defn faction-icon
  [faction]
  (let [icon @(re-frame/subscribe [:steamdating.factions/icon faction])]
    (when icon
      [:img.sd-FactionIcon
       {:alt faction
        :title faction
        :src icon}])))
