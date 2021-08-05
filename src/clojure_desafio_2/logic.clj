(ns clojure-desafio-2.logic
  (:require [clojure-desafio-2.credit-card :as w.db]))

(use 'java-time)

(println "Início do projeto - Core\n")

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
  "Adiciona uma compra na lista de compras"
  [compra lista-de-compras]
  (conj lista-de-compras compra))

(defn adicionar-cliente
  "Adiciona um cliente na lista de clientes"
  [cliente lista-de-clientes]
  (conj lista-de-clientes cliente))

(defn adicionar-cartao
  "Adiciona um cartão na lista de cartões"
  [cartao lista-de-cartoes]
  (conj lista-de-cartoes cartao))

(defn seleciona-um-cartao
  "Seleciona UM cartão no conjunto e variedades de cartões, pegando pelo número do cartão - que é único"
  [cartao lista-de-cartoes]
  (filterv #(= cartao (:numero %)) lista-de-cartoes))

(defn seleciona-um-cliente
  "Seleciona UM cliente no conjunto e variedades de clientes, pegando pelo número do cpf - que é único"
  [cliente lista-de-clientes]
  (filterv #(= cliente (:cpf %)) lista-de-clientes))

(defn lista-compras-por-cliente
  "Retorna lista de compras de acordo com o cliente passado por parâmetro"
  [cliente]
  (get (-> cliente :cartaoRef) :comprasRef))

(println "\nDefinindo listas de clientes, cartões e de compras - antes da associação")
; Adicionando dois clientes na lista de clientes, dados mockados e não validados - por enquanto
; TO-DO: Colocar no formato de schema
(def lista-de-clientes (adicionar-cliente {:nome "Nyelson Barbosa", :cpf "22667862023", :email "nyelson.barbosa@nubank.com.br"} lista-de-clientes))
(def lista-de-clientes (adicionar-cliente {:nome "Icaro Rios",      :cpf "32257409000", :email "icaro.rios@nubank.com.br"     } lista-de-clientes))

; Adicionando um cartão, sem associação a um cliente em específico e não validado - por enquanto
; TO-DO: Colocar no formato de schema
(def lista-de-cartoes (adicionar-cartao {:numero "5410133996442556", :cvv "984", :validade (local-date-time 2022 10 20), :limite 10000} lista-de-cartoes))

; Adicionando sete compras na lista de compras, para ser associada a algum cartão posteriormente, mas sem validações - por enquanto
; TO-DO: Colocar no formato de schema
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 10 20), :valor 1000, :estabelecimento "Adidas",       :categoria "Vestuário"   } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 10 21), :valor 250,  :estabelecimento "Adidas",       :categoria "Vestuário"   } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 03 13), :valor 40,   :estabelecimento "Burguer King", :categoria "Restaurante" } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 05 29), :valor 300,  :estabelecimento "Nike",         :categoria "Vestuário"   } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 12 30), :valor 50,   :estabelecimento "Cinemark",     :categoria "Cinema"      } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 12 27), :valor 5,    :estabelecimento "Padaria",      :categoria "Alimentos"   } lista-de-compras))
(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 12 25), :valor 70,   :estabelecimento "Boliche",      :categoria "Aleatório"   } lista-de-compras))

(println "Lista de clientes:" lista-de-clientes)
(println "Lista de cartões:" lista-de-cartoes)
(println "Lista de compras:" lista-de-compras)
(println "\n\n")

(println "Peguei o cartão:" (seleciona-um-cartao "5410133996442556" lista-de-cartoes))
(def cartao (get (seleciona-um-cartao "5410133996442556" lista-de-cartoes) 0))

; Foi utilizado o get cartão 0, para pegar o mapa referente ao cartão filtrado
(println "Associando as compras no cartão pego" (assoc-in cartao [:comprasRef] lista-de-compras))
(def cartao (assoc-in cartao [:comprasRef] lista-de-compras))


(println "\n\nO cliente pesquisado foi:" (seleciona-um-cliente "22667862023" lista-de-clientes))
(def cliente (get (seleciona-um-cliente "22667862023" lista-de-clientes) 0))

; Foi utilizada a mesma lógica do cartão para retornar o único cliente no vetor de cliente
(println "Associando o cartão ao cliente" (assoc-in cliente [:cartaoRef] cartao))
(def cliente (assoc-in cliente [:cartaoRef] cartao))

(println "Listando compras do cliente:" (lista-compras-por-cliente  cliente))

(println "\nO total das compras referente a categoria vestuário é de:"  (somatorio-total (retorna-simbolo-por-categoria (agrupa-categorias lista-de-compras) "Vestuário")))
(println "O total das compras referente a categoria restaurante é de:"  (somatorio-total (retorna-simbolo-por-categoria (agrupa-categorias lista-de-compras) "Restaurante")))
(println "O total das compras é de:"                                    (somatorio-total lista-de-compras))


(println "\nRetornando o valor das comprar com o valor de 1000:" (->> lista-de-compras
                                                                      (filtrando-compras :valor 40)))

(println "Retornando o valor das comprar com o valor de 1000:"   (->> lista-de-compras
                                                                      (filtrando-compras :valor 1000)))

(println "Retornando o valor das comprar com o valor de 1000:"   (->> lista-de-compras
                                                                      (filtrando-compras :valor 300)))

(println "\nRetornando o valor das compra na Adidas:"            (->> lista-de-compras
                                                                      (filtrando-compras :estabelecimento "Adidas")))

(println "Retornando o valor das compras no BK:"                 (->> lista-de-compras
                                                                      (filtrando-compras :estabelecimento "Burguer King")))

(println "Retornando o valor das compra na Nike:"                (->> lista-de-compras
                                                                      (filtrando-compras :estabelecimento "Nike")))

(println "\nRetornando o valor do extrato no intervalo:"         (->> lista-de-compras
                                                                      (filtrando-compras-mes (local-date-time 2021 01 01 9 00) (local-date-time 2021 07 01 9 00)) somatorio-total))
