# Sistema de Gestão de Pedidos

Sistema distribuído para **gestão de pedidos e processamento de pagamentos**, desenvolvido com foco em arquitetura de software, separação de responsabilidades e comunicação entre serviços.

O projeto simula o funcionamento de uma plataforma de e-commerce, na qual diferentes serviços são responsáveis por partes específicas do negócio.

---

## 🎯 Objetivo do projeto

O objetivo é desenvolver uma arquitetura composta por serviços independentes, cada um responsável por um determinado domínio do negócio.

O sistema busca representar um fluxo semelhante ao de uma compra:

```text
Cliente
   ↓
Pedido
   ↓
Catálogo / Produtos
   ↓
Promoção
   ↓
Pagamento
   ↓
Financeiro
   ↓
Administração
```

Cada etapa possui suas próprias responsabilidades e regras de negócio.

---

# 🏗️ Visão geral da arquitetura

O sistema é composto por diferentes serviços:

```text
                         ┌────────────────────┐
                         │      CLIENTE       │
                         └─────────┬──────────┘
                                   │
                                   ▼
                         ┌────────────────────┐
                         │   ORDER SERVICE    │
                         │      Pedidos       │
                         └─────────┬──────────┘
                                   │
             ┌─────────────────────┼─────────────────────┐
             │                     │                     │
             ▼                     ▼                     ▼
     ┌───────────────┐     ┌───────────────┐    ┌───────────────┐
     │   CUSTOMER    │     │    CATALOG     │    │  PROMOTION    │
     │    SERVICE    │     │    SERVICE     │    │    SERVICE    │
     │   Clientes    │     │   Produtos     │    │    Descontos  │
     └───────────────┘     └───────────────┘    └───────────────┘
                                   │
                                   ▼
                         ┌────────────────────┐
                         │  PAYMENT SERVICE   │
                         │     Pagamentos     │
                         └─────────┬──────────┘
                                   │
                                   ▼
                         ┌────────────────────┐
                         │ FINANCIAL SERVICE  │
                         │     Financeiro     │
                         └────────────────────┘

                         ┌────────────────────┐
                         │ ADMINISTRATION     │
                         │      SERVICE       │
                         └────────────────────┘
```

---

# 🧩 Serviços

## Order Service

Responsável pelo **gerenciamento dos pedidos**.

Entre suas responsabilidades estão:

* criação e gerenciamento de pedidos;
* controle do ciclo de vida do pedido;
* relacionamento entre cliente, produtos e pagamento;
* cálculo e composição das informações do pedido;
* acompanhamento do status do pedido.

O `Order Service` representa o contexto central do processo de compra.

---

## Customer Service

Responsável pelo **gerenciamento dos clientes**.

Exemplos de responsabilidades:

* cadastro de clientes;
* consulta de clientes;
* manutenção dos dados cadastrais;
* identificação do cliente relacionado ao pedido.

---

## Catalog Service

Responsável pelo **catálogo de produtos**.

Suas responsabilidades incluem:

* produtos;
* informações dos produtos;
* preços;
* disponibilidade;
* consulta dos itens disponíveis para compra.

---

## Promotion Service

Responsável pelas **promoções e regras de desconto**.

Pode ser utilizado pelo fluxo de pedidos para:

* consultar promoções;
* validar regras promocionais;
* calcular descontos;
* determinar condições de aplicação de uma promoção.

---

## Payment Service

Responsável pelo **domínio de pagamentos**.

O serviço controla o ciclo de vida do pagamento associado a um pedido.

Exemplo de fluxo:

```text
PENDING
   │
   ├──→ APPROVED
   │       │
   │       ├──→ CANCELLED
   │       │
   │       └──→ REFUNDED
   │
   └──→ REJECTED
```

Entre suas responsabilidades estão:

* criação do pagamento;
* consulta do pagamento;
* aprovação;
* rejeição;
* cancelamento;
* estorno;
* controle do estado do pagamento;
* garantia das regras de negócio relacionadas ao pagamento.

O pagamento é tratado como um domínio próprio, separado do pedido.

---

## Financial Service

Responsável pelo **domínio financeiro**.

O objetivo é representar as responsabilidades relacionadas à movimentação e ao controle financeiro decorrentes dos pagamentos realizados.

---

## Administration Service

Responsável pelas funcionalidades relacionadas à **administração do sistema**.

Pode concentrar operações administrativas, configurações e funcionalidades destinadas ao gerenciamento interno da plataforma.

---

# 🔄 Fluxo macro de uma compra

Um possível fluxo de negócio é:

```text
1. Cliente realiza uma compra
          ↓
2. Order Service cria o pedido
          ↓
3. Customer Service identifica o cliente
          ↓
4. Catalog Service fornece os produtos
          ↓
5. Promotion Service verifica descontos
          ↓
6. Pedido calcula o valor final
          ↓
7. Payment Service processa o pagamento
          ↓
8. Pagamento é aprovado ou rejeitado
          ↓
9. Financial Service registra o resultado financeiro
          ↓
10. Order Service atualiza o estado do pedido
```

