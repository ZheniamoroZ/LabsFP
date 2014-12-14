(ns crawler.crawler
  (:use crawler.parser)
  (:use crawler.logger)
  (:use crawler.pool))
(declare crawl crawl-iter)

(def ^:dynamic max-depth 0)

(defn process-hrefs [hrefs depth]
  "Parallelization of url processing"
  (doseq [suburl hrefs]       
    (async #(crawl-iter suburl depth))))

(defn process-success [depth url hrefs]  
  "Add current url info to results"  
  (let [rec (apply str [url " " (count hrefs) " links"])]
    (write-log-prefixed rec depth))  
  (when (< depth max-depth)   
    (process-hrefs hrefs (inc depth))))

(defn process-redirect [depth url]  
  "Log redirect and add final url info to results"
  (let [rec (apply str [url " Redirect"])]
    (write-log-prefixed rec depth)))

(defn process-error [depth url http-resp]
  "Log error"
  (let [rec (apply str [url " Bad"])]
    (write-log-prefixed rec depth)))

(defn crawl-iter [url cur-depth]
  "Process url"
  (def parsing-result (get-hrefs url))  
  (case (:status parsing-result)
    :ok (let [hrefs (:hrefs parsing-result)]
      (process-success cur-depth url hrefs))
    :redirect (let [hrefs (:hrefs parsing-result)]
      (process-redirect cur-depth url)                
      (process-success cur-depth (:final-url parsing-result) hrefs))
    :error (process-error cur-depth url parsing-result)))

(defn crawl [url max-d]
  "Use url as a start for collecting hrefs. max-d - depth of search"
  (def max-depth max-d)
  (sync-wise
    #(crawl-iter url 1)))
