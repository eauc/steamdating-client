(ns steamdating.components.faction.icon
  (:require [re-frame.core :as re-frame]
            [steamdating.models.faction :as faction]
            [steamdating.services.factions]))


(defn faction-icon
  []
  (let [factions (re-frame/subscribe [:steamdating.factions/factions])]
    (fn faction-icon-render
      [{:keys [faction]}]
      (let [icon (faction/icon @factions faction)]
        (when icon
          [:img.sd-FactionIcon {:src icon}])))))
