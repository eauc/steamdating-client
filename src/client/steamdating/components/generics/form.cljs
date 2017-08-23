(ns steamdating.components.generics.form
  (:require [re-frame.core :as re-frame]
            [steamdating.components.generics.icon :refer [icon]]
            [steamdating.models.form :as form]
            [steamdating.services.debug :as debug]))


(defn form
  [state {:keys [label on-submit]} children]
  (let [valid? (form/is-valid state)]
    [:form.sd-Form
     {:no-validate true
      :on-submit (fn [event]
                   (.preventDefault event)
                   (when valid? (on-submit))
                   false)}
     [:fieldset.sd-Form-group
      [:legend.sd-Form-legend label]
      children
      [:button.sd-Form-submit
       {:class (when-not valid? "sd-Form-disabled")
        :type "submit"
        :value "submit"}
       [icon "check"]]]]))
