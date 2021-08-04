(ns clojure-desafio-2.logic
  (:require [clojure-desafio-2.credit-card :as w.db]))

(use 'java-time)

(println "Início do projeto - Core")
(println "\n")

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

;acho estranho a funcao já dizer que vai usar esta lista de compras na definição dela, se eu quiser utilizar outra
; não consigo
(def compras-categorizadas  (agrupa-categorias w.db/compras))

(def lista-vestuario        (retorna-simbolo-por-categoria compras-categorizadas "Vestuário"  ))
(def lista-restaurante      (retorna-simbolo-por-categoria compras-categorizadas "Restaurante"))

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

(println "O total das compras referente a categoria vestuário é de:"        (somatorio-total lista-vestuario))
(println "\nO total das compras referente a categoria restaurante é de:"    (somatorio-total lista-restaurante))
(println "\nO total das compras referente fatura é de:"                     (somatorio-total w.db/compras))

(println "\nAssociando os objetos:" (assoc-in w.db/cliente [:cartaoRef] (assoc-in w.db/cartao [:comprasRef] w.db/compras)))

(println "\nRetornando o valor das comprar com o valor de 1000:" (->>  w.db/compras
                                                                      (filtrando-compras :valor 40)))
(println "\nRetornando o valor das comprar com o valor de 1000:" (->>  w.db/compras
                                                                      (filtrando-compras :valor 1000)))
(println "\nRetornando o valor das comprar com o valor de 1000:" (->>  w.db/compras
                                                                      (filtrando-compras :valor 300)))

(println "\nRetornando o valor das compra no estabelecimento adidas:" (->>  w.db/compras
                                                                      (filtrando-compras :estabelecimento "Adidas")))
(println "\nRetornando o valor das compras no BK:"                    (->>  w.db/compras
                                                                      (filtrando-compras :estabelecimento "Burguer King")))
(println "\nRetornando o valor das comprana nike:"                    (->>  w.db/compras
                                                                      (filtrando-compras :estabelecimento "Nike")))

(println "\nRetornando o valor do extrato no intervalo:" (->>  w.db/compras
              ( filtrando-compras-mes (local-date-time 2021 01 01 9 00) (local-date-time 2021 07 01 9 00)) somatorio-total))
