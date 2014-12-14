(ns clusterization.core-test
  (:require [clojure.test :refer :all]
            [clusterization.core :refer :all])
  (import java.lang.Math))

(deftest euclid-distance-test
  (are [p1 p2] (= p1 p2)
       (Math/sqrt 8) (euclid-distance [1 2] [3 4])
       (Math/sqrt 18) (euclid-distance [2 3 9] [3 7 8])
       2.0 (euclid-distance [4] [2])
       5.0 (euclid-distance [-1 2 -3] [-5 2 0])
       2.0 (euclid-distance [2.0 1.0] [2 3])))

(deftest hamming-distance-test
  (are [p1 p2] (= p1 p2)
       2 (hamming-distance [1 2] [3 4])
       1 (hamming-distance [1 5] [5 5])
       0 (hamming-distance [10 12] [10 12])
       2 (hamming-distance [2 15 6] [1 15 8])
       0 (hamming-distance [6.0] [6.0])
       1 (hamming-distance [-1] [1])))

(deftest get-point-potential-test
  (let [p [2 4]
        points [[2 4] [1 2] [3 3]]
        alpha 1.5]
    (is (= (+ 1 (Math/exp (- (* alpha (Math/sqrt 5)))) (Math/exp (- (* alpha (Math/sqrt 2)))))
         (first (get-point-potential p points euclid-distance alpha))))))

(deftest update-potentials-test
  (let [potentials [[5 [5 4]] [3 [2 2]] [2 [1 4]]]
        core [5 [5 4]]
        beta 1.5
        new-potentials [[ 0.0 [5 4]]
                        [(- 3 (* (first core) (Math/exp (* (- beta) (euclid-distance (last core) [2 2]))))) [2 2]]
                        [(- 2 (* (first core) (Math/exp (* (- beta) (euclid-distance (last core) [1 4]))))) [1 4]]]]
    (is (= new-potentials (update-potentials potentials core beta euclid-distance)))))

