(ns bogo-clojure.core
  (:gen-class)
  (:require [clojure.string :as string]
            [bogo-clojure.bg-word :refer :all]
            [bogo-clojure.bg-telex :refer :all]))


(defn get-action
  ;; Action is is a pair that. The first element is the transform. The
  ;; second element is a function taking in current string, make
  ;; transform, return the new string
  [key typemode]
  (if (contains? typemode key)
    (get typemode key)
    [:addchar key]))

(defn process-key
  ([astring key]
     (process-key astring key TELEX))
  ([astring key typemode]
     (let [[first-word last-word] (grammar-split-word astring)
           [transform action] (get-action key typemode)]
       (str first-word
            (if (fn? action)
              (let [new-word (action last-word)]
                (if (not= new-word last-word)
                  new-word
                  (str (rollback new-word transform) key)
                  ))
              (str last-word key))))))

(defn process-sequence
  [sequence typemode]
  (reduce (fn [word key] (process-key word key typemode))
           ""
           sequence))
