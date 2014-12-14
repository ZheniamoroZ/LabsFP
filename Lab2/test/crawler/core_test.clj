(ns crawler.core-test
  (:require [clojure.test :refer :all]
            [crawler.core :refer :all]))

(def root-url "https://www.google.com/")

(deftest main-test
  (-main root-url 2))
