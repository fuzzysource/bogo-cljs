(ns bogo-clojure.bg-websocket
  (:gen-class)
  (:require [bogo-clojure.core :as bogo]
            [bogo-clojure.bg-typemode :refer :all]
            [clojure.string :as string])
  (:use org.httpkit.server))

(def typemode (atom TELEX))

(defn process!
  [channel data]
  (let [[old-string key] (string/split-lines data)
        new-string (bogo/process-key old-string key @typemode)]
    (println old-string key new-string)
    (send! channel new-string)
    ))

(defn handler [req]
  (with-channel req channel
    (on-close channel (fn [status]
                        (println "channel closed")))
    (if (websocket? channel)
      (println "WebSocket channel")
      (println "HTTP channel"))
    (on-receive channel (fn [data]
                          (process! channel data)))))

(defn -main
  [& args]
  (if (not (empty? args))
    (reset! typemode (eval (symbol "bogo-clojure.bg-typemode" (string/upper-case (string/join args)))))
    true)
  (run-server handler {:port 8080}))
