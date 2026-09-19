# Payment System

Educational mini payment system demonstrating **event-driven microservices architecture** in Java.

The project consists of two Spring Boot services communicating via Kafka, sharing a common module for DTOs, and backed by PostgreSQL, Redis, and Liquibase.

- **payment-service** — REST API for payments and users. Publishes `PAYMENT_CREATED` / `PAYMENT_SUCCEEDED` events to Kafka.
- **audit-service** — Kafka consumer. Saves each event to `payment_audit_log`, provides `GET /api/audit/payments/{id}` for history.
- **common** — shared module with `PaymentEvent` record used by both services.

## Tech Stack

- **Java 22**
- **Spring Boot 4.1.1** (Spring Web, Spring Data JPA, Spring Kafka, Spring Data Redis)
- **PostgreSQL 16** — main database
- **Liquibase** — schema migrations
- **Apache Kafka** — event streaming between services
- **Redis** — cache for payments
- **Docker Compose** — infrastructure
- **Gradle** — multi-module build

## Features

### Payments
- Create payment with user existence check and amount limit (10,000)
- Get payment (cached in Redis)
- Confirm payment — status changes to `SUCCEEDED`, cache is invalidated
- Every status change publishes a Kafka event

### Users
- Create user (unique email validation)
- Get users with their payments

### Caching (Cache-Aside)
Payments are cached in Redis using the cache-aside pattern:
- Read: check Redis → on miss, read DB → put to Redis (`@Cacheable`)
- Write: update DB → invalidate cache (`@CacheEvict`)
- TTL: 30 seconds — protects against stale data
- JSON serialization via `RedisCacheConfiguration`

### Kafka (Pub/Sub)
- **Producer** in `payment-service` publishes events to topic `payments.events` when a payment is created or confirmed.
- **Consumer** in `audit-service` reads events in its own consumer group (`payment-service-audit`) and saves them to `payment_audit_log`.
- Each service has its own database schema.
- Event payload is defined once in the `common` module.

### Idempotent Consumer
Kafka guarantees **at-least-once** delivery, so a consumer might receive the same event twice (e.g. after rebalance or crash). To handle this:
- Every processed event's `eventId` is stored in `processed_events` table with a unique primary key.
- On incoming event: attempt `INSERT ... ON CONFLICT DO NOTHING`.
- If conflict → skip. If inserted → process.

This makes the consumer **idempotent** — the same event is never processed twice.

## API

### payment-service (port 8080)
#### Users:
```bash
POST /api/users — create user
GET /api/users — list all users
GET /api/users/{id} — get user with payments
```

#### Payments
```bash
POST /api/payments — create payment
GET /api/payments/{id} — get payment (cached)
POST /api/payments/{id}/confirm — confirm payment
```

Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### audit-service (port 8081)
GET /api/audit/payments/{id} — audit history for a payment

Example response for `GET /api/audit/payments/44`:

```json
[
  {
    "id": 1,
    "eventId": "89d1a298-b38c-4518-84a3-220f2583e97b",
    "paymentId": 44,
    "eventType": "PAYMENT_CREATED",
    "status": "NEW",
    "amount": 60.00,
    "occurredAt": "2026-09-19T17:29:22.868561",
    "receivedAt": "2026-09-19T17:29:23.305160"
  }
]
```

## How to run

Requirements: Java 22, Docker.

1. Start infrastructure:

```bash
docker-compose up -d
```

This runs Postgres (5432), Redis (6379), and Kafka (9092).
2. Run PaymentServiceApplication — port 8080.
3. Run AuditServiceApplication — port 8081.
4. Open Swagger: http://localhost:8080/swagger-ui/index.html
5. Create a payment via Swagger. Check the audit:

```bash
curl http://localhost:8081/api/audit/payments/{id}
```

## Project Structure

```text
payment-system/
├── common/                          # Shared DTOs and events
│   └── src/main/java/dev/yalerikk/common/events/
├── payment-service/                 # Payment REST API + Kafka producer
│   └── src/main/java/dev/yalerikk/paymentservice/
├── audit-service/                   # Kafka consumer + audit API
│   └── src/main/java/dev/yalerikk/audit_service/
├── docker-compose.yml
├── build.gradle
└── settings.gradle
```

## What I Learned

- Reorganized a single-module Spring Boot app into a multi-module Gradle project with a shared common module.
- Implemented publish/subscribe with Kafka across two services in independent consumer groups.
- Set up an idempotent Kafka consumer using processed_events + INSERT ... ON CONFLICT DO NOTHING.
- Debugged Kafka deserialization issues in Spring Boot 4 (Jackson 3), where spring-kafka still relies on Jackson 2 — solved by explicitly adding Jackson 2 dependencies to the consumer module.
- Applied the Cache-Aside pattern with Redis: @Cacheable on read, @CacheEvict on write, JSON serialization, TTL.
- Migrated database schema with Liquibase across two services with separate schemas.