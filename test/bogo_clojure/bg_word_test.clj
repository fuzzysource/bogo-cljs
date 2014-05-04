(ns bogo-clojure.bg-word-test
  (:require [clojure.test :refer :all]
            [bogo-clojure.bg-word :refer :all]))

(deftest test-split-word
  (is (= ["m" "o" "ng"] (split-word "mong")))
  (is (= ["m" "eo" ""] (split-word "meo")))
  (is (= ["nfèogh" "oo" ""] (split-word "nfèoghoo")))
  (is (= ["ng" "èo" ""] (split-word "ngèo")))
  (is (= ["qu" "a" "n"]  (split-word "quan")))
  (is (= ["gi" "a" "n"]  (split-word "gian")))
  (is (= ["g" "i" "n"]  (split-word "gin"))))

(deftest test-normalize
  (is (= "meo" (normalize "mèo")))
  (is (= "meoconrahôca" (normalize "mÈocoNrahỒcá")))
  )

(deftest test-valid-word?
  (let [test-cases {
                    "meo" true
                    "me" true
                    "qua" true
                    "mèo" true
                    "ôin" false
                    "ôN" true
                    "chuyên" true
                    "chuyêm" true
                    "chuyêch" false
                    "quôx" false
                    "quên" true
                    "quuên" false
                    "mươ" true
                    }]
    (dorun (map #(is  (let [correct? (= %2 (valid-word? %1))]
                        (when (not correct?) (println %1 "false"))
                        correct?))
                (keys test-cases)
                (vals test-cases)))))

(deftest test-add-accent-word
  (is (= "mèo" (add-accent-word "meo" :grave)))
  (is (= "Èo" (add-accent-word "Éo" :grave)))
  (is (= "miếU" (add-accent-word "miêU" :acute)))
  (is (= "ChuyỆn" (add-accent-word "ChuyÊn" :dot)))
  (is (= "mf" (add-accent-word "mf" :grave)))
  (is (= "Om" (add-accent-word "Ọm" :none)))
  (is (= "ước" (add-accent-word "ươc" :acute)))
  (is (= "tOàn" (add-accent-word "tOan" :grave)))
  (is (= "giã" (add-accent-word "gia" :tilde)))
  (is (= "quản" (add-accent-word "quan" :hook)))
  (is (= "mia" (add-accent-word "mía" :none)))
  (is (= "nằM" (add-accent-word "năM" :grave)))
  (is (= "NGuyến" (add-accent-word "NGuyên" :acute)))
  (is (= "noọng" (add-accent-word "noong" :dot)))
  (is (= "mửa" (add-accent-word "mứa" :hook)))
  (is (= "qu" (add-accent-word "qu" :acute)))
  (is (= "gì" (add-accent-word "gi" :grave)))
  (is (= "gÌn" (add-accent-word "gÍn" :grave)))
  (is (= "gIàn" (add-accent-word "gIán" :grave)))
  (is (= "quẢ" (add-accent-word "quÁ" :hook)))
  (is (= "quâN" (add-accent-word "quấN" :none)))
  (is (= "hòa" (add-accent-word "hoa" :grave)))
  (is (= "giẴc" (add-accent-word "giẲc" :tilde)))
  )

(deftest test-add-mark-word
  (is (= "đang" (add-mark-word "dang" :bar)))
  (is (= "dược" (add-mark-word "duọc" :horn)))
  (is (= "mưu" (add-mark-word "muu" :horn)))
  (is (= "bướU" (add-mark-word "bướU" :horn)))
  (is (= "Đang" (add-mark-word "Dang" :bar)))
  (is (= "phất" (add-mark-word "phát" :hat)))
  (is (= "sắc" (add-mark-word "sác" :breve)))
  (is (= "Duọc" (add-mark-word "Được" :nomark)))
  )

(deftest test-get-last-word
  (is (= "dang" (get-last-word "ddang")))
  (is (= "đăng" (get-last-word "đồngđăng")))
  (is (= "do" (get-last-word "độclậptựdo")))
  (is (= "ong" (get-last-word "mèong")))
  (is (= "" (get-last-word "")))
  )
