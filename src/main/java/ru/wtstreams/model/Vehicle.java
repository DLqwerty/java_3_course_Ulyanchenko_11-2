package ru.wtstreams.model;

public class Vehicle {

    private Long id;
    private String name;
    private String nation;
    private String type;
    private double br;

    public Vehicle() {
    }

    public Vehicle(Long id, String name, String nation, String type, double br) {
        this.id = id;
        this.name = name;
        this.nation = nation;
        this.type = type;
        this.br = br;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBr() {
        return br;
    }

    public void setBr(double br) {
        this.br = br;
    }

    @Override
    public String toString() {
        return "Vehicle{id=%d, name='%s', nation='%s', type='%s', br=%.1f}"
                .formatted(id, name, nation, type, br);
    }
}
