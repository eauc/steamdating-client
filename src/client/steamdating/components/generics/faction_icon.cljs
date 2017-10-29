(ns steamdating.components.generics.faction-icon)


(defn faction-icon
  [{:keys [icons name] :as props}]
  [:div.sd-faction-icon
   [:img
    (-> props
        (dissoc :icons :name)
        (assoc :src (str "/data/icons/" (get icons (keyword name)))))]
   [:span.sd-faction-icon-label
    name]])
