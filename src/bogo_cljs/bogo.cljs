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

(defn create-html-range
  "Create a HTML range within a node with given start and end position."
  [node startOffset endOffset]
  (let
    [r (.createRange js/document)]
    (.setStart r node startOffset)
    (.setEnd r node endOffset)
    r))

(defn range-before-selection-start
  [selection]
  (let
    [start-node (.-anchorNode selection)
     offset (.-anchorOffset selection)]
    (create-html-range start-node 0 offset)
    ))

(defn last-word-before
  "Get the last word right before the caret. If there is no word before
  it, return an empty string"
  [selection]
  (let [range-before (range-before-selection-start selection)
        string-before (.toString range-before)]
    (last (.split string-before delimiters))))

(defn process-key-at-caret
  "When user do not select any text, the selection object .toString()
  return an empty string. The new string combined from the text before
  the caret and the entered key"
  [selection key]
  (let [node (.-anchorNode selection)
        last-word (last-word-before selection)
        word-range (let [width (.-length last-word)
                         endOffset (.-anchorOffset selection)]
                     (create-range node
                                   (- endOffset width)
                                   endOffset))]
    (do
      (.deleteContents word-range)
      (.insertNode word-range (.createTextNode js/document
                                               (process_key last-word key))))))

(defn process-key!
  "Process the key event"
  [selection key]
  (if (no-text-selection selection)
    (process-key-at-caret! selection key)
    (replace-selection-with-key selection! key)))


