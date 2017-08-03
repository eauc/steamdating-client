(ns steamdating.components.prompt.prompt
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.components.prompt.handler]
            [steamdating.components.prompt.spec]
            [steamdating.components.prompt.sub]))


(defn render
  [_ {:keys [on-cancel on-update on-validate]}]

  (let [on-form-click #(-> % .stopPropagation)
        on-form-submit (fn [event]
                         (doto event
                           (.preventDefault)
                           (.stopPropagation))
                         (on-validate))
        on-input-change #(on-update (-> % .-target .-value))]
    (fn prompt-render
      [{:keys [message type value] :as state} _]

      [:div.sd-Prompt {:class (when state "sd-Prompt-show")}
       [:div.sd-Prompt-mask {:on-click on-cancel}
        [:form.sd-Prompt-content {:on-click on-form-click
                                  :on-submit on-form-submit}
         [:div.sd-Prompt-msg message]
         [:input.sd-Prompt-value {:class (when (not= type :prompt)
                                           "sd-Prompt-control-hide")
                                  :type (if (number? value) "number" "text")
                                  :id "prompt.value"
                                  :name "prompt.value"
                                  :value value
                                  :on-change on-input-change}]
         [:div.sd-Prompt-controls
          [:button.sd-Prompt-control-ok {:type "submit"}
           [:span "Ok "]
           [icon {:name "check"}]]
          [:button.sd-Prompt-control-cancel {:class (when (= type :alert)
                                                      "sd-Prompt-control-hide")
                                             :type "button"
                                             :on-click on-cancel}
           [:span "No "]
           [icon {:name "close"}]]]]]])))


(defn prompt
  []

  (let [state (re-frame/subscribe [:prompt])
        on-cancel #(re-frame/dispatch [:prompt-cancel])
        on-update #(re-frame/dispatch [:prompt-update %])
        on-validate #(re-frame/dispatch [:prompt-validate])]
    (fn prompt-component
      []

      [render @state
       {:on-cancel on-cancel
        :on-update on-update
        :on-validate on-validate}])))
