(ns bogo-clojure.bg-macro
  (:gen-class)
  (:require [clojure.string :as string]
            [bogo-clojure.bg-word :refer :all]))

(defn has-char?
  [word set]
  (let [nword (normalize word)]
    (reduce #(or %1 (not= -1 (.indexOf nword %2)))
            false
            set))
  )

(defmacro add-mark
  [key & pairs]
  `(fn [~'word]
        ~(concat '(cond)
                 (loop [mark-set pairs
                        actions  '()]
                   (if (nil? mark-set)
                     actions
                  (recur (next (next mark-set))
                         (concat actions
                                 `((has-char? ~'word ~(second mark-set))
                                   (add-mark-word ~'word ~(first mark-set)))))))
                 `(:else (str ~'word ~key)))))

(defmacro add-accent
  [key accent]
  `(fn [word#]
     (add-accent-word word# ~accent)
     ))
