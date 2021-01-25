package com.fbmoll.badges.persistence.data;

public class Game {

    private int position;
    private  String name;
    private  String discount;
    private Double price;

    public Game(int position, String name, String discount, Double price) {
        this.position = position;
        this.name = name;
        this.discount = discount;
        this.price = price;
    }

    public Game() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
