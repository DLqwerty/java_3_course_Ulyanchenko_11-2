package ru.wtstreams.model;

import java.time.LocalDateTime;

public class Stream {

    private Long id;
    private Long streamerId;
    private Long vehicleId;
    private String title;
    private LocalDateTime startTime;
    private int viewers;
    private boolean isLive;

    public Stream() {
    }

    public Stream(Long id, Long streamerId, Long vehicleId, String title,
                  LocalDateTime startTime, int viewers, boolean isLive) {
        this.id = id;
        this.streamerId = streamerId;
        this.vehicleId = vehicleId;
        this.title = title;
        this.startTime = startTime;
        this.viewers = viewers;
        this.isLive = isLive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStreamerId() {
        return streamerId;
    }

    public void setStreamerId(Long streamerId) {
        this.streamerId = streamerId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    @Override
    public String toString() {
        return "Stream{id=%d, streamerId=%d, vehicleId=%d, title='%s', startTime=%s, viewers=%d, live=%s}"
                .formatted(id, streamerId, vehicleId, title, startTime, viewers, isLive);
    }
}
