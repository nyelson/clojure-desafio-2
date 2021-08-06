(ns clojure-desafio-2.prints
  (:require [clojure-desafio-2.logic :refer :all]))

(println "Início do projeto - Core\n")

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
