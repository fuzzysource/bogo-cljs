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
  (if (not= -1 (.indexOf SINGLE-VOWELS c))
    true
    false))

(defn add-accent-char
  "Add mark to a character.
Avaiable accents: :grave :acute :hook :tilde :dot :none"
  [char accent]
  (let [ new-accent  (get {:grave 0 :acute 1 :hook 2 :tilde 3 :dot 4 :none 5}
                          accent)
        lower-char (string/lower-case char)
        accent-pos (.indexOf SINGLE-VOWELS lower-char)
        current-accent (mod accent-pos 6)
        ]
    (to-case-of char
                (str (get SINGLE-VOWELS
                          (+ accent-pos (- new-accent current-accent)))))))

