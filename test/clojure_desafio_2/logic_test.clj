(ns clojure-desafio-2.logic-test
  (:require [clojure.test :refer :all]
            [clojure-desafio-2.logic :refer :all]))

(use 'java-time)

(deftest adicionar-compra-test
  (testing "Testando o método de adicionar compra na lista de compras"

    (def lista-de-clientes [])
    (def lista-de-cartoes [])
    (def lista-de-compras [])

    (def lista-de-clientes (adicionar-cliente {:nome "Nyelson Barbosa", :cpf "22667862023", :email "nyelson.barbosa@nubank.com.br"} []))
    (def lista-de-cartoes (adicionar-cartao {:numero "5410133996442556", :cvv "984", :validade (local-date-time 2022 10 20), :limite 10000} []))

    (def cartao (get (seleciona-um-cartao "5410133996442556" lista-de-cartoes) 0))
    (def cartao (assoc-in cartao [:comprasRef] lista-de-compras))
    (def cliente (get (seleciona-um-cliente "22667862023" lista-de-clientes) 0))
    (def cliente (assoc-in cliente [:cartaoRef] cartao))

    (is (= 1
           (count (adicionar-compra {:data            (local-date-time 2021 10 20),
                                     :valor           100,
                                     :estabelecimento "Adidas",
                                     :categoria       "Vestuário"
                                     }
                                    lista-de-compras))
           ))

    (let [compra (peek (adicionar-compra {:data            (local-date-time 2021 10 20),
                                          :valor           1000,
                                          :estabelecimento "Adidas",
                                          :categoria       "Vestuário"
                                          }
                                         lista-de-compras))]
      (is (and (= 1000 (:valor compra)) (= "Adidas" (:estabelecimento compra)) (= "Vestuário" (:categoria compra)))))
    ))

(deftest listar-compras-por-cliente-test
  (testing "Testando método de listar compras por cliente"

    (def lista-de-clientes [])
    (def lista-de-cartoes [])
    (def lista-de-compras [])

    (def lista-de-clientes (adicionar-cliente {:nome "Nyelson Barbosa", :cpf "22667862023", :email "nyelson.barbosa@nubank.com.br"} []))
    (def lista-de-clientes (adicionar-cliente {:nome "Icaro Rios", :cpf "32257409000", :email "icaro.rios@nubank.com.br"} lista-de-clientes))

    ; Adicionando um cartão, sem associação a um cliente em específico e não validado - por enquanto
    (def lista-de-cartoes (adicionar-cartao {:numero "5410133996442556", :cvv "984", :validade (local-date-time 2022 10 20), :limite 10000} []))
    (def lista-de-cartoes (adicionar-cartao {:numero "5459268200059002", :cvv "737", :validade (local-date-time 2023 05 05), :limite 10000} lista-de-cartoes))

    ; Adicionando sete compras na lista de compras, para ser associada a algum cartão posteriormente, mas sem validações - por enquanto
    (def lista-de-compras (adicionar-compra {:data (local-date-time 2021 10 20), :valor 1000, :estabelecimento "Adidas", :categoria "Vestuário"} []))
    (def cartao (get (seleciona-um-cartao "5410133996442556" lista-de-cartoes) 0))
    (def cartao (assoc-in cartao [:comprasRef] lista-de-compras))
    (def cliente (get (seleciona-um-cliente "22667862023" lista-de-clientes) 0))
    (def cliente (assoc-in cliente [:cartaoRef] cartao))
    (def cartao2 (get (seleciona-um-cartao "5459268200059002" lista-de-cartoes) 0))
    (def cliente2 (get (seleciona-um-cliente "32257409000" lista-de-clientes) 0))
    (def cliente2 (assoc-in cliente2 [:cartaoRef] cartao2))

    (is (= [{:data (local-date-time 2021 10 20), :valor 1000, :estabelecimento "Adidas", :categoria "Vestuário"}]
           (lista-compras-por-cliente cliente)))

    (is (= 1
           (count (lista-compras-por-cliente cliente))))

    (is (nil? (lista-compras-por-cliente cliente2)))))

(deftest gastos-por-categoria-test
  (testing "Testando método de listar compras por cliente"

    (def lista-de-clientes [])
    (def lista-de-cartoes [])
    (def lista-de-compras [])

    (is (= (agrupa-categorias lista-de-compras) {}))

    (def lista-de-clientes (adicionar-cliente {:nome "Nyelson Barbosa", :cpf "22667862023", :email "nyelson.barbosa@nubank.com.br"} []))
    (def lista-de-clientes (adicionar-cliente {:nome "Icaro Rios", :cpf "32257409000", :email "icaro.rios@nubank.com.br"} lista-de-clientes))

    ; Adicionando um cartão, sem associação a um cliente em específico e não validado - por enquanto
    ; TO-DO: Colocar no formato de schema
    (def lista-de-cartoes (adicionar-cartao {:numero "5410133996442556", :cvv "984", :validade (local-date-time 2022 10 20), :limite 10000} []))
    (def lista-de-cartoes (adicionar-cartao {:numero "5459268200059002", :cvv "737", :validade (local-date-time 2023 05 05), :limite 10000} lista-de-cartoes))

    ; Adicionando sete compras na lista de compras, para ser associada a algum cartão posteriormente, mas sem validações - por enquanto
    ; TO-DO: Colocar no formato de schema
    (def lista-de-compras (adicionar-compra {:data (local-date-time 2021 10 20), :valor 1000, :estabelecimento "Adidas", :categoria "Vestuário"} []))
    (def lista-de-compras (adicionar-compra {:data (local-date-time 2021 10 21), :valor 250, :estabelecimento "Adidas", :categoria "Vestuário"} lista-de-compras))
    (def lista-de-compras (adicionar-compra {:data (local-date-time 2021 03 13), :valor 40, :estabelecimento "Burguer King", :categoria "Restaurante"} lista-de-compras))

    (def cartao (get (seleciona-um-cartao "5410133996442556" lista-de-cartoes) 0))
    (def cartao (assoc-in cartao [:comprasRef] lista-de-compras))
    (def cliente (get (seleciona-um-cliente "22667862023" lista-de-clientes) 0))
    (def cliente (assoc-in cliente [:cartaoRef] cartao))
    (def cartao2 (get (seleciona-um-cartao "5459268200059002" lista-de-cartoes) 0))
    (def cliente2 (get (seleciona-um-cliente "32257409000" lista-de-clientes) 0))
    (def cliente2 (assoc-in cliente2 [:cartaoRef] cartao2))

    (let [agrupamento {"Vestuário"   [{:data            (local-date-time 2021 10 20),
                                       :valor           1000,
                                       :estabelecimento "Adidas",
                                       :categoria       "Vestuário"}
                                      {:data            (local-date-time 2021 10 21),
                                       :valor           250,
                                       :estabelecimento "Adidas",
                                       :categoria       "Vestuário"}],
                       "Restaurante" [{:data            (local-date-time 2021 03 13),
                                       :valor           40,
                                       :estabelecimento "Burguer King",
                                       :categoria       "Restaurante"}]}]
      (is (= (agrupa-categorias lista-de-compras) agrupamento))
      )

    (def lista-de-compras (adicionar-compra {:data (local-date-time 2021 12 27), :valor 5, :estabelecimento "Padaria", :categoria "Alimentos" } lista-de-compras))

    (let [agrupamento {"Vestuário"   [{:data            (local-date-time 2021 10 20),
                                       :valor           1000,
                                       :estabelecimento "Adidas",
                                       :categoria       "Vestuário"}
                                      {:data            (local-date-time 2021 10 21),
                                       :valor           250,
                                       :estabelecimento "Adidas",
                                       :categoria       "Vestuário"}],
                       "Restaurante" [{:data            (local-date-time 2021 03 13),
                                       :valor           40,
                                       :estabelecimento "Burguer King",
                                       :categoria       "Restaurante"}]
                       "Alimentos" [{:data (local-date-time 2021 12 27),
                                     :valor 5,
                                     :estabelecimento "Padaria",
                                     :categoria "Alimentos"}]
                       }]
      (is (= (agrupa-categorias lista-de-compras) agrupamento))
      )

    ))