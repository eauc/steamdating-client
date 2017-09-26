(ns steamdating.components.online.online-edit
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.form :refer [form]]
            [steamdating.components.generics.input :refer [input]]))


(defn online-edit
  []
  (let [status @(re-frame/subscribe [:steamdating.online/status])
        state @(re-frame/subscribe [:steamdating.online/edit-current])
        update-field #(re-frame/dispatch [:steamdating.forms/update :online %1 %2])]
    [:div.sd-OnlineEdit
     (when (not= :offline status)
       [form state
        {:label "Upload current tournament"
         :save-label "Upload"
         :on-submit #(re-frame/dispatch [:steamdating.online/upload-current])}
        [:div
         [input {:type :text
                 :label "Name"
                 :field [:name]
                 :form-state state
                 :on-update update-field
                 :required :required
                 :order 1}]
         [input {:type :date
                 :label "Date"
                 :field [:date]
                 :form-state state
                 :on-update update-field
                 :required :required
                 :order 2}]]])]))
