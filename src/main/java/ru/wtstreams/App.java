package ru.wtstreams;

import ru.wtstreams.controller.console.ConsoleController;
import ru.wtstreams.model.OrderStatus;
import ru.wtstreams.model.PaymentType;
import ru.wtstreams.model.Stream;
import ru.wtstreams.model.StreamOrder;
import ru.wtstreams.model.Streamer;
import ru.wtstreams.model.Vehicle;
import ru.wtstreams.repository.OrderRepository;
import ru.wtstreams.repository.StreamRepository;
import ru.wtstreams.repository.StreamerRepository;
import ru.wtstreams.repository.VehicleRepository;
import ru.wtstreams.repository.inmemory.InMemoryOrderRepository;
import ru.wtstreams.repository.inmemory.InMemoryStreamRepository;
import ru.wtstreams.repository.inmemory.InMemoryStreamerRepository;
import ru.wtstreams.repository.inmemory.InMemoryVehicleRepository;
import ru.wtstreams.service.OrderService;
import ru.wtstreams.service.StreamService;
import ru.wtstreams.service.StreamerService;
import ru.wtstreams.service.VehicleService;

import java.math.BigDecimal;

public class App {

    public static void main(String[] args) {
        StreamerRepository streamerRepository = new InMemoryStreamerRepository();
        VehicleRepository vehicleRepository = new InMemoryVehicleRepository();
        StreamRepository streamRepository = new InMemoryStreamRepository();
        OrderRepository orderRepository = new InMemoryOrderRepository();

        StreamerService streamerService = new StreamerService(streamerRepository);
        VehicleService vehicleService = new VehicleService(vehicleRepository);
        StreamService streamService = new StreamService(streamRepository, streamerService, vehicleService);
        OrderService orderService = new OrderService(orderRepository, streamService);

        seedData(streamerService, vehicleService, streamService, orderService);

        ConsoleController controller = new ConsoleController(
                streamerService, vehicleService, streamService, orderService);
        controller.run();
    }

    private static void seedData(StreamerService streamerService,
                                 VehicleService vehicleService,
                                 StreamService streamService,
                                 OrderService orderService) {
        Streamer first = streamerService.register("LiveToWar", "Twitch", 15400, "USSR");
        Streamer second = streamerService.register("TankMaster", "YouTube", 8200, "Germany");

        Vehicle t34 = vehicleService.add("T-34-85", "USSR", "Танк", 6.3);
        Vehicle tiger = vehicleService.add("Tiger H1", "Germany", "Танк", 5.7);
        Vehicle mustang = vehicleService.add("P-51D-30", "USA", "Самолёт", 6.0);

        Stream liveStream = streamService.createDraft(first.getId(), t34.getId(), "Марафон на ИС-7");
        streamService.start(liveStream.getId());
        streamService.updateViewers(liveStream.getId(), 320);

        Stream plannedStream = streamService.createDraft(second.getId(), tiger.getId(), "Топ-500 на Тигре");
        streamService.createDraft(second.getId(), mustang.getId(), "Освоение Мустанга");

        StreamOrder paid = orderService.createOrder(liveStream.getId(), "Fan1337",
                "Прокати на ИС-7", PaymentType.DONATION, new BigDecimal("500.00"));
        orderService.changeStatus(paid.getId(), OrderStatus.DONE);

        StreamOrder pending = orderService.createOrder(liveStream.getId(), "SniperBoy",
                "Сыграй отрядом", PaymentType.POINTS, new BigDecimal("150.00"));

        System.out.println("Загружены тестовые данные:");
        System.out.println("  Стримеров: " + streamerService.getAll().size());
        System.out.println("  Техники: " + vehicleService.getAll().size());
        System.out.println("  Стримов: " + streamService.getAll().size());
        System.out.println("  Заказов: " + orderService.getAll().size());
        System.out.println("  Стример #" + plannedStream.getStreamerId()
                + " запланировал стрим '" + plannedStream.getTitle() + "'");
        System.out.println("  Ожидающий заказ id=" + pending.getId() + " от " + pending.getViewer());
        System.out.println();
    }
}
