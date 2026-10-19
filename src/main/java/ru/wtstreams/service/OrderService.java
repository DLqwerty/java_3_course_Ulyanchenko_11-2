package ru.wtstreams.service;

import ru.wtstreams.model.OrderStatus;
import ru.wtstreams.model.PaymentType;
import ru.wtstreams.model.StreamOrder;
import ru.wtstreams.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderService {

    private final OrderRepository orderRepository;
    private final StreamService streamService;

    public OrderService(OrderRepository orderRepository, StreamService streamService) {
        this.orderRepository = orderRepository;
        this.streamService = streamService;
    }

    public StreamOrder createOrder(Long streamId, String viewer, String action,
                                   PaymentType paymentType, BigDecimal amount) {
        if (!streamService.exists(streamId)) {
            throw new ServiceException("Стрим с id=" + streamId + " не найден");
        }
        if (viewer == null || viewer.isBlank()) {
            throw new ServiceException("Имя заказчика не может быть пустым");
        }
        if (action == null || action.isBlank()) {
            throw new ServiceException("Действие заказа не может быть пустым");
        }
        if (amount == null || amount.signum() < 1) {
            throw new ServiceException("Сумма заказа не может быть отрицательной");
        }
        StreamOrder order = new StreamOrder(null, streamId, viewer, action,
                paymentType, amount, OrderStatus.NEW, LocalDateTime.now());
        return orderRepository.save(order);
    }

    public StreamOrder changeStatus(Long orderId, OrderStatus newStatus) {
        StreamOrder order = getById(orderId);
        if (order.getStatus() == OrderStatus.DONE) {
            throw new ServiceException("Нельзя изменить статус завершённого заказа");
        }
        if (order.getStatus() == OrderStatus.REJECTED && newStatus != OrderStatus.NEW) {
            throw new ServiceException("Отклонённый заказ можно вернуть только в статус NEW");
        }
        order.setStatus(newStatus);
        orderRepository.update(order);
        return order;
    }

    public List<StreamOrder> getAll() {
        return orderRepository.findAll();
    }

    public List<StreamOrder> getByStreamId(Long streamId) {
        return orderRepository.findByStreamId(streamId);
    }

    public StreamOrder getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Заказ с id=" + id + " не найден"));
    }

    public BigDecimal calculateEarnings() {
        return calculateEarnings(orderRepository.findAll());
    }

    public BigDecimal calculateEarningsByStream(Long streamId) {
        return calculateEarnings(orderRepository.findByStreamId(streamId));
    }

    private BigDecimal calculateEarnings(List<StreamOrder> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DONE)
                .map(StreamOrder::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
