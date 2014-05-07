(ns bogo-clojure.bg-websocket
  (:gen-class)
  (:require [bogo-clojure.core :as bogo]
            [clojure.string :as string])
  (:use org.httpkit.server))


(defn process!
  [channel data]
  (let [[old-string key] (string/split-lines data)
        new-string (bogo/process-key old-string key)]
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
  (run-server handler {:port 8080}))
