(ns random.fluokitten
  (:require [uncomplicate.fluokitten [core :refer :all] [jvm :refer :all]]))

;; Values
((partial + 3) 2)

;;; Values can be put into boxes - vectors, lists, etc.
;;; But there exist even simpler box -> Maybe
;;;
;;; If you apply function to the value, you will get different values depending on the context.
;;; This is the idea that Functions, Applicatives, Monads, Arows, etc. are all based on.
;;;
;;; E.g. Maybe data type defines two related contexts
(def just2 ( just 2))
;; or
nil ;=> sth. went wrong with putting the value in the box or the value itself is wrong
(maybe? just2) ; => true
(maybe? nil) ; => true
(maybe? 2) ; => false


;;; There are different kinds of boxes - e.g. Functions, Applicative Functors, Monads



;;; Functors.
;;; They solve the problem with applying functions to values

;; ((partial + 3) just2) NOT POSSIBLE!
(fmap (partial + 3) just2) ; => result is boxed
(fmap (partial + 3) nil) ; => nil is returned, no exception is thrown!

;; notice that fmap preserves the collection type - e.g. for vector it returns vector
(fmap inc [1 2 3])
(fmap + [1 2 3] [1 2 3 4])
(fmap + (list 1 2 3) [1 2] (sorted-set 5 3 1 2))
(fmap + {:a 1 :b 2} {:a 3 :c 4} {:d 5})
(fmap * (atom 2) (ref 3) (atom 4))


;; functions are Functors too: fmap f g = f comp g

;; Example
;; It's silly because clojure maps handle nils ok
(def posts {1 {:title "Apples"}})

(defn find-post [post-id]
  (if-let [post (posts post-id)]
    (just post)
    nil))

(defn get-post-title [post]
  (post :title))

(fmap get-post-title (find-post 1)) ; => (just "Apples")
(fmap get-post-title (find-post 2)) ; => nil

(fmap (partial + 3) [2 4 6])

(def foo (fmap (partial + 3) (partial * 2)))
(foo 10) ; => 23



;;; Applicative Functors take it to the next level: values are wrapped in a context; functions are wrapped in a context too!
;;;  Applicative is a box that can accept functions to be applied not only bare, but also boxed.

(def add3 (partial + 3))

(just add3)

(fapply (just add3) (just 2))

;; vectors are also applicative and can carry many functions to be applied
(fapply [(partial * 2) (partial + 3)] [1 2 3])
(fmap (partial * 2) (partial + 3) [1 2 3])

(fapply [inc dec (partial * 10) ] [1 2 3])

;; all wrapped functions are applied to all wrapped data
(fapply [+ -] [1 2] [3 4])



;;; Monad takes the function that accepts (unwrapped) value and returns wrapped value and apply it to the wrapped value.

(defn half [x]
  (if (even? x)
    (just (/ x 2))
    nil))

(half 4)
(half 5)
;; you can apply half directly to wrapped value
(half (just 4))

;; However, since our box just is also Monad, it can handle such cases with the help of bind function
(bind (just 4) half)
(bind (just 3) half)
;; the >>= is the same as bind for single box
(>>= (just 4) half)
;; but different for multiple ones
(>>= (just 20) half half half)
(>>= (just 20) half half)

(bind [1 2 3] #(return (inc %) (dec %)))


;;; Monoids - offer default operation "op" on some type, and an identity element "id".
;;; op has to be closed and associative.

(op [1 2] [3])



;;; Foldable - useful if we want to get raw data back from the box
;;; fold is similar to reduce but more powerful -> it can work without explicitly supplied function
;;; and can accept multiple foldables

(fold (atom 3))
(fold [])
(fold [1 2 3])
(fold [[1 2 3] [3 4 5 4 3]  [3 2 1]])
(fold (fold [[1 2 3] [3 4 5 4 3] [3 2 1]]))
