# War Thunder Streams — Аттестация 1

Консольное приложение для управления стримами по War Thunder. 
Первая аттестация: модельный слой, in-memory репозитории, сервисы и консольный интерфейс.

## Стек
- **Java 17**
- **Maven** (сборка в исполняемый JAR)
- **In-memory storage**: `ConcurrentHashMap` + `AtomicLong`
- **Без фреймворков** — только чистый ООП и слоистая архитектура

## Сущности
| Сущность | Поля |
|----------|------|
| `Streamer` | id, nickname, platform, subscribers, mainNation |
| `Vehicle` | id, name, nation, type, br |
| `Stream` | id, streamerId, vehicleId, title, startTime, viewers, isLive |
| `StreamOrder` | id, streamId, viewer, action, paymentType, amount, status, createdAt |

Плюс два enum'а: `PaymentType` (POINTS / DONATION) и `OrderStatus` (NEW / ACCEPTED / DONE / REJECTED).

## Архитектура
