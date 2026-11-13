package com.ph.tugtog.model;

public class MyGig {
    private String title;
    private String date;
    private String payRate;
    private int currentBookings;
    private int maxBookings;
    private int coverResId;

    private String description;

    public MyGig(String title, String date, String payRate, int currentBookings, int maxBookings, int coverResId, String description) {
        this.title = title;
        this.date = date;
        this.payRate = payRate;
        this.currentBookings = currentBookings;
        this.maxBookings = maxBookings;
        this.coverResId = coverResId;
        this.description = description;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getPayRate() { return payRate; }
    public void setPayRate(String payRate) { this.payRate = payRate; }
    public int getCurrentBookings() { return currentBookings; }
    public void setCurrentBookings(int currentBookings) { this.currentBookings = currentBookings; }
    public int getMaxBookings() { return maxBookings; }
    public void setMaxBookings(int maxBookings) { this.maxBookings = maxBookings; }
    public int getCoverResId() { return coverResId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
