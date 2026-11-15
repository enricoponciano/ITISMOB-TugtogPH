package com.ph.tugtog.model;

import java.io.Serializable;

/** A gig listing visible to bands browsing the discover feed. */
public class DiscoverGig implements Serializable {

    private String title;
    private String venueName;
    private String date;
    private String payRate;
    private int currentSlots;
    private int maxSlots;
    private int coverResId;
    private String description;
    private String venueAddress;

    public DiscoverGig(String title, String venueName, String date, String payRate,
                       int currentSlots, int maxSlots, int coverResId,
                       String description, String venueAddress) {
        this.title = title;
        this.venueName = venueName;
        this.date = date;
        this.payRate = payRate;
        this.currentSlots = currentSlots;
        this.maxSlots = maxSlots;
        this.coverResId = coverResId;
        this.description = description;
        this.venueAddress = venueAddress;
    }

    public String getTitle()        { return title; }
    public String getVenueName()    { return venueName; }
    public String getDate()         { return date; }
    public String getPayRate()      { return payRate; }
    public int getCurrentSlots()    { return currentSlots; }
    public int getMaxSlots()        { return maxSlots; }
    public int getCoverResId()      { return coverResId; }
    public String getDescription()  { return description; }
    public String getVenueAddress() { return venueAddress; }
}
