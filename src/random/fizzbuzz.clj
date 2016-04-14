(ns random.fizzbuzz)

(defn fizzbuzzing [check-number-fn]
  "Prints fizzbuzz sequence from 1 to 30 using given check-number-fn to decide
   how numbers should be represented."
  (doseq [n (range 1 31)]
    (println (check-number-fn n))))

;; first try - plain if
(fizzbuzzing (fn [n]
               (if (and (zero? (mod n 5)) (zero? (mod n 3)))
                 "FizzBuzz"
                 (if (zero? (mod n 5))
                   "Buzz"
                   (if ( zero? (mod n 3))
                     "Fizz"
                     n)))))

;; second round - using cond
(defn divisible-by
  "Returns true if m is divisible by n, false otherwise."
  [m n]
  (zero? (mod m n)))

(fizzbuzzing (fn [n]
               (let [divisible-by-5 (divisible-by n 5)
                     divisible-by-3 (divisible-by n 3)]
                 (cond
                  (and divisible-by-5 divisible-by-3) "FizzBuzz"
                  divisible-by-5 "Buzz"
                  divisible-by-3 "Fizz"
                  :default n))))


;; third round - case
(fizzbuzzing (fn [n]
               (case (mod n 15)
                 0 "FizzBuzz"
                 (3 6 9 12) "Fizz"
                 (5 10) "Buzz"
                 n)))
