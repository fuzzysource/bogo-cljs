(ns bogo-clojure.bg-char
  (:gen-class)
  (:require [clojure.string :as string]))
(def SINGLE-VOWELS (str "àáảãạaằắẳẵặăầấẩẫậâèéẻẽẹeềếểễệêìíỉĩịi"
                              "òóỏõọoồốổỗộôờớởỡợơùúủũụuừứửữựưỳýỷỹỵy"))
(def SINGLE-CONSONANTS "bcdghjklmnpqrstvwxzđ")
(def LOWER-CHARS (str SINGLE-VOWELS SINGLE-CONSONANTS))
(def ACCENTS [:grave :acute :hook :tilde :dot :none])
(def MARKS [:hat :breve :horn :bar :nomark])

(defn lower-case?
  [c]
  (if (not= -1 (.indexOf LOWER-CHARS c))
    true
    false))

(defn to-case-of
  "Change case of c2 to which of c1"
  [c1 c2]
  (if (lower-case? c1)
    (string/lower-case c2)
    (string/upper-case c2)))

(defn single-vowel?
  [c]
  (if (not= -1 (.indexOf SINGLE-VOWELS (string/lower-case c)))
    true
    false))

(defn single-consonant?
  [c]
  (not  (single-vowel? c))
  )

(defn get-accent-char
  [c]
  (let [accent-pos (.indexOf SINGLE-VOWELS (string/lower-case c))
        current-accent (mod accent-pos 6)]
    (if (not= -1 accent-pos)
      (ACCENTS current-accent)
      :none
      )))

(defn add-accent-char
  "Add mark to a character.
Avaiable accents: :grave :acute :hook :tilde :dot :none"
  [char accent]

  (if (char? char)
    (add-accent-char (str char) accent)
    (if (single-vowel? char)
      (let [ new-accent-index (.indexOf ACCENTS accent)
            c (string/lower-case char)
            vowel-index (.indexOf SINGLE-VOWELS c)
            current-accent-index (mod vowel-index 6)
            ]
        (to-case-of char
                    (str (get SINGLE-VOWELS
                              (+ vowel-index (- new-accent-index
                                                current-accent-index))))))
      char)))

(defn remove-accent-char
  [c]
  (add-accent-char c :none)
  )

(defn add-mark-char
  "Add mark to a char.
available marks are  :hat :breve :horn :bar :nomark
if given char is not possible to add mark, return the original char"
  [char mark]
  (let [c (remove-accent-char (string/lower-case char))
        current-accent (get-accent-char char)
        ]
    (add-accent-char (to-case-of char
                                 (case mark
                                   :hat (cond
                                         (contains? #{"a" "ă" "â"} c) "â"
                                         (contains? #{"o" "ô" "ơ"} c) "ô"
                                         :else char)
                                   :breve (cond
                                           (contains? #{"a" "ă" "â"} c) "ă"
                                           :else char)
                                   :horn (cond
                                          (contains? #{"u" "ư"} c) "ư"
                                          (contains? #{"o" "ô" "ơ"} c) "ơ"
                                          :else char)
                                   :bar (cond
                                         (contains? #{"d" "đ"} c) "đ"
                                         :else char)
                                   :nomark (cond
                                            (contains? #{"a" "ă" "â"} c) "a"
                                            (contains? #{"o" "ô" "ơ"} c) "o"
                                            (contains? #{"u" "ư"} c) "u"
                                            (contains? #{"d" "đ"} c) "d"
                                            :else char
                                            )
                                   char))
                     current-accent)))
