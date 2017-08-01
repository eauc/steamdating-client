(ns steamdating.core
  (:require [cljs.nodejs :as nodejs]
            compression
            express))

(nodejs/enable-util-print!)

(defn -main
  []
  (let [port (or (some-> js/process
                         (aget "env" "PORT")
                         (js/parseInt))
                 3000)]
    (doto (express)
      (.use (compression))
      (.use (.static express "resources/public"))
      (.listen port #(.log js/console "Server listening on port" port)))))

(set! *main-cli-fn* -main)
