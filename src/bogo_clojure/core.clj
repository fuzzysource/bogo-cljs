(ns bogo-clojure.core
  (:gen-class)
  (:require [clojure.string :as string]
            [bogo-clojure.bg-word :refer :all]
            [bogo-clojure.bg-telex :refer :all]))

(defn get-action
  ;; Action is a function taking in current string, make transform,
  ;; return the new string
  [key typemode]
  (if (contains? typemode key)
    (get typemode key)
    :addchar))

(defn process-key*
  ;; capitalize the last character if the rollback was triggered by an
  ;; upper-case character
  [key old-string new-string]
  (if (and (= (inc (count old-string)) (count new-string))
           (= (string/lower-case key) (str (last new-string))))
    (str (subs new-string 0 (count old-string)) key)
    new-string))

(defn process-key
  ;; Return a new string that is resulted by typing new character into
  ;; the old one.
  ([astring key]
     (process-key astring key TELEX))
  ([astring key typemode]
     (let [[first-word last-word] (grammar-split-word astring)
           strkey (str key)
           lkey (string/lower-case strkey)
           action (get-action lkey typemode)]
       (str first-word
            (refine-word (process-key* strkey
                                       last-word
                                       (if (fn? action)
                                         (action last-word)
                                         (str last-word strkey))))))))

(defn process-sequence
  ;; Return the string that is resulted by typing the given sequence
  ;; of keys.
  ([sequence]
     (process-sequence sequence TELEX))
  ([sequence typemode]
     (reduce (fn [word key] (process-key word key typemode))
             ""
             sequence)))
