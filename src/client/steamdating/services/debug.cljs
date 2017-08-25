(ns steamdating.services.debug)


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
