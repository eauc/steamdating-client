(ns steamdating.components.generics.file-save
  (:require [steamdating.components.generics.icon :refer [icon]]))


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


(defn file-save
  [data]
  (let [url (atom nil)]
    (fn [data]
      (when (some? @url)
        (revoke-link @url))
      (let [new-url (generate-link data)]
        (reset! url new-url)
        [:a.sd-FileSaveButton
         {:href new-url
          :download "steamdating.json"}
         [icon "save"]
         [:span.sd-FileSaveButton-text
          " Save"]]))))
