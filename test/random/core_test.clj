(ns random.core-test
  (:require [clojure.test :refer :all]
            [random.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (=
         {:person {:first-name "Juraj" :last-name "Martinka"}}
         {:person {:first-name "Juraj" :last-name "Martin"}}))))
