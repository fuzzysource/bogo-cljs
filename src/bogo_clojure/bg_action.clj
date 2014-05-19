(ns bogo-clojure.bg-action
  (:require [clojure.string :as string]
            [bogo-clojure.bg-word :refer :all]
            [bogo-clojure.bg-char :refer :all]))

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
      (= :bar transform)
      (string/join [(mark->word first-consonant :nomark)
                    vowel
                    last-consonant])
      (accent? transform)
      (remove-accent-word word)
      (mark? transform)
      (string/join [first-consonant
                    (mark->word vowel :nomark)
                    last-consonant])
      :else word)))

(defn process-mark
  "Process the case when the key entered trigger a mark transformation"
  [word mark key]
  (let [new-word (mark->word word mark)]
    (if (not= new-word word)
      new-word
      (str (rollback-transformation new-word mark) key))))

(defn process-accent
  "Process the case when the key entered trigger a accent transformation"
  [word accent key]
  (let [new-word (accent->word word accent)]
    (if (not= new-word word)
      new-word
      (str (rollback-transformation new-word accent)
           key))))

(defn word-ends-with?
  "Test wether the given word ends with part"
  [word part]
  (let [last-index (.lastIndexOf word part)]
    (if (= -1 last-index)
      false
      (if (= (count word) (+ last-index (count part)))
        true
        false))))

(defn process-fast-typing
  "Some key enter trigger the insertion of new string for covenience. For example in
  Telex typing mode, press ] will inserts ư instead of the original key."
  [word added-chars keys]
  (if (word-ends-with? word added-chars)
     (str (subs word 0 (.lastIndexOf word added-chars))
          keys)
     (str word added-chars)))

(defmacro mark->
  "This macro create a mark-adding function associating with the enter key.
  Pairs describes the effected parts of the word and the
  their accompanying transformations"
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
                                   (process-mark ~'word ~(first mark-set) ~key))))))
              `(:else (str ~'word ~key)))))

(defmacro accent->
  "This macro create a accent-adding function associating with the enter key.
  Pairs describes the effected parts of the word and the
  their accompanying transformations"
  [key accent]
  `(fn [word#]
     (process-accent word# ~accent ~key)))

(defmacro add->
  "Create a function that add new chars at the end of the original word."
  [key chars]
  `(fn [~'word]
     (process-fast-typing ~'word ~chars ~key)))


