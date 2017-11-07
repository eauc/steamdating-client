(ns steamdating.components.file.save-button
  (:require [steamdating.components.generics.button :refer [button]]
            [steamdating.components.generics.icon :refer [icon]]))


(defn generate-link
  [data]
  (as-> (clj->js data) $
    (.stringify js/JSON $)
    (js/Array. $)
    (js/Blob. $ #js {:type "text/plain"})
    (.createObjectURL js/URL $)))


(defn revoke-link
  [url]
  (.revokeObjectURL js/URL url))


(defn file-save-button
  []
  (let [url (atom nil)]
    (fn [{:keys [data]}]
      (when (some? @url)
        (revoke-link @url))
      (let [new-url (generate-link data)
            download (str "steamdating_" (.now js/Date) ".json")]
        (reset! url new-url)
        [button {:download download
                 :element :a
                 :href new-url
                 :icon "save"
                 :label "Save"}]))))
