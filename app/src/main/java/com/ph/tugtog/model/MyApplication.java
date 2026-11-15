package com.ph.tugtog.model;

/** Represents a band's application to a specific gig. */
public class MyApplication {

    public enum Status { PENDING, BOOKED }

    private DiscoverGig gig;
    private Status status;

    public MyApplication(DiscoverGig gig, Status status) {
        this.gig = gig;
        this.status = status;
    }

    public DiscoverGig getGig()   { return gig; }
    public Status getStatus()     { return status; }
}
