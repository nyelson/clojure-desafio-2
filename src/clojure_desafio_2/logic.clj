(ns clojure-desafio-2.logic
  (:require [clojure-desafio-2.credit-card :as w.db]))

(use 'java-time)

(println "Início do projeto - Core")
(println "\n")

(def lista-de-compras w.db/compras)
(def lista-de-cartoes w.db/cartao)
(def lista-de-clientes w.db/cliente)

(defn agrupa-categorias
  "Agrupa e retorna uma lista de acordo com a categoria"
  [lista]
  (group-by :categoria lista))

(defn retorna-simbolo-por-categoria
  "Retorna o novo simbolo de acordo com a categoria passada e presente na lista agrupada"
  [lista categoria]
  (get lista categoria))

(defn somatorio-total
  "Retorna o somatório total dos valores passados pela lista no parâmetro"
  [lista]
  (->> lista (map :valor) (reduce +)))

(defn compras-por-estabelecimento-ou-valor
  [coluna filtro compra]
  (= filtro (coluna compra)))

(defn filtrando-compras
  [coluna filtro compras]
  (filter #(compras-por-estabelecimento-ou-valor coluna filtro %) compras))

(defn calcular-mes
  [inicial final compra]
  (println inicial final compra)
  (and (after? final (:data compra))
       (before? inicial (:data compra))))

(defn filtrando-compras-mes
  [inicial final compras]
  (filter #(calcular-mes inicial final %) compras))

(defn adicionar-compra
  [compra lista-de-compras]
  (conj lista-de-compras compra))

;(println "\nAssociando os objetos:" (assoc-in w.db/cliente [:cartaoRef] (assoc-in w.db/cartao [:comprasRef] lista-de-compras)))

;(def lista-de-cartoes (assoc-in lista-de-cartoes [:comprasRef] lista-de-compras))
;(conj lista-de-cartoes lista-de-compras)
;(def lista-de-cartoes)
;(println (assoc-in lista-de-cartoes [:comprasRef] lista-de-compras))

(println "\n\n\natribuição a uma nova lista de uma nova compra")

(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 10 20), :valor 1000, :estabelecimento "Adidas",       :categoria "Vestuário"   } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 10 21), :valor 250,  :estabelecimento "Adidas",       :categoria "Vestuário"   } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 03 13), :valor 40,   :estabelecimento "Burguer King", :categoria "Restaurante" } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 05 29), :valor 300,  :estabelecimento "Nike",         :categoria "Vestuário"   } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 12 30), :valor 50,   :estabelecimento "Cinemark",     :categoria "Cinema"      } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 12 27), :valor 5,    :estabelecimento "Padaria",      :categoria "Alimentos"   } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 12 25), :valor 70,   :estabelecimento "Boliche",      :categoria "Aleatório"   } lista-de-compras))

(def lista-de-cartoes (assoc-in lista-de-cartoes [:comprasRef] lista-de-compras))
(def lista-de-clientes (assoc-in lista-de-clientes [:cartaoRef] lista-de-cartoes))

(println lista-de-cartoes)
(println lista-de-clientes)

;{:nome Nyelson, :cpf 22667862023, :email nyelson.barbosa@nubank.com.br,
;   :cartaoRef {:numero 5410133996442556, :cvv 984, :validade #object[java.time.LocalDateTime 0x133f86bc 2022-10-20T00:00], :limite 10000,
;     :comprasRef [{:data #object[java.time.LocalDateTime 0x21a66db1 2021-10-20T00:00], :valor 1000, :estabelecimento Adidas, :categoria Vestuário}
;                  {:data #object[java.time.LocalDateTime 0x4526d37d 2021-10-21T00:00], :valor 250, :estabelecimento Adidas, :categoria Vestuário}
;                  {:data #object[java.time.LocalDateTime 0x3e01d372 2021-03-13T00:00], :valor 40, :estabelecimento Burguer King, :categoria Restaurante}
;                  {:data #object[java.time.LocalDateTime 0x10cdaee8 2021-05-29T00:00], :valor 300, :estabelecimento Nike, :categoria Vestuário}
;                  {:data #object[java.time.LocalDateTime 0x29482a5 2021-12-30T00:00], :valor 50, :estabelecimento Cinemark, :categoria Cinema}
; {:data #object[java.time.LocalDateTime 0x715a14b1 2021-12-27T00:00], :valor 5, :estabelecimento Padaria, :categoria Alimentos} {:data #object[java.time.LocalDateTime 0x41f0d3ab 2021-12-25T00:00], :valor 70, :estabelecimento Boliche, :categoria Aleatório}]}}

;(defn lista-compras-por-cliente
;  [cpf-cliente lista-de-clientes]
;  (->> lista-de-clientes
;            (:cpf cpf-cliente)))
;
;(println "Usuario:" (lista-compras-por-cliente "0" lista-de-clientes))

;#(= "22667862023" (:cpf %)

;(defn compara-cpf
;  [cpf cliente]
;  (println cliente)
;  (= cpf (:cpf cliente)))
;
;;(println (filter #(compara-cpf "22667862023" %) lista-de-clientes))
;(println (->> lista-de-clientes
;              (filter #(compara-cpf 22667862023 %))))





;acho estranho a funcao já dizer que vai usar esta lista de compras na definição dela, se eu quiser utilizar outra
; não consigo
;(def compras-categorizadas (agrupa-categorias lista-de-compras))
;(def lista-vestuario (retorna-simbolo-por-categoria compras-categorizadas "Vestuário"))
;(def lista-restaurante (retorna-simbolo-por-categoria compras-categorizadas "Restaurante"))
;
;(println lista-de-compras)
;(println "\n\n\n")
;
;(println "O total das compras referente a categoria vestuário é de:" (somatorio-total lista-vestuario))
;(println "\nO total das compras referente a categoria restaurante é de:" (somatorio-total lista-restaurante))
;(println "\nO total das compras é de:" (somatorio-total lista-de-compras))
;
;(println "\nAssociando os objetos:" (assoc-in w.db/cliente [:cartaoRef] (assoc-in w.db/cartao [:comprasRef] lista-de-compras)))
;
;(println "\nRetornando o valor das comprar com o valor de 1000:" (->> lista-de-compras
;                                                                      (filtrando-compras :valor 40)))
;(println "\nRetornando o valor das comprar com o valor de 1000:" (->> lista-de-compras
;                                                                      (filtrando-compras :valor 1000)))
;(println "\nRetornando o valor das comprar com o valor de 1000:" (->> lista-de-compras
;                                                                      (filtrando-compras :valor 300)))
;
;(println "\nRetornando o valor das compra no estabelecimento adidas:" (->> lista-de-compras
;                                                                           (filtrando-compras :estabelecimento "Adidas")))
;(println "\nRetornando o valor das compras no BK:" (->> lista-de-compras
;                                                        (filtrando-compras :estabelecimento "Burguer King")))
;(println "\nRetornando o valor das comprana nike:" (->> lista-de-compras
;                                                        (filtrando-compras :estabelecimento "Nike")))
;
;(println "\nRetornando o valor do extrato no intervalo:" (->> lista-de-compras
;                                                              (filtrando-compras-mes (local-date-time 2021 01 01 9 00) (local-date-time 2021 07 01 9 00)) somatorio-total))
