package ru.wtstreams.controller.console;

import ru.wtstreams.model.OrderStatus;
import ru.wtstreams.model.PaymentType;
import ru.wtstreams.model.Stream;
import ru.wtstreams.model.StreamOrder;
import ru.wtstreams.model.Streamer;
import ru.wtstreams.model.Vehicle;
import ru.wtstreams.service.OrderService;
import ru.wtstreams.service.ServiceException;
import ru.wtstreams.service.StreamService;
import ru.wtstreams.service.StreamerService;
import ru.wtstreams.service.VehicleService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleController {

    private final Scanner scanner = new Scanner(System.in);

    private final StreamerService streamerService;
    private final VehicleService vehicleService;
    private final StreamService streamService;
    private final OrderService orderService;

    public ConsoleController(StreamerService streamerService,
                             VehicleService vehicleService,
                             StreamService streamService,
                             OrderService orderService) {
        this.streamerService = streamerService;
        this.vehicleService = vehicleService;
        this.streamService = streamService;
        this.orderService = orderService;
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> viewStreamers();
                    case "2" -> viewVehicles();
                    case "3" -> viewStreams();
                    case "4" -> viewOrders();
                    case "5" -> addStreamer();
                    case "6" -> addVehicle();
                    case "7" -> manageStreams();
                    case "8" -> createOrder();
                    case "9" -> changeOrderStatus();
                    case "10" -> showEarnings();
                    case "0" -> running = false;
                    default -> System.out.println("Неизвестная команда. Попробуйте снова.");
                }
            } catch (ServiceException e) {
                System.out.println("Ошибка: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число.");
            }
            System.out.println();
        }
        System.out.println("Выход из приложения. До встречи!");
    }

    private void printMainMenu() {
        System.out.println("=== WT Streams Console ===");
        System.out.println("1.  Показать стримеров");
        System.out.println("2.  Показать технику");
        System.out.println("3.  Показать стримы");
        System.out.println("4.  Показать заказы");
        System.out.println("5.  Зарегистрировать стримера");
        System.out.println("6.  Добавить технику");
        System.out.println("7.  Управление стримами (создать/запустить/завершить)");
        System.out.println("8.  Создать заказ");
        System.out.println("9.  Изменить статус заказа");
        System.out.println("10. Сводка по заработку");
        System.out.println("0.  Выход");
        System.out.print("Выберите пункт: ");
    }

    private void viewStreamers() {
        List<Streamer> streamers = streamerService.getAll();
        if (streamers.isEmpty()) {
            System.out.println("Стримеры отсутствуют.");
            return;
        }
        streamers.forEach(System.out::println);
    }

    private void viewVehicles() {
        List<Vehicle> vehicles = vehicleService.getAll();
        if (vehicles.isEmpty()) {
            System.out.println("Техника отсутствует.");
            return;
        }
        vehicles.forEach(System.out::println);
    }

    private void viewStreams() {
        List<Stream> streams = streamService.getAll();
        if (streams.isEmpty()) {
            System.out.println("Стримы отсутствуют.");
            return;
        }
        streams.forEach(System.out::println);
    }

    private void viewOrders() {
        List<StreamOrder> orders = orderService.getAll();
        if (orders.isEmpty()) {
            System.out.println("Заказы отсутствуют.");
            return;
        }
        orders.forEach(System.out::println);
    }

    private void addStreamer() {
        System.out.print("Никнейм: ");
        String nickname = scanner.nextLine().trim();
        System.out.print("Платформа (Twitch/YouTube/...): ");
        String platform = scanner.nextLine().trim();
        System.out.print("Количество подписчиков: ");
        int subscribers = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Основная нация: ");
        String mainNation = scanner.nextLine().trim();

        Streamer streamer = streamerService.register(nickname, platform, subscribers, mainNation);
        System.out.println("Стример зарегистрирован: " + streamer);
    }

    private void addVehicle() {
        System.out.print("Название: ");
        String name = scanner.nextLine().trim();
        System.out.print("Нация: ");
        String nation = scanner.nextLine().trim();
        System.out.print("Тип (Танк/Самолёт/...): ");
        String type = scanner.nextLine().trim();
        System.out.print("Боевой рейтинг (1.0 - 12.0): ");
        double br = Double.parseDouble(scanner.nextLine().trim());

        Vehicle vehicle = vehicleService.add(name, nation, type, br);
        System.out.println("Техника добавлена: " + vehicle);
    }

    private void manageStreams() {
        System.out.println("7.1 Создать стрим");
        System.out.println("7.2 Запустить стрим");
        System.out.println("7.3 Завершить стрим");
        System.out.println("7.4 Обновить количество зрителей");
        System.out.print("Выберите действие: ");
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "7.1" -> createStream();
            case "7.2" -> startStream();
            case "7.3" -> stopStream();
            case "7.4" -> updateViewers();
            default -> System.out.println("Неизвестное действие.");
        }
    }

    private void createStream() {
        System.out.print("Id стримера: ");
        Long streamerId = Long.parseLong(scanner.nextLine().trim());
        System.out.print("Id техники: ");
        Long vehicleId = Long.parseLong(scanner.nextLine().trim());
        System.out.print("Название стрима: ");
        String title = scanner.nextLine().trim();

        Stream stream = streamService.createDraft(streamerId, vehicleId, title);
        System.out.println("Стрим создан: " + stream);
    }

    private void startStream() {
        System.out.print("Id стрима: ");
        Long streamId = Long.parseLong(scanner.nextLine().trim());
        Stream stream = streamService.start(streamId);
        System.out.println("Стрим запущен: " + stream);
    }

    private void stopStream() {
        System.out.print("Id стрима: ");
        Long streamId = Long.parseLong(scanner.nextLine().trim());
        Stream stream = streamService.stop(streamId);
        System.out.println("Стрим завершён: " + stream);
    }

    private void updateViewers() {
        System.out.print("Id стрима: ");
        Long streamId = Long.parseLong(scanner.nextLine().trim());
        System.out.print("Количество зрителей: ");
        int viewers = Integer.parseInt(scanner.nextLine().trim());
        Stream stream = streamService.updateViewers(streamId, viewers);
        System.out.println("Данные обновлены: " + stream);
    }

    private void createOrder() {
        System.out.print("Id стрима: ");
        Long streamId = Long.parseLong(scanner.nextLine().trim());
        System.out.print("Имя заказчика: ");
        String viewer = scanner.nextLine().trim();
        System.out.print("Действие (например, 'прокати на ИС-7'): ");
        String action = scanner.nextLine().trim();
        PaymentType paymentType = readPaymentType();
        System.out.print("Сумма: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine().trim());

        StreamOrder order = orderService.createOrder(streamId, viewer, action, paymentType, amount);
        System.out.println("Заказ создан: " + order);
    }

    private PaymentType readPaymentType() {
        while (true) {
            System.out.print("Тип оплаты (1 - POINTS, 2 - DONATION): ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> {
                    return PaymentType.POINTS;
                }
                case "2" -> {
                    return PaymentType.DONATION;
                }
                default -> System.out.println("Введите 1 или 2.");
            }
        }
    }

    private void changeOrderStatus() {
        System.out.print("Id заказа: ");
        Long orderId = Long.parseLong(scanner.nextLine().trim());
        OrderStatus newStatus = readOrderStatus();
        StreamOrder order = orderService.changeStatus(orderId, newStatus);
        System.out.println("Статус обновлён: " + order);
    }

    private OrderStatus readOrderStatus() {
        while (true) {
            System.out.print("Новый статус (1 - NEW, 2 - ACCEPTED, 3 - DONE, 4 - REJECTED): ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> {
                    return OrderStatus.NEW;
                }
                case "2" -> {
                    return OrderStatus.ACCEPTED;
                }
                case "3" -> {
                    return OrderStatus.DONE;
                }
                case "4" -> {
                    return OrderStatus.REJECTED;
                }
                default -> System.out.println("Введите число от 1 до 4.");
            }
        }
    }

    private void showEarnings() {
        System.out.println("Общий заработок (завершённые заказы): " + orderService.calculateEarnings());
    }
}
