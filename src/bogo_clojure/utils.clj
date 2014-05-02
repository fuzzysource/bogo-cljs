(ns bogo-clojure.utils
  (:gen-class)
  (:require [clojure.string :as string]))
(def SINGLE-VOWELS (str "àáảãạaằắẳẵặăầấẩẫậâèéẻẽẹeềếểễệêìíỉĩịi"
                              "òóỏõọoồốổỗộôờớởỡợơùúủũụuừứửữựưỳýỷỹỵy"))
(def SINGLE-CONSONANTS "bcdghjklmnpqrstvwxzđ")
(def LOWER-CHARS (str SINGLE-VOWELS SINGLE-CONSONANTS))

(def VOWELS ["a", "ai", "ao", "au", "ay", "e", "eo", "i", "ia", "iu",
             "iê", "iêu", "o", "oa", "oai", "oao", "oay", "oe", "oeo",
             "oi", "oo", "oă", "u", "ua", "ui", "uy", "uya", "uyu",
             "uyê", "uâ", "uây", "uê", "uô", "uôi", "uơ", "y", "yê",
             "yêu", "â", "âu", "ây", "ê", "êu", "ô", "ôi","ă", "ơ",
             "ơi", "ư", "ưa", "ưi", "ưu", "ươ", "ươi", "ươu"])

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

  (if (single-vowel? char)
    (let [ new-accent (.indexOf ACCENTS accent)
          lower-char (string/lower-case char)
          accent-pos (.indexOf SINGLE-VOWELS lower-char)
          current-accent (mod accent-pos 6)
          ]
      (to-case-of char
                  (str (get SINGLE-VOWELS
                            (+ accent-pos (- new-accent current-accent))))))
    char
    ))

(defn remove-accent-char
  [c]
  (add-accent-char c :none)
  )

(defn add-mark-char
  ":hat :breve :horn :bar :nomark"
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
                                            )))
                     current-accent)))
