# 📅 Microsserviço de Eventos (`ms-events`)

Este microsserviço faz parte de uma aplicação distribuída com foco na **gestão de eventos** e comunicação assíncrona via **RabbitMQ**. Ele é responsável por cadastrar eventos, registrar participantes e enviar mensagens para outro microsserviço que lida com o envio de e-mails.

---

## 🚀 Funcionalidades

* Cadastro de novos eventos
* Listagem de todos os eventos
* Listagem de eventos futuros
* Registro de participantes (subscrição)
* Verificação de lotação de evento
* Comunicação com microsserviço de envio de e-mails via RabbitMQ

---

## 💠 Tecnologias Utilizadas

* Java 17
* Spring Boot
* Spring Data JPA
* MySQL
* RabbitMQ
* Maven
* Lombok

---

## 📃 Estrutura de Banco de Dados

### Entidade `EventModel`

| Campo                  | Tipo                    | Descrição                             |
| ---------------------- | ----------------------- | ------------------------------------- |
| id                     | Long                    | ID do evento (gerado automaticamente) |
| title                  | String                  | Título do evento                      |
| description            | String                  | Descrição do evento                   |
| date                   | Date                    | Data do evento                        |
| maxParticipants        | Integer                 | Número máximo de participantes        |
| registeredParticipants | Integer                 | Número atual de participantes         |
| subscriptions          | List<SubscriptionModel> | Lista de inscrições                   |

### Entidade `SubscriptionModel`

| Campo            | Tipo       | Descrição              |
| ---------------- | ---------- | ---------------------- |
| id               | Long       | ID da inscrição        |
| event            | EventModel | Evento relacionado     |
| participantEmail | String     | E-mail do participante |

---

## 🔁 Comunicação via RabbitMQ

Quando um participante se inscreve em um evento:

* O método `registerParticipant` envia uma mensagem para o **Exchange** `email-exchange`, usando a **Routing Key** `email-routing-key`.
* A mensagem é um `Map<String, Object>` com os dados do participante e do evento.
* Essa mensagem será consumida pelo microsserviço de e-mails (`ms-email`).

### Exemplo de envio:

```java
rabbitTemplate.convertAndSend("email-exchange", "email-routing-key", messageMap);
```

---

## 📥 Endpoints disponíveis

### 🔍 Listar eventos

`GET /events`
Retorna todos os eventos cadastrados.

### 📆 Listar eventos futuros

`GET /events/upcoming`
Retorna eventos cuja data ainda não ocorreu.

### ➕ Cadastrar evento

`POST /events`
Corpo JSON:

```json
{
  "title": "Workshop de Java",
  "description": "Aprenda Java com Spring Boot",
  "maxParticipants": 100,
  "date": "2025-07-15T14:00:00"
}
```

### 📟 Registrar participação em um evento

`POST /events/{eventId}/register`
Corpo JSON:

```json
{
  "participantEmail": "usuario@email.com"
}
```

**Regra de negócio:** O sistema verifica se o evento está lotado antes de registrar.

---

## ⚙️ Configurações principais (`application.properties`)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/event
spring.datasource.username=root
spring.datasource.password=1234

spring.rabbitmq.addresses=amqps://<user>:<password>@<host>/<vhost>
spring.rabbitmq.queue=ms.email.queue
```

---

## ✅ Requisitos para rodar localmente

* Java 17+
* MySQL rodando na porta 3306
* RabbitMQ (pode usar CloudAMQP)
* IDE (como IntelliJ ou VSCode)

---

## 📦 Execução

1. Clone o projeto:

```bash
git clone https://github.com/seu-usuario/ms-events.git
```

2. Configure o `application.properties`

3. Execute com Maven ou dentro da IDE:

```bash
mvn spring-boot:run
```

---

## 📧 Integração com ms-email

Este microsserviço **não envia e-mails diretamente**, apenas publica mensagens para uma fila do RabbitMQ. O envio é feito pelo microsserviço **`ms-email`**, que consome a fila.

---