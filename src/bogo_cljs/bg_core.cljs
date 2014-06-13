(ns bogo-cljs.core
  (:require [clojure.string :as string]
            [bogo-cljs.bg-word
             :refer [grammar-split-word
                     refine-word]]
            [bogo-cljs.bg-typemode
             :refer [TELEX]]
            bogo-cljs.bg-action))

(defn get-action
  "Action is a function taking in current string, make transform,
  return the new string"
  [key typemode]
  (if (contains? typemode key)
    (get typemode key)
    "addchar"))

(defn process-key*

  "When typing a character to rollback the transformation, the new
  character added should be in the same case as the entered keys.
  For example: ư + W -> uW rather than uw"

  [key old-string new-string]
  (let [old-length (.-length old-string)
        new-length (.-length new-string)]
    (if (and (= (dec old-length) new-length)
             (= (.toLowerCase key) (aget new-string (dec new-length))))
      (+ (.substring new-string 0 (.-length old-string)) key)
      new-string)))

(defn ^:export process-key
  "Return new string composed by the current string and the key pressed."
  ([astring key]
   (process-key astring key TELEX))
  ([astring key typemode]
   (let [[first-word last-word] (grammar-split-word astring)
         lkey (.toLowerCase key)
         action (get-action lkey typemode)]
     (+ first-word
        (refine-word (process-key* key
                                   last-word
                                   (if (fn? action)
                                     (action last-word)
                                     (+ last-word key))))))))

(defn ^:export process-sequence
  "Return the new string composed by typing a sequence of keys."
  ([sequence]
   (process-sequence sequence TELEX))
  ([sequence typemode]
   (reduce (fn [word key] (process-key word key typemode))
           ""
           sequence)))
