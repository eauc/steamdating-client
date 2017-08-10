(ns steamdating.components.generics.form-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [steamdating.components.generics.form :refer [render-form]]
            [steamdating.components.generics.input :refer [input]]
            [steamdating.models.form :as form]
            [cljs.spec.alpha :as spec]))


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
    (let [submit #(println "submit!")
          update-field (fn [field value]
                         (swap! state assoc-in [:edit field] value)
                         (swap! state form/validate ::test))]
      [render-form state {:label "Test form"
                          :name :test
                          :spec ::test
                          :submit submit
                          :update-field update-field}
       [input {:name :text
               :label "Text field"
               :type "text"
               :required true
               :autofocus true
               :order "1"}]
       [input {:name :number
               :label "Numeric field"
               :type "number"
               :order "2"}]
       [input {:name :select
               :label "Select field"
               :type "select"
               :order "3"
               :options {:opt1 "Option 1"
                         :opt2 "Option 2"
                         :opt3 "Option 3"
                         :opt4 "Option 4"}}]
       [input {:name :select-multiple
               :label "Select multiple field"
               :type "select"
               :order "4"
               :multiple true
               :options {:opt1 "Option 1"
                         :opt2 "Option 2"
                         :opt3 "Option 3"
                         :opt4 "Option 4"}}]
       [input {:name :checkbox
               :label "Checkbox field"
               :type "checkbox"
               :order "5"}]
       [input {:name :textarea
               :label "Textarea field"
               :type "textarea"
               :order "6"}]]))
  (reagent/atom (form/validate
                  {:edit {:text "hello"
                          :number 42
                          :checkbox true
                          :select-multiple ["opt1" "opt3"]}}
                  ::test))
  {:inspect-data true
   :history true})
