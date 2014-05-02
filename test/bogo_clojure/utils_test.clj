(ns bogo-clojure.utils-test
  (:require [clojure.test :refer :all]
            [bogo-clojure.utils :refer :all]))

(deftest test-lower-case?
  (let [test-cases {"a" true
                    "A" false
                    "b" true
                    "ư" true
                    "z" true
                    }]
       (dorun (map #(is (= %2 (lower-case? %1)))
             (keys test-cases)
             (vals test-cases)))))


(deftest test-to-case-of
  (is (= "c" (to-case-of "a" "C")))
  (is (= "Ư" (to-case-of "Á" "ư")))
  (is (= "ử" (to-case-of "a" "ử")))
  (is (= "Ớ" (to-case-of "Á" "ớ")))
  (is (= "x" (to-case-of "a" "X")))
  )

(deftest test-add-accent-char
  (let [test-cases {
                    ["ư" :acute] "ứ"
                    ["Ư" :dot] "Ự"
                    ["u" :dot] "ụ"
                    ["À" :grave] "À"
                    ["Ơ" :dot] "Ợ"
                    ["ụ" :none] "u"
                    ["ọ" :hook] "ỏ"
                    ["ủ" :dot] "ụ"
                    ["ủ" :hook] "ủ"
                    }]
    (dorun (map #(is (= %2 (add-accent-char (%1 0) (%1 1))) )
          (keys test-cases)
          (vals test-cases)))))
