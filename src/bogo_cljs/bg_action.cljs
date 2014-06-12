(ns bogo-cljs.bg-action
  (:require [clojure.string :as string]
            [bogo-cljs.bg-word :refer [normalize
                                       word-structure
                                       mark->word
                                       accent->word
                                       remove-accent-word]]
            [bogo-cljs.bg-char :refer [accent?
                                       mark?]]))

(defn has-char?
  "Test wether the given word has at least one character in the charset"
  [word charset]
  (let [nword (normalize word)]
    (reduce #(or %1 (not= -1 (.indexOf nword %2)))
            false
            charset)))

(defn rollback-transformation
  "Roll back the transformation \"transform\" while conserving other transformations"
  [word transform]
  (let [[first-consonant vowel last-consonant] (word-structure word)]
    (cond
      (= "bar" transform)
      (string/join [(mark->word first-consonant "nomark")
                  vowel
                  last-consonant])
      (accent? transform)
      (remove-accent-word word)
      (mark? transform)
      (string/join [first-consonant
                  (mark->word vowel "nomark")
                  last-consonant])
      :else word)))

(defn process-mark
  "Process the case when the key entered trigger a mark transformation"
  [word mark key]
  (let [new-word (mark->word word mark)]
    (if (not= new-word word)
      new-word
      (.concat (rollback-transformation new-word mark) key))))

(defn process-accent
  "Process the case when the key entered trigger a accent transformation"
  [word accent key]
  (let [new-word (accent->word word accent)]
    (if (not= new-word word)
      new-word
      (.concat (rollback-transformation new-word accent)
           key))))

(defn word-ends-with?
  "Test wether the given word ends with part"
  [word part]
  (let [last-index (.lastIndexOf word part)]
    (if (= -1 last-index)
      false
      (if (= (.-length word) (+ last-index (.-length part)))
        true
        false))))

(defn process-fast-typing
  "Some key enter trigger the insertion of new string for covenience. For example in
  Telex typing mode, press ] will inserts ư instead of the original key."
  [word added-chars keys]
  (if (word-ends-with? word added-chars)
     (.concat (subs word 0 (.lastIndexOf word added-chars))
          keys)
     (.concat word added-chars)))



