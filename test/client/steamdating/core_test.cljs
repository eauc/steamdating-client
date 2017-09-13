(ns steamdating.core-test
  (:require [cljs.test :refer-macros [run-tests]]
            [pjstadig.humane-test-output]
            [steamdating.components.generics.form-test]
            [steamdating.components.player.list-test]
            [steamdating.components.ranking.bests-test]
            [steamdating.components.ranking.list-test]
            [steamdating.components.round.round-test]
            [steamdating.components.round.summary-test]
            [steamdating.models.player-test]
            [steamdating.models.round-test]))


(enable-console-print!)
(devcards.core/start-devcard-ui!)


(run-tests 'steamdating.models.player-test
           'steamdating.models.round-test)
