(ns steamdating.services.debug
  (:require [cljs.spec.alpha :as spec]))


(defonce debug?
  ^boolean js/goog.DEBUG)


(defn setup
  []
  (when debug?
    (enable-console-print!)
    (println "dev mode")))


(defn spy
  [msg data]
  (when debug?
    (println msg data))
  data)


(defn log
  [& args]
  (when debug?
    (apply println args)))


(defn spec-valid?
  [s v]
  (or (not debug?)
      (spec/valid? s v)))
