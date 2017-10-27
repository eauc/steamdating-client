(ns steamdating.components.generics.button
  (:require [steamdating.components.generics.icon :as icon]))


(defn button
  [{:keys [class element icon label type] :as props
    :or {element :button
         type :button}}]
  [element (-> props
               (dissoc :element :icon :label)
               (assoc :class (str "sd-button " class))
               (cond-> (= element :button)
                 (assoc :type type)))
   (when (some? icon)
     [icon/icon {:name icon}])
   (when (some? label)
     [:span.sd-button-label label])])
