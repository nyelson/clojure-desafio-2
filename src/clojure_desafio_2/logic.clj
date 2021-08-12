(ns clojure-desafio-2.logic)

(use '[java-time :exclude [contains? iterate range min format zero? max]])
(use '[clojure.pprint :exclude [formatter]])

(defn uuid [] (java.util.UUID/randomUUID))

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
  ([compra lista]
   (conj lista compra))

  ([data valor estabelecimento categoria]
   {:transacao/id              (uuid)
    :transacao/data            data
    :transacao/valor           valor
    :transacao/estabelecimento estabelecimento
    :transacao/categoria       categoria}))

(defn adicionar-cliente
  "Adiciona um cliente na lista de clientes"
  ([cliente lista]
   (conj lista cliente))

  ([nome cpf email]
   (adicionar-cliente (uuid) nome cpf email))

  ([uuid nome cpf email]
   {:cliente/id    uuid
    :cliente/nome  nome
    :cliente/cpf   cpf
    :cliente/email email})

  ([uuid nome cpf email cartao]
   {:cliente/id     uuid
    :cliente/nome   nome
    :cliente/cpf    cpf
    :cliente/email  email
    :cliente/cartao cartao}))

(defn adicionar-cartao
  "Adiciona um cartão na lista de cartões"
  ([cartao lista]
   (conj lista cartao))

  ([numero cvv validade limite]
   {:cartao/numero   numero
    :cartao/cvv      cvv
    :cartao/validade validade
    :cartao/limite   limite})

  ([numero cvv validade limite transacao]
   {:cartao/numero    numero
    :cartao/cvv       cvv
    :cartao/validade  validade
    :cartao/limite    limite
    :cartao/transacao transacao}))

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