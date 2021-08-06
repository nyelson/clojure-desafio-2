(ns clojure-desafio-2.prints
  (:require [clojure-desafio-2.logic :refer :all]))

(use 'java-time)

(println "Início do projeto\n")

;(def lista-de-clientes [])
;(def lista-de-cartoes [])
;(def lista-de-compras [])

(def lista-de-clientes (adicionar-cliente {:nome "Nyelson Barbosa", :cpf "22667862023", :email "nyelson.barbosa@nubank.com.br"} []))
(def lista-de-clientes (adicionar-cliente {:nome "Icaro Rios",      :cpf "32257409000", :email "icaro.rios@nubank.com.br"     } lista-de-clientes))

;Adicionando um cartão, sem associação a um cliente em específico e não validado - por enquanto
;TO-DO: Colocar no formato de schema
(def lista-de-cartoes (adicionar-cartao {:numero "5410133996442556", :cvv "984", :validade (local-date-time 2022 10 20), :limite 10000} []))
(def lista-de-cartoes (adicionar-cartao {:numero "5459268200059002", :cvv "737", :validade (local-date-time 2023 05 05), :limite 10000} lista-de-cartoes))

;Adicionando sete compras na lista de compras, para ser associada a algum cartão posteriormente, mas sem validações - por enquanto
;TO-DO: Colocar no formato de schema
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 10 20), :valor 1000, :estabelecimento "Adidas",       :categoria "Vestuário"   } []))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 10 21), :valor 250,  :estabelecimento "Adidas",       :categoria "Vestuário"   } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 03 13), :valor 40,   :estabelecimento "Burguer King", :categoria "Restaurante" } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 05 29), :valor 300,  :estabelecimento "Nike",         :categoria "Vestuário"   } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 12 30), :valor 50,   :estabelecimento "Cinemark",     :categoria "Cinema"      } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 12 27), :valor 5,    :estabelecimento "Padaria",      :categoria "Alimentos"   } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 12 25), :valor 70,   :estabelecimento "Boliche",      :categoria "Aleatório"   } lista-de-compras))
(def cartao (get (seleciona-um-cartao "5410133996442556" lista-de-cartoes) 0))
(def cartao (assoc-in cartao [:comprasRef] lista-de-compras))
(def cliente (get (seleciona-um-cliente "22667862023" lista-de-clientes) 0))
(def cliente (assoc-in cliente [:cartaoRef] cartao))
(def cartao2 (get (seleciona-um-cartao "5459268200059002" lista-de-cartoes) 0))
(def cliente2 (get (seleciona-um-cliente "32257409000" lista-de-clientes) 0))
(def cliente2 (assoc-in cliente2 [:cartaoRef] cartao2))

(println "Lista de clientes:" lista-de-clientes)
(println "Lista de cartões:" lista-de-cartoes)
(println "Lista de compras:" lista-de-compras)
(println "\n\n")

(println "Peguei o primeiro cartão:" (seleciona-um-cartao "5410133996442556" lista-de-cartoes))

; Foi utilizado o get cartão 0, para pegar o mapa referente ao cartão filtrado
(println "Associando as compras no primeiro cartão pego" (assoc-in cartao [:comprasRef] lista-de-compras))

(println "\n\nO primeiro cliente pesquisado foi:" (seleciona-um-cliente "22667862023" lista-de-clientes))

; Foi utilizada a mesma lógica do cartão para retornar o único cliente no vetor de cliente
(println "Associando o primeiro cartão ao primeiro cliente" (assoc-in cliente [:cartaoRef] cartao))

; Instanciando o segundo cartão e associando ao segundo cliente, sem adicionar nenhuma compra
(println "\nPeguei o segundo cartão:" (seleciona-um-cartao "5459268200059002" lista-de-cartoes))

(println "\n\nO segundo cliente pesquisado foi:" (seleciona-um-cliente "32257409000" lista-de-clientes))

(println "Associando o segundo cartão ao segundo cliente" (assoc-in cliente2 [:cartaoRef] cartao2))

(println "Listando compras do primeiro cliente:"  (lista-compras-por-cliente cliente))
(println "Listando compras do segundo cliente:"   (lista-compras-por-cliente cliente2))

(println "\nO total das compras referente a categoria vestuário é de:"  (somatorio-total (retorna-simbolo-por-categoria (agrupa-categorias lista-de-compras) "Vestuário")))
(println "O total das compras referente a categoria restaurante é de:"  (somatorio-total (retorna-simbolo-por-categoria (agrupa-categorias lista-de-compras) "Restaurante")))
(println "O total das compras é de:"                                    (somatorio-total lista-de-compras))

(println "\nRetornando o valor das comprar com o valor de 40:"  (->> lista-de-compras
                                                                     (filtrando-compras :valor 40)))

(println "Retornando o valor das comprar com o valor de 1000:"  (->> lista-de-compras
                                                                     (filtrando-compras :valor 1000)))

(println "Retornando o valor das comprar com o valor de 300:"   (->> lista-de-compras
                                                                     (filtrando-compras :valor 300)))

(println "\nRetornando o valor das compra na Adidas:"           (->> lista-de-compras
                                                                     (filtrando-compras :estabelecimento "Adidas")))

(println "Retornando o valor das compras no BK:"                (->> lista-de-compras
                                                                     (filtrando-compras :estabelecimento "Burguer King")))

(println "Retornando o valor das compra na Nike:"               (->> lista-de-compras
                                                                     (filtrando-compras :estabelecimento "Nike")))

(println "\nRetornando o valor do extrato no intervalo:"        (->> lista-de-compras
                                                                     (filtrando-compras-mes (local-date-time 2021 01 01 9 00) (local-date-time 2021 07 01 9 00)) somatorio-total))
