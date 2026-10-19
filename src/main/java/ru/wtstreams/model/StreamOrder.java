package ru.wtstreams.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StreamOrder {

    private Long id;
    private Long streamId;
    private String viewer;
    private String action;
    private PaymentType paymentType;
    private BigDecimal amount;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public StreamOrder() {
    }

    public StreamOrder(Long id, Long streamId, String viewer, String action,
                       PaymentType paymentType, BigDecimal amount,
                       OrderStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.streamId = streamId;
        this.viewer = viewer;
        this.action = action;
        this.paymentType = paymentType;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStreamId() {
        return streamId;
    }

    public void setStreamId(Long streamId) {
        this.streamId = streamId;
    }

    public String getViewer() {
        return viewer;
    }

    public void setViewer(String viewer) {
        this.viewer = viewer;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "StreamOrder{id=%d, streamId=%d, viewer='%s', action='%s', paymentType=%s, amount=%s, status=%s, createdAt=%s}"
                .formatted(id, streamId, viewer, action, paymentType, amount, status, createdAt);
    }
}
