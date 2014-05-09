(ns bogo-clojure.bg-typemode
  (:gen-class)
  (:require [clojure.string :as string]
            [bogo-clojure.bg-word :refer :all]
            [bogo-clojure.bg-action :refer :all]))

(def SIMPLE-TELEX
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

(def TELEX
  {
   "a" (mark-> "a" :hat ["a" "ă" "â"])
   "w" (fn [word]
         (let [case-a (mark->word word :breve)
               case-ou (mark->word word :horn)]
           (cond
            (not= case-a word) case-a
            (not= case-ou word) case-ou
            (not= "ư" (normalize (str (last word)))) (str word "ư")
            :else (str (rollback word :horn) "w"))))
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
   "[" (add-> "[" "ơ")
   "]" (add-> "]" "ư")
   })


(def VNI
  {
   "1" (accent-> "1"
                 :acute)
   "2" (accent-> "2"
                 :grave)
   "3" (accent-> "3"
                 :hook)
   "4" (accent-> "4"
                 :tilde)
   "5" (accent-> "5"
                 :dot)
   "0" (accent-> "0"
                 :none)
   "6" (mark-> "6"
               :hat ["e" "ê"]
               :hat ["o" "ô" "ơ"])
   "7" (mark-> "7"
               :horn ["o" "ô" "ơ"]
               :horn ["u" "ư" "uo" "ươ"])

   "8" (mark-> "8"
               :breve ["a" "ă" "â"])
   "9" (mark-> "9"
               :bar ["d" "đ"])
   })

