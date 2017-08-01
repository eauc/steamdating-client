(ns steamdating.core-test
  (:require [cljs.test :refer-macros [is testing run-tests]]
            [devcards.core :as dc :refer-macros [deftest]]))

(deftest cljs-test-integration
  "## Here are some example tests"
  (testing "testing context 1"
    (is (= (+ 3 4 55555) 4) "This is the message arg to an 'is' test")
    (is (= (+ 1 0 0 0) 1) "This should work")
    (is (= 1 3))
    (is false)
    (is (throw "errors get an extra red line on the side")))
  "Top level strings are interpreted as markdown for inline documentation."
  (testing "testing context 2"
    (is (= (+ 1 0 0 0) 1))
    (is (= (+ 3 4 55555) 4))
    (is false)
    (testing "nested context"
      (is (= (+ 1 0 0 0) 1))
      (is (= (+ 3 4 55555) 4))
      (is false))))

(enable-console-print!)
(devcards.core/start-devcard-ui!)

(run-tests)
