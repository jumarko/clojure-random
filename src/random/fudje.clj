(ns random.fudje
  (:require [clojure.test :refer :all]
            [fudje
             [core :refer [mocking in-background]]
             [sweet :refer :all]]))

;;; Check https://github.com/jimpil/fudje/blob/master/doc/intro.md

(defn increment [x] (inc x))

(defn decrement [x] (dec x))

(mocking [(increment 1) => (dec 1)
          (decrement 2) => 3]
         (is (= 0 (increment 1)))
         (is (= 3 (decrement 2))))

(mocking [(increment 1) => :whatever]
         (is (= :whatever (increment 2))))

(mocking []
         (is (compatible ( contains {:a (contains {:b -2})})
                         {:a {:b 2} :c 3})))
