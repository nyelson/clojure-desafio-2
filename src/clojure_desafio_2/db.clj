(ns clojure-desafio-2.db
  (:require [datomic.api :as d])
  (:use java-time))


(def schema [
             ; Cliente - {:nome "Nyelson Barbosa", :cpf "22667862023", :email "nyelson.barbosa@nubank.com.br"}
             {
              :db/ident       :cliente/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity
              :db/doc         "O nome do cliente"
              }

             {
              :db/ident       :cliente/nome
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "O nome do cliente"
              }

             {
              :db/ident       :cliente/cpf
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "O CPF do cliente"
              }

             {
              :db/ident       :cliente/email
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "O email do cliente"
              }

             {
              :db/ident       :cliente/cartao
              :db/valueType   :db.type/ref
              :db/cardinality :db.cardinality/many
              :db/doc         "O número do cartão relacionado ao cliente"
              }


             ;===================================================================================================
             ;===================================================================================================
             ; Cartao - {:cartao/numero "5410133996442556", :cartao/cvv "984", :cartao/validade (local-date-time 2022 10 20), :cartao/limite 10000, cartao/transacao}
             {
              :db/ident       :cartao/numero
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity
              :db/doc         "O número do cartão"
              }

             {
              :db/ident       :cartao/cvv
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "O cvv do cartão"
              }

             {
              :db/ident       :cartao/validade
              :db/valueType   :db.type/instant
              :db/cardinality :db.cardinality/one
              :db/doc         "A data de validade do cartão"
              }

             {
              :db/ident       :cartao/limite
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one
              :db/doc         "O limite do cartão"
              }

             {
              :db/ident       :cartao/transacao
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/many
              :db/doc         "A transação relacionada ao cartão"
              }

             ;===================================================================================================
             ;===================================================================================================
             ; Compras - Transações - {:data (local-date-time 2021 10 20), :valor 1000, :estabelecimento "Adidas",       :categoria "Vestuário"   }
             {
              :db/ident       :transacao/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity
              :db/doc         "O id da transação"
              }

             {
              :db/ident       :transacao/data
              :db/valueType   :db.type/instant
              :db/cardinality :db.cardinality/one
              :db/doc         "A data da transação"
              }

             {
              :db/ident       :transacao/valor
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one
              :db/doc         "O valor da transação"
              }

             {
              :db/ident       :transacao/estabelecimento
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "O estabelecimento do cartão"
              }

             {
              :db/ident       :transacao/categoria
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "A categoria do cartão"
              }
             ])


;Você precisa escrever uma função clojure que salve os dados do cartão de crédito e de seu cliente no banco de dados Datomic.
(def db-uri "datomic:dev://localhost:4334/nubank")
;(d/delete-database db-uri)
(d/create-database db-uri)
(def conn (d/connect db-uri))
(pprint conn)

(d/transact conn schema)

(defn cria-schema! [conn]
  (d/transact conn schema))

(cria-schema! conn)

(defn adiciona-clientes! [conn clientes]
  (d/transact conn clientes))

(defn todos-os-clientes [db]
  (d/q '[:find (pull ?cliente [* {:cliente/cartao [*]}])
         :where [?cliente :cliente/nome]]
       db))

(defn uuid [] (java.util.UUID/randomUUID))

(defn novo-cliente
  ; gerar ids dinamicamente
  ([nome cpf email]
   (novo-cliente (uuid) nome cpf email))

  ([uuid nome cpf email]
   {:cliente/id     uuid
    :cliente/nome   nome
    :cliente/cpf    cpf
    :cliente/email  email})

  ([uuid nome cpf email cartao]
   {:cliente/id     uuid
    :cliente/nome   nome
    :cliente/cpf    cpf
    :cliente/email  email
    :cliente/cartao cartao}))

;([nome cpf email]
; (novo-cliente (uuid) nome cpf email))
;
;; usar ids que ja foram criados antes
;([uuid nome cpf email]
; {:cliente/id    uuid
;  :cliente/nome  nome
;  :cliente/cpf   cpf
;  :cliente/email email}

(defn novo-cartao
  ;; gerar ids dinamicamente
  ;([numero cvv validade limite transacao]
  ; (novo-cartao numero cvv validade limite transacao))

  ; usar ids que ja foram criados antes
  ([numero cvv validade limite transacao]
   {:cartao/numero    numero
    :cartao/cvv       cvv
    :cartao/validade  validade
    :cartao/limite    limite
    :cartao/transacao transacao})

  ;([numero cvv validade limite]
  ; (novo-cartao numero cvv validade limite))

  ; usar ids que ja foram criados antes
  ([numero cvv validade limite]
   {:cartao/numero   numero
    :cartao/cvv      cvv
    :cartao/validade validade
    :cartao/limite   limite}))

;{:data (local-date-time 2021 10 20), :valor 1000, :estabelecimento "Adidas",       :categoria "Vestuário"   }
(defn nova-transacao
  ([data valor estabelecimento categoria]
   {:transacao/data            data
    :transacao/valor           valor
    :transacao/estabelecimento estabelecimento
    :transacao/categoria       categoria}))


(def cliente      (novo-cliente (uuid), "Nyelson Barbosa", "22667862023", "nyelson.barbosa@nubank.com.br"))
(def cartao       (novo-cartao "5410133996442556", "984", (sql-date (local-date-time 2022 10 20)), 10000.00M))
(def transacao    (nova-transacao (sql-date (local-date-time 2021 03 12)), 100.00M, "Adidas", "Vestuário"))

(pprint (adiciona-clientes! conn [cliente, cartao, transacao]))
(pprint @(d/transact conn [cliente]))

(pprint (todos-os-clientes (d/db conn)))