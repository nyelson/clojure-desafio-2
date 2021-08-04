(ns clojure-desafio-2.logic-test
  (:require [clojure.test :refer :all]
            [clojure-desafio-2.logic :refer :all]))

(use 'java-time)

(deftest adicionar-compra-test
  (testing "Testando o método de adicionar compra na lista de compras"
    [lista-de-compras]
    (is (= 0 (count lista-de-compras)))
    (is (= 1 (count (adicionar-compra {:data (local-date-time 2021 10 20), :valor 1000, :estabelecimento "Adidas", :categoria "Vestuário"} lista-de-compras))))))