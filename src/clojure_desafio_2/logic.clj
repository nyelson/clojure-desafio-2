(ns clojure-desafio-2.logic
  (:require [clojure-desafio-2.credit-card :as w.db]))

(use 'java-time)

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
  [compra lista]
  (conj lista compra))

(defn adicionar-cliente
  "Adiciona um cliente na lista de clientes"
  [cliente lista]
  (conj lista cliente))

(defn adicionar-cartao
  "Adiciona um cartão na lista de cartões"
  [cartao lista]
  (conj lista cartao))

(defn seleciona-um-cartao
  "Seleciona UM cartão no conjunto e variedades de cartões, pegando pelo número do cartão - que é único"
  [cartao lista]
  (filterv #(= cartao (:numero %)) lista))

(defn seleciona-um-cliente
  "Seleciona UM cliente no conjunto e variedades de clientes, pegando pelo número do cpf - que é único"
  [cliente lista]
  (filterv #(= cliente (:cpf %)) lista))

(defn lista-compras-por-cliente
  "Retorna lista de compras de acordo com o cliente passado por parâmetro"
  [cliente]
  (get (-> cliente :cartaoRef) :comprasRef))

; Definindo listas de clientes, cartões e de compras - antes da associação
; Adicionando dois clientes na lista de clientes, dados mockados e não validados - por enquanto
; TO-DO: Colocar no formato de schema


;(def lista-de-clientes (adicionar-cliente {:nome "Nyelson Barbosa", :cpf "22667862023", :email "nyelson.barbosa@nubank.com.br"} []))
;(def lista-de-clientes (adicionar-cliente {:nome "Icaro Rios",      :cpf "32257409000", :email "icaro.rios@nubank.com.br"     } lista-de-clientes))

; Adicionando um cartão, sem associação a um cliente em específico e não validado - por enquanto
; TO-DO: Colocar no formato de schema
;(def lista-de-cartoes (adicionar-cartao {:numero "5410133996442556", :cvv "984", :validade (local-date-time 2022 10 20), :limite 10000} []))
;(def lista-de-cartoes (adicionar-cartao {:numero "5459268200059002", :cvv "737", :validade (local-date-time 2023 05 05), :limite 10000} lista-de-cartoes))

; Adicionando sete compras na lista de compras, para ser associada a algum cartão posteriormente, mas sem validações - por enquanto
; TO-DO: Colocar no formato de schema
;(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 10 20), :valor 1000, :estabelecimento "Adidas",       :categoria "Vestuário"   } []))
;(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 10 21), :valor 250,  :estabelecimento "Adidas",       :categoria "Vestuário"   } lista-de-compras))
;(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 03 13), :valor 40,   :estabelecimento "Burguer King", :categoria "Restaurante" } lista-de-compras))
;(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 05 29), :valor 300,  :estabelecimento "Nike",         :categoria "Vestuário"   } lista-de-compras))
;(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 12 30), :valor 50,   :estabelecimento "Cinemark",     :categoria "Cinema"      } lista-de-compras))
;(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 12 27), :valor 5,    :estabelecimento "Padaria",      :categoria "Alimentos"   } lista-de-compras))
;(def lista-de-compras (adicionar-compra {:data (local-date-time 2021 12 25), :valor 70,   :estabelecimento "Boliche",      :categoria "Aleatório"   } lista-de-compras))
;(def cartao (get (seleciona-um-cartao "5410133996442556" lista-de-cartoes) 0))
;(def cartao (assoc-in cartao [:comprasRef] lista-de-compras))
;(def cliente (get (seleciona-um-cliente "22667862023" lista-de-clientes) 0))
;(def cliente (assoc-in cliente [:cartaoRef] cartao))
;(def cartao2 (get (seleciona-um-cartao "5459268200059002" lista-de-cartoes) 0))
;(def cliente2 (get (seleciona-um-cliente "32257409000" lista-de-clientes) 0))
;(def cliente2 (assoc-in cliente2 [:cartaoRef] cartao2))