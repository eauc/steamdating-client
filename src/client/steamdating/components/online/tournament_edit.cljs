(ns steamdating.components.online.tournament-edit
  (:require [steamdating.components.form.form :refer [form]]
            [steamdating.components.form.input :refer [form-input]]
            [steamdating.components.generics.button :refer [button]]
            [steamdating.models.form :as form-model]
            [steamdating.models.online :as online]
            [steamdating.models.ui :as ui]
            [steamdating.services.debug :as debug]
            [re-frame.core :as re-frame]))


(defn online-tournament-edit-render
  [{:keys [on-submit on-field-update state]}]
  [form {:label "Upload current tournament"
         :on-submit on-submit
         :state state}
   [:div.sd-online-tournament-edit
    [form-input (form-model/field-input-props
                  {:field [:name]
                   :form state
                   :label "Name"
                   :on-update on-field-update})]
    [form-input (form-model/field-input-props
                  {:field [:date]
                   :form state
                   :label "Date"
                   :on-update on-field-update
                   :placeholder "yyyy-mm-dd"
                   :type :date})]
    [button {:class (ui/classes "success"
                                (when-not (form-model/valid? state) "disabled"))
             :icon "upload-cloud"
             :label "Upload"
             :type :submit}]]])


(defn online-tournament-edit
  []
  (let [state @(re-frame/subscribe
                 [:sd.forms/validate :online-tournament
                  (partial form-model/validate :sd.online.tournament/edit)])
        on-field-update #(re-frame/dispatch [:sd.forms/update :online-tournament %1 %2])
        on-submit #(re-frame/dispatch [:sd.online.tournament/upload])]
    [online-tournament-edit-render
     {:on-field-update on-field-update
      :on-submit on-submit
      :state state}]))
