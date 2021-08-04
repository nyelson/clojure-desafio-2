(ns clojure-desafio-2.credit-card)
(use 'java-time)

(def cliente {:nome "Nyelson", :cpf "22667862023", :email "nyelson.barbosa@nubank.com.br"})
(def cartao {:numero "5410133996442556", :cvv "984", :validade (local-date-time 2022 10 20), :limite 10000})
(def compras [{:data (local-date-time 2021 10 20), :valor 1000,   :estabelecimento "Adidas",        :categoria "Vestuário"},
              {:data (local-date-time 2021 10 21), :valor 250,    :estabelecimento "Adidas",        :categoria "Vestuário"},
              {:data (local-date-time 2021 03 13), :valor 40,     :estabelecimento "Burguer King",  :categoria "Restaurante"},
              {:data (local-date-time 2021 05 29), :valor 300,    :estabelecimento "Nike",          :categoria "Vestuário"}])