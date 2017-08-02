(ns steamdating.debug)


(defonce debug?
  ^boolean js/goog.DEBUG)


(defn setup
  []
  (when debug?
    (enable-console-print!)
    (println "dev mode")))
