(ns clojure-desafio-2.logic-test
  (:require [clojure.test :refer :all]
            [clojure-desafio-2.logic :refer :all]))

(use 'java-time)

(deftest adicionar-compra-test
  (testing "Testando o método de adicionar compra na lista de compras"
    (is (= 1
           (count (adicionar-compra {:data (local-date-time 2021 10 20),
                                     :valor 100,
                                     :estabelecimento "Adidas",
                                     :categoria "Vestuário"
                                     }
                                     lista-de-compras))
           ))

    (let [compra (peek (adicionar-compra {:data           (local-date-time 2021 10 20),
                                          :valor           1000,
                                          :estabelecimento "Adidas",
                                          :categoria       "Vestuário"
                                         }
                                        lista-de-compras))]
      (is (and (= 1000 (:valor compra)) (= "Adidas" (:estabelecimento compra)) (= "Vestuário" (:categoria compra)))))
    ))

(deftest listar-compras-por-cliente-test
  (testing "Testando método de listar compras por cliente"
    (is (= [{:data (local-date-time 2021 10 20), :valor 1000, :estabelecimento "Adidas", :categoria "Vestuário" }]
           (lista-compras-por-cliente cliente)))

    (is (= 1
           (count (lista-compras-por-cliente cliente))))

    (is (nil? (lista-compras-por-cliente cliente2)))))

(deftest gastos-por-categoria-test
  (testing "Testando método de listar compras por cliente"

    ( let [agrupamento {"Vestuário" [{:data (local-date-time 2021 10 20),
                                     :valor 1000,
                                     :estabelecimento "Adidas",
                                     :categoria "Vestuário"}
                                    {:data (local-date-time 2021 10 21),
                                     :valor 250,
                                     :estabelecimento "Adidas",
                                     :categoria "Vestuário"}],
                       "Restaurante" [{:data (local-date-time 2021 03 13),
                                       :valor 40,
                                       :estabelecimento "Burguer King",
                                       :categoria "Restaurante"}]}]
      (is ( = (agrupa-categorias lista-de-compras) agrupamento))
    )

    ))