(ns steamdating.components.generics.file-open
  (:require [re-frame.core :as re-frame]))


(defn open-button
  [{:keys [id on-open]} children]
  [:div.sd-FileOpenButton
   [:input.sd-FileOpenButton-input
    {:id id
     :type "file"
     :on-change (fn [event]
                  (re-frame/dispatch
                    (conj on-open (-> event .-target .-files (aget 0)))))}]
   [:label.sd-FileOpenButton-button
    {:for id}
    children]])
