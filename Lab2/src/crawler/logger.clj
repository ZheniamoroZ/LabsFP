(ns crawler.logger)
(declare write-file)

(def log (ref []))

(defn clear-log []
  (dosync (ref-set log [])))

(defn printlog [& args]
  (let [line (conj args "\n")]
    (dosync
      (ref-set log 
        (into (deref log) line)))))

(def prefix-pattern "   ")
(defn write-log-prefixed [text depth]
  (let [prefix (apply str (repeat depth prefix-pattern))]
    (println prefix text)
    (printlog prefix text)))

(defn save-log [filename]
  (write-file filename (apply str (deref log))))

(defn write-file [filename text]
  (with-open [w (clojure.java.io/writer  filename :append false)]
    (.write w text)))

