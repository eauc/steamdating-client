(ns steamdating.core-test
  (:require [cljs.test :refer [run-tests]]
            [devcards.core :as dc]
            [steamdating.components.form.input-test]
            [steamdating.components.player.edit-test]
            [steamdating.components.player.list-test]
            [steamdating.components.round.next-test]
            [steamdating.components.round.nth-test]))


(enable-console-print!)
(devcards.core/start-devcard-ui!)


(run-tests)
