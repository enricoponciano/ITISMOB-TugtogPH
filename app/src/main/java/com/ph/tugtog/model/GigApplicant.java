package com.ph.tugtog.model;

public class GigApplicant {
    private MyGig gig;
    private String applicantName;
    private int avatarResId;
    private boolean isBooked;

    public GigApplicant(MyGig gig, String applicantName, int avatarResId) {
        this.gig = gig;
        this.applicantName = applicantName;
        this.avatarResId = avatarResId;
        this.isBooked = false;
    }

    public MyGig getGig() { return gig; }
    public String getApplicantName() { return applicantName; }
    public int getAvatarResId() { return avatarResId; }
    public boolean isBooked() { return isBooked; }
    public void setBooked(boolean booked) { isBooked = booked; }
}
