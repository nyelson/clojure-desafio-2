(ns clojure-desafio-2.db-schema)

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
              :db/unique      :db.unique/identity
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


;===============================================================================================================================================================
;===============================================================================================================================================================
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
              :db/valueType   :db.type/ref
              :db/cardinality :db.cardinality/many
              :db/doc         "A transação relacionada ao cartão"
              }

;===============================================================================================================================================================
;===============================================================================================================================================================
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