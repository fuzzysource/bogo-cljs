(ns bogo-clojure.core-test
  (:require [clojure.test :refer :all]
            [bogo-clojure.core :refer :all]))

(deftest test-last-word
  "Return the longest part at the end of the string that can be developed to
a valid vietnamese word"
  (is (= "mèo" (last-word "mèo")))
  (is (= "gà" (last-word) "mèogà"))
  (is (= "r" (last-word "meor")))
  (is (= "oo" (last-word "meoo")))
  )

(deftest separate
  "Seperate a string into 3 parts: first-consonant, vowel, last-consonant"
  (let [test-cases {
                    "a" ("" "a" "")
                    "ab" ("ab" "" "")
                    "meo" ("m" "eo" "")
                    "dang" ("d" "a" "ng")
                    }]
    (dorun (map #(is %2 (separate %1))
                (keys test-cases)
                (vals test-cases))))
    )
  )
(deftest fuzzy-valid?
  "True if the argument is an valid vietnamese word, fuzzy means that the result
is not consistent with the vietnamese writing rules. The feature allows the
freedom in typing name, etc."
  (let [test-cases {
                    "meo" True
                    "menh" True
                    "monh" True
                    "mox" False
                    }]
    (dorun (map #(is %2 (fuzzy-valid? %1))
                (keys test-cases)
                (vals test-cases))))
  )

(deftest test-transform
  (testing "Add accent"
    (is (= "mèo" (transform "meo" :effect :grave)))
    (is (= "méo" (transform "meo" :effect :acute)))
    (is (= "mẻo" (transform "meo" :effect :hook)))
    (is (= "mẽo" (transform "meo" :effect :titde)))
    (is (= "mẹo" (transform "meo" :effect :dot)))
    (is (= "meo" (transform "méo" :effect :plain)))
    )
  (testing "Add mark"
    (is (= "â" (transform "da" :effect :hat)))
    (is (= "ô" (transform "o" :effect :hat)))
    (is (= "ă" (transform "ă" :effect :breve)))
    (is (= "ơ" (transform "o" :effect :horn)))
    (is (= "ư" (transform "u" :effect :horn)))
    (is (= "đ" (transform "d" :effect :bar)))
    )
  )

