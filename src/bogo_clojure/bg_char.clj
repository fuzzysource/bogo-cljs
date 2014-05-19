(ns bogo-clojure.bg-char
  (:require [clojure.string :as string]))

(def SINGLE-VOWELS (str "àáảãạaằắẳẵặăầấẩẫậâèéẻẽẹeềếểễệêìíỉĩịi"
                              "òóỏõọoồốổỗộôờớởỡợơùúủũụuừứửữựưỳýỷỹỵy"))
(def SINGLE-CONSONANTS "bcdghjklmnpqrstvwxzđ")
(def LOWER-CHARS (str SINGLE-VOWELS SINGLE-CONSONANTS))
(def ACCENTS [:grave :acute :hook :tilde :dot :none])
(def MARKS [:hat :breve :horn :bar :nomark])

(defn accent?
  [transform]
  (not= -1 (.indexOf ACCENTS transform)))

(defn mark?
  [transform]
  (not= -1 (.indexOf MARKS transform)))

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
  [char]
  (let [c (str char)]
    (if (not= -1 (.indexOf SINGLE-VOWELS (string/lower-case c)))
      true
      false)))

(defn single-consonant?
  [c]
  (not  (single-vowel? c))
  )

(defn char->accent
  [char]
  (let [c (string/lower-case (str char))]
    (if (single-vowel? c)
      (ACCENTS (mod (.indexOf SINGLE-VOWELS c) 6))
      :none)))

(defn char->mark
  [char]
  (let [c (string/lower-case (str char))]
    (if (single-vowel? c)
      (let [vowel-index (.indexOf SINGLE-VOWELS c)]
        ([:nomark :breve :hat :nomark :hat :nomark
          :nomark :hat :horn :nomark :horn :nomark] (quot vowel-index 6)))
      (if (= "đ" c)
        :bar
        :nomark))))

(defn accent->char
  "Add mark to a character.
Avaiable accents: :grave :acute :hook :tilde :dot :none"
  [char accent]

  (if (char? char)
    (accent->char (str char) accent)
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
  (accent->char c :none)
  )

(defn mark->char*
  [char mark]
   (case mark
     :hat (cond
           (contains? #{"a" "ă" "â"} char) "â"
           (contains? #{"o" "ô" "ơ"} char) "ô"
           (contains? #{"e"} char) "ê"
           :else char)
     :breve (cond
             (contains? #{"a" "ă" "â"} char) "ă"
             :else char)
     :horn (cond
            (contains? #{"u" "ư"} char) "ư"
            (contains? #{"o" "ô" "ơ"} char) "ơ"
            :else char)
     :bar (cond
           (contains? #{"d" "đ"} char) "đ"
           :else char)
     :nomark (cond
              (contains? #{"a" "ă" "â"} char) "a"
              (contains? #{"o" "ô" "ơ"} char) "o"
              (contains? #{"u" "ư"} char) "u"
              (contains? #{"d" "đ"} char) "d"
              :else char)
     char)
  )

(defn mark->char
  "Add mark to a char.
available marks are  :hat :breve :horn :bar :nomark
if given char is not possible to add mark, return the original char"
  [char mark]
  (if (char? char)
    (mark->char (str char) mark)
    (let [c (remove-accent-char (string/lower-case char))
          current-accent (char->accent char)]
      (accent->char (to-case-of char
                                (mark->char* c mark) )
                       current-accent))))

(defn remove-mark-char
  [c]
  (mark->char c :nomark))

