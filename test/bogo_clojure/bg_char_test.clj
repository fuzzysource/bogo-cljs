(ns bogo-clojure.bg-char-test
  (:require [clojure.test :refer :all]
            [bogo-clojure.bg-char :refer :all]))

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
                    ["o" :hook] "ỏ"
                    ["õ" :hook] "ỏ"
                    ["a" :hook] "ả"
                    ["Ả" :acute] "Á"
                    ["Y" :grave] "Ỳ"
                    }]
    (dorun (map #(is (= %2 (add-accent-char (%1 0) (%1 1))) )
          (keys test-cases)
          (vals test-cases)))))

(deftest test-add-mark-char
  (let [test-cases {
                    ["u" :horn] "ư"
                    ["U" :horn] "Ư"
                    ["O" :horn] "Ơ"
                    ["đ" :horn] "đ"
                    ["d" :bar] "đ"
                    ["a" :hat] "â"
                    ["â" :breve] "ă"
                    ["Ấ" :breve] "Ắ"
                    ["Ă" :nomark] "A"
                    ["Ồ" :breve] "Ồ"
                    ["Ồ" :horn] "Ờ"
                    ["Đ" :bar] "Đ"
                    ["â" :nomark] "a"
                    ["x" :nomark] "x"
                    ["Ờ" :nomark ] "Ò"
                    }]
    (dorun (map #(is (= %2 (add-mark-char (%1 0) (%1 1))) )
          (keys test-cases)
          (vals test-cases)))))
