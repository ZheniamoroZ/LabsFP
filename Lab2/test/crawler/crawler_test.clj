(ns crawler.crawler-test
  (:require [clojure.test :refer :all]
            [crawler.core :refer :all])
  (:use crawler.crawler))

(def test-hrefs '["https://www.google.com/" "https://www.google.com/"])
  
(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 (process-hrefs-sync test-hrefs 1)))))
