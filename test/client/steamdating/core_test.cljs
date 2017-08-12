(ns steamdating.core-test
  (:require [cljs.test :refer-macros [run-tests]]
            [steamdating.components.generics.form-test]
            [steamdating.components.player.list-test]
            [steamdating.models.player-test]))


(enable-console-print!)
(devcards.core/start-devcard-ui!)


(run-tests 'steamdating.models.player-test)
