package com.example.tom.mineclicker;

public class HighscoreModel {
    private int rank;
    private String username;
    private int floor;
    private int clicks;
    private String country;

    public HighscoreModel(int rank, String username, int floor, int clicks, String country)
    {
        this.rank = rank;
        this.username = username;
        this.floor = floor;
        this.clicks = clicks;
        this.country = country;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
