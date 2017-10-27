(ns steamdating.models.form
  (:require [cljs.spec.alpha :as spec]
            [clojure.string :as s]
            [phrase.alpha :as phrase :refer-macros [defphraser]]
            [steamdating.services.debug :as debug]))


(spec/def :sd.form/value
  map?)


(spec/def :sd.form/error
  (spec/nilable map?))


(spec/def :sd.form/base
  :sd.form/value)


(spec/def :sd.form/edit
  :sd.form/value)


(spec/def :sd.form/form
  (spec/keys :req-un [:sd.form/edit]
             :opt-un [:sd.form/base]))


(spec/def :sd.form/validated
  (spec/and :sd.form/form
            (spec/keys :req-un [:sd.form/error])))


(spec/def :sd.form/forms
  (spec/map-of keyword? :sd.form/form))


(defn create
  [value]
  {:base value
   :edit value})


(defn field-value
  ([form field default-value]
   (get-in form (into [:edit] field) default-value))
  ([form field]
   (field-value form field nil)))


(defn field-error
  [form field]
  (get-in form (into [:error] field)))


(defn field->id
  [field]
  (s/join "." (map #(if (keyword? %) (name %) %) field)))


(defn field-input-props
  [{:keys [default-value field form on-update]
    :as props}]
  (let [id (field->id field)]
    (-> props
        (dissoc :default-value :field :form)
        (merge {:error (field-error form field)
                :on-update #(on-update field %)
                :name id
                :value (field-value form field default-value)}))))


(defn valid?
  [{:keys [error]}]
  (not error))


(defn explain->error
  [explain]
  (reduce
    (fn [error [in pb]] (assoc-in error in pb))
    {}
    (map
      (fn [pb]
        [(:in pb)
         (phrase/phrase {} pb)])
      (:cljs.spec.alpha/problems explain))))


(defn validate
  [spec {:keys [edit] :as form}]
  (let [valid? (spec/valid? spec edit)
        explain (spec/explain-data spec edit)]
    (assoc form :error (if-not valid? (explain->error explain)))))



(defn field-name
  ([context path k]
   ;; (debug/log "field-name" context path k)
   (or (get-in context (conj path k))
       (s/capitalize (name k))))
  ([context path]
   (field-name context (butlast path) (last path))))


(defphraser :default
  [context pb]
  (debug/log "phrase-default" context pb)
  "is invalid")


(defphraser #(contains? % k)
  [context {:keys [path] :as pb} k]
  (str (field-name context path k) " is required"))


(defphraser not-empty
  [context {:keys [path]}]
  (str (field-name context path) " should not be empty"))
