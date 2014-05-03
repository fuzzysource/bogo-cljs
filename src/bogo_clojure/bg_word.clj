(ns bogo-clojure.bg-word
  (:gen-class)
  (:require [clojure.string :as string]
            [bogo-clojure.bg-char :refer :all]))

;;; This namespace includes function to process single **VALID**
;;; vietnamese words. Any words serving as parameter of functions in
;;; this namespace have to be a valid vietnamese word or be able to
;;; develop to a valid vietnamese word.

(defn fuzzy-split-word
  "Split a word into 3 components: first-consonant, vowel,
last-consonant * last-consonant: the longest substring of consonants
at the end of given word * vowel: the longest substring of vowel next
to the last-consonant * first-consonant: the remaining characters.

Return value is a vector with the form:
[first-consonant vowel last-consonant]

Usage: (fuzzy-split-word word)"
  [word]
  (let [rword (reverse word)]
    (map string/reverse
         (reduce (fn [comps char]
                   (let [c  (str char)]
                     (cond
                      (and (single-consonant? c) (= (comps 1) ""))
                      ["" "" (str (comps 2) c)]
                      (and (single-vowel? c) (= (comps 0) ""))
                      ["" (str (comps 1) c) (comps 2)]
                      :else [(str (comps 0) c) (comps 1) (comps 2)]
                      )
                     ))
                 ["" "" ""]
                 rword))
    )
  )

(defn split-word
  [word]
  (fuzzy-split-word word))
