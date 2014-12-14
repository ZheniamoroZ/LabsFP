(ns crawler.parser-test
  (:require [clojure.test :refer :all]
            [crawler.parser :refer :all]))

(def html-sample "<html><head/><body><a href=\"http://172.0.0.1\"/><body/><html/>")
(def redirect-url "http://google.com/")
(def wrong-url "x")
(def url-404 "http://google.com/bsuir")

(deftest parse-html-test
  (is (= :document (:type (parse-html html-sample)))))

(deftest extract-hrefs-test
  (is (= '("http://172.0.0.1") (extract-hrefs html-sample))))

(deftest test-incorrect-url
  (is (= :error (:status (get-hrefs wrong-url)))))

(deftest test-server-error
  (is (= :error (:status (get-hrefs url-404)))))

(deftest test-redirect
  (is (= :redirect (:status (get-hrefs redirect-url)))))
