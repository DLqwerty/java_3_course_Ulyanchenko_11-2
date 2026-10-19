package ru.wtstreams.model;

public class Streamer {

    private Long id;
    private String nickname;
    private String platform;
    private int subscribers;
    private String mainNation;

    public Streamer() {
    }

    public Streamer(Long id, String nickname, String platform, int subscribers, String mainNation) {
        this.id = id;
        this.nickname = nickname;
        this.platform = platform;
        this.subscribers = subscribers;
        this.mainNation = mainNation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    public String getMainNation() {
        return mainNation;
    }

    public void setMainNation(String mainNation) {
        this.mainNation = mainNation;
    }

    @Override
    public String toString() {
        return "Streamer{id=%d, nickname='%s', platform='%s', subscribers=%d, mainNation='%s'}"
                .formatted(id, nickname, platform, subscribers, mainNation);
    }
}
