(ns bogo-clojure.bg-word-test
  (:require [clojure.test :refer :all]
            [bogo-clojure.bg-word :refer :all]))

(deftest test-word-structure
  (is (= ["m" "o" "ng"] (word-structure "mong")))
  (is (= ["m" "eo" ""] (word-structure "meo")))
  (is (= ["nfèogh" "oo" ""] (word-structure "nfèoghoo")))
  (is (= ["ng" "èo" ""] (word-structure "ngèo")))
  (is (= ["qu" "a" "n"]  (word-structure "quan")))
  (is (= ["gi" "a" "n"]  (word-structure "gian")))
  (is (= ["g" "i" "n"]  (word-structure "gin"))))

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
                    "duo" true
                    }]
    (dorun (map #(is  (let [correct? (= %2 (valid-word? %1))]
                        (when (not correct?) (println %1 "false"))
                        correct?))
                (keys test-cases)
                (vals test-cases)))))

(deftest test-accent->word
  (is (= "mèo" (accent->word "meo" :grave)))
  (is (= "Èo" (accent->word "Éo" :grave)))
  (is (= "miếU" (accent->word "miêU" :acute)))
  (is (= "ChuyỆn" (accent->word "ChuyÊn" :dot)))
  (is (= "mf" (accent->word "mf" :grave)))
  (is (= "Om" (accent->word "Ọm" :none)))
  (is (= "ước" (accent->word "ươc" :acute)))
  (is (= "tOàn" (accent->word "tOan" :grave)))
  (is (= "giã" (accent->word "gia" :tilde)))
  (is (= "quản" (accent->word "quan" :hook)))
  (is (= "mia" (accent->word "mía" :none)))
  (is (= "nằM" (accent->word "năM" :grave)))
  (is (= "NGuyến" (accent->word "NGuyên" :acute)))
  (is (= "noọng" (accent->word "noong" :dot)))
  (is (= "mửa" (accent->word "mứa" :hook)))
  (is (= "qu" (accent->word "qu" :acute)))
  (is (= "gì" (accent->word "gi" :grave)))
  (is (= "gÌn" (accent->word "gÍn" :grave)))
  (is (= "gIàn" (accent->word "gIán" :grave)))
  (is (= "quẢ" (accent->word "quÁ" :hook)))
  (is (= "quâN" (accent->word "quấN" :none)))
  (is (= "hòa" (accent->word "hoa" :grave)))
  (is (= "giẴc" (accent->word "giẲc" :tilde)))
  )

(deftest test-mark->word
  (is (= "đang" (mark->word "dang" :bar)))
  (is (= "dược" (mark->word "duọc" :horn)))
  (is (= "mưu" (mark->word "muu" :horn)))
  (is (= "bướU" (mark->word "bướU" :horn)))
  (is (= "Đang" (mark->word "Dang" :bar)))
  (is (= "phất" (mark->word "phát" :hat)))
  (is (= "sắc" (mark->word "sác" :breve)))
  (is (= "Duọc" (mark->word "Được" :nomark)))
  )

(deftest test-get-last-word
  (is (= "dang" (get-last-word "ddang")))
  (is (= "đăng" (get-last-word "đồngđăng")))
  (is (= "do" (get-last-word "độclậptựdo")))
  (is (= "ong" (get-last-word "mèong")))
  (is (= "" (get-last-word "")))
  )

(deftest test-grammar-split-word
  (is (= ["d" "dang"] (grammar-split-word "ddang")))
  (is (= ["đồng" "đăng"] (grammar-split-word "đồngđăng")))
  (is (= ["" ""] (grammar-split-word "")))
  )

(deftest test-accent->string
  (is (= "mèoconrahồcá" (accent->string "mèoconrahồca" :acute))))

(deftest test-mark->string
  (is (= "mèoconrahồcấ" (mark->string "mèoconrahồcá" :hat))))

