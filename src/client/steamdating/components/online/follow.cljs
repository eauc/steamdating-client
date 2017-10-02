(ns steamdating.components.online.follow
  (:require [re-frame.core :as re-frame]))


(defn follow
  []
  (let [{:keys [_id]} @(re-frame/subscribe [:steamdating.tournament/online])
        status @(re-frame/subscribe [:steamdating.online/status])]
    (when (= :synced status)
      [:a.sd-OnlineFollow
       {:href (str "#/follow/" _id)}
       "Follow tournament online"])))
