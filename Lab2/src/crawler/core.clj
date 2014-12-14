(ns crawler.core
  (:gen-class)
  (:use crawler.logger crawler.crawler)
  (:require [clojure.java.io :as io]))

(declare readlines-from perform)

(def output-filename "output.txt")

(defn -main
  [urls-filename max-depth]
  (try
    (let [urls (readlines-from urls-filename) max-d (Integer. max-depth)]
      (perform urls max-d))
    (catch Exception e
      (println e))))

(defn perform [urls max-depth]  
  (clear-log)
  (doseq [url urls]  
    (crawl url max-depth))
  (save-log output-filename))

(defn readlines-from [filename]
  (with-open [rdr (io/reader filename)]
    (doall (line-seq rdr))))
