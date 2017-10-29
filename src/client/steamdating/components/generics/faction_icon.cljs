(ns steamdating.components.generics.faction-icon)


(defn faction-icon
  [{:keys [icons name] :as props}]
  [:div.sd-faction-icon
   (let [path (get icons (keyword name))]
     (when (some? path)
       [:img
        (-> props
            (dissoc :icons :name)
            (assoc :src (str "/data/icons/" path)))]))
   [:span.sd-faction-icon-label
    name]])
