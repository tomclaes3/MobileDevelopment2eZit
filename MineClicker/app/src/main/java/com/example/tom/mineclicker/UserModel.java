package com.example.tom.mineclicker;

public class UserModel {

    private int floor = 1;
    private int gold;
    private String username;
    private String country;
    private String password;
    private int dps;
    private int clickDamage;
    private int clickCount;
    private int userId;

    public int getId() {return userId;}

    public void setId(int id) {userId = id; }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDps() {
        return dps;
    }

    public void setDps(int dps) {
        this.dps = dps;
    }

    public int getClickDamage() {
        return clickDamage;
    }

    public void setClickDamage(int clickDamage) {
        this.clickDamage = clickDamage;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

}
