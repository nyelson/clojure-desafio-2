(ns clojure-desafio-2.db
  (:require [datomic.api :as d])
  (:use [clojure-desafio-2.db-schema :as s])
  (:use [clojure-desafio-2.logic :as l]))

(use '[java-time :exclude [contains? iterate range min format zero? max]])
(use '[clojure.pprint :exclude [formatter]])

; Função responsável por fazer um transact para essa conexão, associando o schema definido no projeto db_schema.clj
(defn cria-schema! [conn]
  (d/transact conn s/schema))


(defn todos-os-clientes [db]
  (d/q '[:find (pull ?cliente [*])
         :where [?cliente :cliente/id]] db))


(defn todos-os-cartoes [db]
  (d/q '[:find (pull ?cartao [*])
         :where [?cartao :cartao/numero]] db))


(defn todos-as-transacoes [db]
  (d/q '[:find (pull ?transacao [*])
         :where [?transacao :transacao/valor]] db))


(defn um-cliente
  "Uma forma de selecionar o cliente pelo CPF do cliente"
  [db cliente-cpf]
  (d/pull db '[*] [:cliente/cpf cliente-cpf]))


(defn um-cartao
  "Uma forma de seleciona o cartao pelo número do cartão"
  [db numero-cartao]
  (d/pull db '[*] [:cartao/numero numero-cartao]))


(defn uma-transacao
  "Uma forma de seleciona a transacao pelo id da transação"
  [db id-transacao]
  (d/pull db '[*] [:transacao/id id-transacao]))


(defn adiciona-clientes!
  [conn clientes]
  (d/transact conn clientes))


(defn adiciona-cartoes!
  ([conn cartoes]
   (d/transact conn cartoes))

  ([conn cartoes id-cliente]
   (let [resultado @(d/transact conn cartoes)
         cartao-id (first (vals (:tempids resultado)))]
     (d/transact conn [[:db/add id-cliente :cliente/cartao cartao-id]]))))


(defn adiciona-transacoes!
  ([conn transacoes]
   (d/transact conn transacoes))

  ([conn transacoes id-cartao]
   (let [resultado @(d/transact conn transacoes)
         transacao-id (first (vals (:tempids resultado)))]
     (d/transact conn [[:db/add id-cartao :cartao/transacao transacao-id]]))))










;Define uma URI para o banco de dados (lembrando que ele é único e utiliza apenas uma grande tabela)
(def db-uri "datomic:dev://localhost:4334/nubank")

; Cria a database usando como referencia a URI informada por parâmetro e definida acima
(d/create-database db-uri)

; Define um simbolo que é referente a conexão da base de dados (as transações - d/transact - precisam dessa conexão para ocorrer)
(def conn (d/connect db-uri))

; Chama a função para criar o schema
(cria-schema! conn)

;Adiciona clientes
(def cliente (l/adicionar-cliente (uuid), "Nyelson Barbosa", "22667862023", "nyelson.barbosa@nubank.com.br"))
(def cartao (l/adicionar-cartao "5410133996442556", "984", (sql-date (local-date-time 2022 10 20)), 10000.00M))
(def transacao (l/adicionar-compra (sql-date (local-date-time 2021 03 12)), 100.00M, "Adidas", "Vestuário"))

(adiciona-clientes! conn [cliente])
(adiciona-cartoes! conn [cartao])
(adiciona-transacoes! conn [transacao])

(pprint (todos-os-clientes (d/db conn)))
(pprint (todos-os-cartoes (d/db conn)))
(pprint (todos-as-transacoes (d/db conn)))

; Outra forma de adicionar cartao já vinculando a um cliente
;(pprint (adiciona-cartao! conn (-> (um-cliente (d/db conn) (-> cliente :cliente/cpf))
;                                   :db/id) [cartao]))

;Outra forma de adiciona transação 'compra' já vinculando a um cartão
;(pprint (adiciona-transacao! conn (-> (um-cartao (d/db conn) (-> cartao :cartao/numero))
;                                      :db/id) [transacao]))

; Deleta o database usando como referencia a URI informada por parâmetro e definida inicialmente
;(d/delete-database db-uri)