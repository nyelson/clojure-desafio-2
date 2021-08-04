(ns clojure-desafio-2.credit-card)
(use 'java-time)

(def cliente {:nome "Nyelson", :cpf "22667862023", :email "nyelson.barbosa@nubank.com.br"})
(def cartao {:numero "5410133996442556", :cvv "984", :validade (local-date-time 2022 10 20), :limite 10000})
(def compras [])