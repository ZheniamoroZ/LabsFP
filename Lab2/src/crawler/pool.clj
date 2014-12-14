(ns crawler.pool)

(def ^:dynamic futures [])

(defn wait-for-all-done []
  "Wait for all stream to finish"
  (while (not (every? #(future-done? %) futures)) ()))

(defn async [action]
  "Perform in different stream"
  (def futures 
      (conj futures 
        (future (action)))))

(defn sync-wise [action]
  "Prepare threads and wait till they're finished"
  (def futures [])
  (action)
  (wait-for-all-done))
