(ns steamdating.components.player.edit
  (:require [steamdating.components.generics.form :refer [form]]
            [steamdating.components.generics.input :refer [input]]
            [cljs.spec.alpha :as spec]))


(spec/def ::name-uniq
  (comp not #{"toto" "toti"}))


(spec/def ::name
  (spec/and string?
            not-empty
            ::name-uniq))


(spec/def ::origin
  (spec/and string?))


(spec/def ::faction
  (spec/and string?
            not-empty))


(spec/def ::list
  (spec/and string?
            not-empty))


(spec/def ::lists
  (spec/coll-of ::list :kind vector?))


(spec/def ::notes
  (spec/and string?))


(spec/def ::player
  (spec/keys :req-un [::name]
             :opt-un [::origin
                      ::faction
                      ::lists
                      ::notes]))


(defn edit
  []
  [form {:name :player
         :label "Create Player"
         :on-submit :steamdating.players/create
         :spec ::player}
   [input {:name :name
           :label "Name"
           :type "text"
           :required "required"
           :autofocus "autofocus"
           :order "1"}]
   [input {:name :origin
           :label "Origin"
           :type "number"
           :order "2"}]
   [input {:name :faction
           :label "Faction"
           :type "select"
           :options {}
           :order "3"}]
   [input {:name :lists
           :label "Lists"
           :type "select"
           :options {}
           :multiple "multiple"
           :order "4"}]
   [input {:name :notes
           :label "Notes"
           :type "textarea"
           :order "6"}]])
