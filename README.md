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

```
model → repository (interface) → repository.inmemory (impl) → service → controller.console
```

- **model** — POJO-сущности (Streamer, Vehicle, Stream, StreamOrder) + enum'ы
- **repository** — интерфейсы для доступа к данным (StreamerRepository, VehicleRepository, StreamRepository, OrderRepository)
- **repository.inmemory** — реализации на `ConcurrentHashMap` с `AtomicLong` для генерации ID
- **service** — бизнес-логика и валидации (StreamerService, VehicleService, StreamService, OrderService)
- **controller.console** — консольное меню
- **App** — точка входа, собирает граф зависимостей вручную (без Spring)

Сервисы зависят от интерфейсов репозиториев, а не от конкретных классов — это позволит на следующей аттестации подменить in-memory реализацию на JDBC без правок сервисов и контроллера.

## Запуск

```bash
mvn clean package
java -jar target/wt-streams-console-jar-with-dependencies.jar
```

При старте загружаются тестовые данные: 2 стримера, 3 единицы техники, 3 стрима, 2 заказа — чтобы можно было сразу проверить работу без ручного ввода.

## Что демонстрирует

- **Слоистая архитектура**: контроллер не знает про `HashMap`, сервис не знает про консоль.
- **Бизнес-валидации в сервисах**: уникальность ника стримера, диапазон BR (1.0–12.0), whitelist типов техники, проверка существования стрима перед заказом, проверка существования стримера и техники перед созданием стрима.
- **State machine для заказов**: нельзя изменить статус `DONE`, из `REJECTED` — только в `NEW`.
- **Раздельный подсчёт заработка**: очки и донаты суммируются только по заказам в статусе `DONE`.
- **Потокобезопасность**: `ConcurrentHashMap` + `AtomicLong` — готовность к многопоточному доступу.

## Структура проекта

```
src/main/java/ru/wtstreams/
├── App.java                    — точка входа
├── model/                      — сущности и enum'ы
├── repository/                 — интерфейсы репозиториев
│   └── inmemory/               — in-memory реализации
├── service/                    — бизнес-логика
└── controller/console/         — консольный контроллер
```
