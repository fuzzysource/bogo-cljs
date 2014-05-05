(ns bogo-clojure.core
  (:gen-class))

(defn do-stack-transform
  [stack transform option]
  (cons transform stack)
  )
