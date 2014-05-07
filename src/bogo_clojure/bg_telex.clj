(ns bogo-clojure.bg-telex
  (:gen-class)
  (:require [clojure.string :as string]
            [bogo-clojure.bg-word :refer :all]
            [bogo-clojure.bg-macro :refer :all]))

(def TELEX
  {
   "a" (mark-> "a" :hat ["a" "ă" "â"])
   "w" (mark-> "w"
               :horn ["u" "ư" "uo" "ươ"]
               :breve ["a" "ă" "â"]
               :horn ["o" "ơ" "ô"])
   "o" (mark-> "o" :hat ["o" "ô" "ơ"])
   "e" (mark-> "e" :hat ["e" "ê"])
   "d" (mark-> "d"
               :bar ["d" "đ"])
   "r" (accent-> "r"
                 :hook)
   "x" (accent-> "x"
                 :tilde)
   "j" (accent-> "j"
                 :dot)
   "s" (accent-> "s"
                 :acute)
   "f" (accent-> "f"
                 :grave)
   "z" (accent-> "z"
                 :none)
   })
