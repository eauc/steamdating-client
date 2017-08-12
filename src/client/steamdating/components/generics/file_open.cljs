(ns steamdating.components.generics.file-open
  (:require [re-frame.core :as re-frame]))


(defn open-button
  [{:keys [id on-open]} & children]
  (let [on-change (fn [event]
                    (re-frame/dispatch
                      (conj on-open (aget (-> event .-target .-files) 0))))]
    [:div.sd-FileOpenButton
     [:input.sd-FileOpenButton-input
      {:id id
       :type "file"
       :on-change on-change}]
     (apply conj
            [:label.sd-FileOpenButton-button
             {:for id}]
            children)]))
