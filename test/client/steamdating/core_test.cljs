(ns steamdating.core-test
  (:require [cljs.test :refer [run-tests]]
            [devcards.core :as dc]))


(enable-console-print!)
(devcards.core/start-devcard-ui!)


(run-tests)
