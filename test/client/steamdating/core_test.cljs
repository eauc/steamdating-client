(ns steamdating.core-test
  (:require [cljs.test :refer [run-tests]]
            [devcards.core :as dc]
            [steamdating.components.form.input-test]))


(enable-console-print!)
(devcards.core/start-devcard-ui!)


(run-tests)
