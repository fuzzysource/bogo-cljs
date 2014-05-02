(ns bogo-clojure.core
  (:gen-class))

(defn -main [& args]
  (println "Welcome to my project! These are your args:" args))

(defn separate
  "Separate a string into 3 substrings: first-consonant, vowel, and last-consonant.
The string is divied so that:
* last-consonant: all consecutive consonants standing at the end of the string
* vowel: longest string consecutive vowels on the left of last-consonant
* first-consonant: the remaining characters"
  [word]
  )
