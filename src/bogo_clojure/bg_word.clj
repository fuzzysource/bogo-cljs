(ns bogo-clojure.bg-word
  (:gen-class)
  (:require [clojure.string :as string]
            [bogo-clojure.bg-char :refer :all]))

;;; This namespace includes function to process single **VALID**
;;; vietnamese words. Any words serving as parameter of functions in
;;; this namespace have to be a valid vietnamese word or be able to
;;; develop to a valid vietnamese word.

(def CONSONANTS #{"b" "c" "ch" "d" "g" "gh" "gi" "h" "k"
                    "kh" "l" "m" "n" "ng" "ngh" "nh" "p" "ph"
                    "qu" "r" "s" "t" "th" "tr" "v" "x" "đ" ""})

(def TERMINAL_CONSONANTS #{"c" "ch" "m" "n" "ng" "nh" "p" "t" ""})

(def VOWELS #{ "a" "ai" "ao" "au" "ay" "e" "eo" "i" "ia" "iu"
               "iê" "iêu" "o" "oa" "oai" "oao" "oay" "oe" "oeo"
               "oi" "oo" "oă" "u" "ua" "ui" "uy" "uya" "uyu"
               "uyê" "uâ" "uây" "uê" "uô" "uôi" "uơ" "y" "yê"
               "yêu" "â" "âu" "ây" "ê" "êu" "ô" "ôi" "ă" "ơ"
               "ơi" "ư" "ưa" "ưi" "ưu" "ươ" "ươi" "ươu" ""})

(def TERMINAL_VOWELS #{ "ai" "ao" "au" "ay" "eo" "ia" "iu" "iêu"
                        "oai" "oao" "oay" "oeo" "oi" "ua" "ui"
                        "uya" "uyu" "uây" "uôi" "uơ" "yêu" "âu"
                        "ây" "êu" "ôi" "ơi" "ưa" "ưi" "ưu" "ươi"
                        "ươu" ""})

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
  "Similar to fuzzy-split-word but this function is more appropriate when
 processing qu and gi"
  [word]
  (let [comps (fuzzy-split-word word)
        last-comp0 (str (last (nth comps 0)))
        first-comp1 (str (first (nth comps 1)))]
    (if (or (and (= "q" (string/lower-case last-comp0))
                 (= "u" (string/lower-case first-comp1)))
            (and (= "g" (string/lower-case last-comp0))
                 (= "i" (string/lower-case first-comp1))
                 (> (count (nth comps 1)) 1)))
      [(str (nth comps 0) first-comp1) (subs (nth comps 1) 1) (nth comps 2)]
      comps)))

(defn normalize
  "Lower case and remove any accent"
  [word]
  (reduce #(str %1 (string/lower-case (remove-accent-char  (str %2))))
          ""
          word))

(defn valid-word-qu-gi?
  [comps]
  (let [[first-consonant vowel last-consonant] comps]
       (cond
        (and (= first-consonant "qu") (= (subs vowel 0 1) "u")) false
        (and (= first-consonant "gi") (= (subs vowel 0 1) "i")) false
        :else true)))

(defn valid-word?
  "Return true if the given word is a valid vietnamese words or is extendable to
a valid vietnamese word"
  [word]
  (let [comps (split-word (normalize word))
        [first-consonant vowel last-consonant] comps]
    (if (and (contains? CONSONANTS first-consonant)
             (contains? VOWELS vowel)
             (contains? TERMINAL_CONSONANTS last-consonant)
             (valid-word-qu-gi? comps))
      (if (contains? TERMINAL_VOWELS vowel)
        (= "" last-consonant)
        (case last-consonant
          "ch" (contains? #{"a" "ê" "uê" "i" "uy" "oa"} vowel)
          "c" (not (= "ơ" vowel))
          true
          ))
      false)))
