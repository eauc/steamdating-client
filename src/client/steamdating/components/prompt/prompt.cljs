(ns steamdating.components.prompt.prompt
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.generics.input :refer [input]]
            [steamdating.services.prompt]))


(defn render
  [{:keys [message type value] :as state}
   {:keys [on-cancel on-update on-validate]}]
  [:div.sd-Prompt {:class (when state "sd-Prompt-show")}
   [:div.sd-Prompt-mask {:on-click on-cancel}
    [:form.sd-Prompt-content
     {:on-click #(-> % .stopPropagation)
      :on-submit (fn [event]
                   (doto event
                     (.preventDefault)
                     (.stopPropagation))
                   (on-validate))}
     [:div.sd-Prompt-msg message]
     (when (= type :prompt)
       [input {:type (if (number? value) :number :text)
               :field [:value]
               :form-state {:edit {:value value}}
               :on-update on-update
               :autofocus "autofocus"}])
     [:div.sd-Prompt-controls
      [:button.sd-Prompt-control-ok {:type "submit"}
       [:span "Ok "]
       [icon "check"]]
      (when (not= type :alert)
        [:button.sd-Prompt-control-cancel
         {:type "button"
          :on-click on-cancel}
         [:span "No "]
         [icon "x"]])]]]])


(defn prompt
  []
  (let [state @(re-frame/subscribe [:steamdating.prompt/prompt])
        on-cancel #(re-frame/dispatch [:steamdating.prompt/cancel])
        on-update #(re-frame/dispatch [:steamdating.prompt/update %2])
        on-validate #(re-frame/dispatch [:steamdating.prompt/validate])]
    [render state
     {:on-cancel on-cancel
      :on-update on-update
      :on-validate on-validate}]))
