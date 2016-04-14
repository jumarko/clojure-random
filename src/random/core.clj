(ns random.core
  (:gen-class))

(defn stupid []
  (if (> 1 0)
    (println "Hello")
    nil))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