A ideia é que cada serviço seja responsável pelo seu próprio domínio, evitando concentrar todas as regras em uma única aplicação.

---

# 🔗 Comunicação entre os serviços

Os serviços podem se comunicar de forma síncrona ou assíncrona, dependendo da necessidade do fluxo.

### Comunicação síncrona

Utilizada quando uma resposta é necessária imediatamente.

Exemplo:

```text
Order Service
      │
      │ consulta
      ▼
Catalog Service
      │
      │ resposta
      ▼
Order Service
```

### Comunicação assíncrona

Utilizada para propagação de eventos e processamento desacoplado.

Exemplo:

```text
Payment Service
      │
      │ PaymentApproved
      ▼
     Kafka
      │
      ├───────────────┐
      ▼               ▼
Order Service    Financial Service
```

Dessa forma, um acontecimento em um domínio pode ser comunicado aos demais serviços interessados sem criar uma dependência direta entre eles.

---

# 📡 Eventos

O projeto utiliza a ideia de **eventos de domínio/eventos de integração** para representar acontecimentos relevantes no sistema.

Exemplos:

```text
OrderCreated
PaymentCreated
PaymentApproved
PaymentRejected
PaymentCancelled
PaymentRefunded
```

Um evento representa algo que **já aconteceu** no sistema.

Por exemplo:

```text
PaymentApproved
```

significa:

> Um pagamento foi aprovado.

Esse evento pode ser consumido por outros serviços que precisam reagir a essa informação.

---

# 🧱 Arquitetura interna dos serviços

Embora o sistema seja dividido em diferentes serviços, cada serviço pode possuir sua própria organização interna.

No `Payment Service`, por exemplo:

```text
Payment Service
│
├── Domain
│
├── Application
│
├── Interface Adapters
│
└── Infrastructure
```

A separação interna busca manter as regras de negócio independentes dos detalhes tecnológicos.

---

# 🧠 Conceitos arquiteturais utilizados

O projeto foi desenvolvido como laboratório prático para estudar:

* Microservices;
* Domain-Driven Design (DDD);
* Bounded Contexts;
* Clean Architecture;
* Hexagonal Architecture;
* SOLID;
* Dependency Inversion;
* Ports and Adapters;
* Domain Events;
* Event-driven architecture;
* Kafka;
* Redis;
* PostgreSQL;
* APIs REST;
* comunicação entre serviços.

---

# 🗺️ Bounded Contexts

Cada serviço representa um contexto específico do negócio.

Uma visão simplificada:

```text
┌──────────────────────────────────────────────────────┐
│                  SISTEMA DE PEDIDOS                  │
│                                                      │
│  Customer     Catalog      Promotion     Order       │
│     │            │             │           │         │
│     └────────────┴─────────────┴───────────┘         │
│                                              │       │
│                                              ▼       │
│                                           Payment    │
│                                              │       │
│                                              ▼       │
│                                          Financial   │
│                                                      │
└──────────────────────────────────────────────────────┘
```

Cada contexto possui:

* responsabilidades próprias;
* regras próprias;
* modelos próprios;
* linguagem específica do domínio.

O objetivo não é simplesmente dividir uma aplicação em vários projetos, mas **separar responsabilidades de negócio**.

---

# 🎯 Objetivo arquitetural

O principal objetivo do projeto é estudar como construir um sistema no qual:

```text
Negócio
   ↓
Domínios separados
   ↓
Serviços independentes
   ↓
Contratos de comunicação
   ↓
Eventos
   ↓
Infraestrutura
```

A tecnologia é utilizada como meio para implementar essas decisões.

A arquitetura deve refletir o negócio, e não o contrário.

---

# 🚧 Status

Projeto em desenvolvimento.

### Serviços

* [x] Order Service — em desenvolvimento
* [x] Customer Service — em desenvolvimento
* [x] Catalog Service — em desenvolvimento
* [x] Promotion Service — em desenvolvimento
* [x] Payment Service — em desenvolvimento
* [x] Financial Service — em desenvolvimento
* [x] Administration Service — em desenvolvimento

### Integrações

* [ ] Comunicação completa entre serviços
* [ ] Kafka
* [ ] Redis
* [ ] Persistência completa
* [ ] Observabilidade
* [ ] Testes de integração
* [ ] Docker Compose completo

---

# 📚 Propósito de aprendizado

Este projeto foi criado para transformar conceitos de arquitetura de software em uma implementação prática.

O foco não é apenas construir APIs, mas compreender:

> **qual problema cada serviço resolve, qual responsabilidade pertence a cada domínio e como os diferentes contextos colaboram para formar o sistema como um todo.**
