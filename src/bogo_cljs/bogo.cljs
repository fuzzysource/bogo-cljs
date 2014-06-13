(ns bogo
  (:require [bogo-cljs.core :refer [process_key]]))

(def delimiters #"[,./?;:!\s]")

(defn editareas
  "Select all editable areas in the web page"
  []
  (.querySelectorAll js/document "input, textarea, *[contenteditable=\"true\"]"))

(defn selection
  "Get the dom that has the caret in"
  []
  (.. js/document getSelection))

(defn range-before-selection-start
  [selection]
  (let
    [r (.createRange js/document)
     start-node (.-anchorNode selection)
     offset (.-anchorOffset selection)]
    (.setStart r start-node 0)
    (.setEnd r start-node offset)
    r))

(defn last-word-before
  "Get the last word right before the caret. If there is no word before
  it, return an empty string"
  [selection]
  (let [range-before (range-before-selection-start selection)
        string-before (.toString range-before)]
    (last (.split string-before delimiters))))

(defn process-key-at-caret!
  "When user do not select any text, the selection object .toString()
  return an empty string. The new string combined from the text before
  the caret and the entered key"
  [selection key]
  (let [word-before (last-word-before selection)
        word-length (.-length word-before)
        new-word (process_key word-before key)]
    (do
      (delete-word-before! selection word-before)
      (insert-new-text! selection new-word))))

(defn process-key!
  "Process the key event"
  [selection key]
  (if (no-text-selection selection)
    (process-key-at-caret! selection key)
    (replace-selection-with-key selection! key)))


