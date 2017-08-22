(ns steamdating.components.generics.form-test
  (:require [cljs.spec.alpha :as spec]
            [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [steamdating.components.generics.form :refer [form]]
            [steamdating.components.generics.input :refer [input]]
            [steamdating.models.form :as form-model]))


(spec/def ::text
  (spec/and string? not-empty))


(spec/def ::number
  (spec/and number? (comp not #{42 71})))


(spec/def ::select
  #{"opt1" "opt2" "opt3"})


(spec/def ::select-multiple
  (spec/coll-of ::select :kind vector?))


(spec/def ::checkbox
  boolean?)


(spec/def ::textarea
  string?)


(spec/def ::test
  (spec/keys :req-un [::text]
             :opt-un [::number
                      ::select
                      ::select-multiple
                      ::checkbox]))


(defcard-rg form-test
  "Generic form component"
  (fn [state]
    (let [on-submit #(println "submit!")
          update-field (fn [field value]
                         (swap! state assoc-in (into [:edit] field) value)
                         (swap! state form-model/validate ::test))]
      [form @state {:label "Test form"
                    :on-submit on-submit}
       [:div
        [input {:type "text"
                :label "Text field"
                :field [:text]
                :state @state
                :on-update update-field
                :required true
                :autofocus true
                :order "1"}]
        [input {:type "number"
                :label "Numeric field"
                :field [:number]
                :state @state
                :on-update update-field
                :order "2"}]
        [input {:type "select"
                :label "Select field"
                :field [:select]
                :state @state
                :on-update update-field
                :options {:opt2 "Option 2"
                          :opt4 "Option 4"
                          :opt3 "Option 3"
                          :opt1 "Option 1"}
                :order "3"}]
        [input {:type "select"
                :label "Select multiple field"
                :multiple true
                :field [:select-multiple]
                :state @state
                :on-update update-field
                :options {:opt3 "Option 3"
                          :opt1 "Option 1"
                          :opt4 "Option 4"
                          :opt2 "Option 2"}
                :order "4"}]
        [input {:type "checkbox"
                :label "Checkbox field"
                :field [:checkbox]
                :state @state
                :on-update update-field
                :order "5"}]
        [input {:type "textarea"
                :label "Textarea field"
                :field [:textarea]
                :state @state
                :on-update update-field
                :order "6"}]]]))
  (reagent/atom (form-model/validate
                  {:edit {:text "hello"
                          :number 42
                          :checkbox true
                          :select-multiple ["opt1" "opt3"]}}
                  ::test))
  {:inspect-data true
   :history true})
