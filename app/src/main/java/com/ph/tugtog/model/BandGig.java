package com.ph.tugtog.model;

/**
 * A gig as seen from the Band side (browse, save, apply).
 */
public class BandGig {

    public static final int STATUS_NONE = 0;     // not applied yet
    public static final int STATUS_APPLIED = 1;  // applied, waiting for the organizer
    public static final int STATUS_BOOKED = 2;   // organizer confirmed the booking

    private final String title;
    private final String venue;
    private final String address;
    private final String city;
    private final String date;
    private final String time;
    private final String payRate;
    private final String genre;
    private int bookedSlots;
    private final int maxSlots;
    private final int coverResId;
    private final String description;
    private final String backline;

    private int status;
    private boolean saved;

    public BandGig(String title, String venue, String address, String city, String date, String time,
                   String payRate, String genre, int bookedSlots, int maxSlots, int coverResId,
                   String description, String backline, int status, boolean saved) {
        this.title = title;
        this.venue = venue;
        this.address = address;
        this.city = city;
        this.date = date;
        this.time = time;
        this.payRate = payRate;
        this.genre = genre;
        this.bookedSlots = bookedSlots;
        this.maxSlots = maxSlots;
        this.coverResId = coverResId;
        this.description = description;
        this.backline = backline;
        this.status = status;
        this.saved = saved;
    }

    public String getTitle() { return title; }
    public String getVenue() { return venue; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getPayRate() { return payRate; }
    public String getGenre() { return genre; }
    public int getBookedSlots() { return bookedSlots; }
    public void setBookedSlots(int bookedSlots) { this.bookedSlots = bookedSlots; }
    public int getMaxSlots() { return maxSlots; }
    public int getCoverResId() { return coverResId; }
    public String getDescription() { return description; }
    public String getBackline() { return backline; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public boolean isSaved() { return saved; }
    public void setSaved(boolean saved) { this.saved = saved; }

    public boolean isFull() { return bookedSlots >= maxSlots; }
    public int getSlotsLeft() { return Math.max(0, maxSlots - bookedSlots); }
    public String getLocation() { return address + ", " + city; }
}
