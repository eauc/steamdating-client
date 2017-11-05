(ns steamdating.components.generics.button
  (:require [steamdating.components.generics.icon :as icon]
            [steamdating.models.ui :as ui]))


(defn button
  [{:keys [class element icon label title type] :as props
    :or {element :button
         type :button}}]
  [element (-> props
               (dissoc :element :icon :label)
               (assoc :class (ui/classes "sd-button" class)
                      :title (or title label))
               (cond-> (= element :button)
                 (assoc :type type)))
   (when (some? icon)
     [icon/icon {:name icon}])
   (when (some? label)
     [:span.sd-button-label label])])
