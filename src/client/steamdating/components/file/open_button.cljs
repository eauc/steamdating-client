(ns steamdating.components.file.open-button
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.button :refer [button]]))


(defn file-open-button
  [{:keys [id on-open]} children]
  [:div.sd-file-open-button
   [:input.input
    {:id id
     :type "file"
     :on-change #(let [value (-> % .-target .-files (aget 0))]
                  (re-frame/dispatch (conj on-open value)))}]
   [button {:element :label
            :for id
            :icon "file-text"
            :label "Open"}]])
