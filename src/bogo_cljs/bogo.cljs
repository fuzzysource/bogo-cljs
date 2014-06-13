(ns bogo
  (:require bogo-cljs.core))

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
  (let [last-text-range (create-html-range (.-anchorNode selection)
                                           0
                                           (.-anchorOffset selection))
        old-text (.toString last-text-range)]
    (do
      (.deleteContents last-text-range)
      (.insertNode last-text-range
                   (.createTextNode js/document
                                    (bogo-cljs.core/process-key old-text
                                                                key))))))

(defn editing-areas
  []
  (.querySelectorAll js/document "input,textarea,div[contenteditable=true]"))

(defn special-key-pressed?
  [key-event]
  (or (.-altKey key-event)
      (.-ctrlKey key-event)
      (.-metaKey key-event)))

(defn backspace-press?
  [key-event]
  (= "Backspace" (.key key-event)))


(defn process-key-event!
  "Process the key event"
  [key-event selection akey]
  (if (= "" (.toString selection))
    (do
      (process-key-at-caret selection akey)
      (.preventDefault key-event))
    false))

(defn binding-key-event-handler
  [node]
  (aset node "onkeypress"
        (fn [key-event]
          (if (or (special-key-pressed? key-test)
                  (backspace-press? key-event))
            false ;; Leave the key event handled by default
            ;; if an character is pressed
            (process-key-event! key-event
                               (.getSelection js/document)
                               (.-key key-event))))))




