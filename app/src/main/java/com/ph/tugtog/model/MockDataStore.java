package com.ph.tugtog.model;

import com.ph.tugtog.R;

import java.util.ArrayList;
import java.util.List;

public class MockDataStore {
    private static MockDataStore instance;

    public List<MyGig> myGigs;
    public List<GigApplicant> applicants;

    private MockDataStore() {
        myGigs = new ArrayList<>();
        applicants = new ArrayList<>();

        // Create 3 Gigs for Mow's Bar
        MyGig gig1 = new MyGig("Indie & Math Rock Night", "SAT, NOV 7", "₱3,500", 3, 5, R.drawable.mows_venue, "Looking for high energy bands. Bring own breakables.");
        MyGig gig2 = new MyGig("Acoustic Fridays", "FRI, NOV 13", "₱2,500", 1, 3, R.drawable.mows_venue, "Chill acoustic sets. Covers and originals welcome.");
        MyGig gig3 = new MyGig("Sunday Jam", "SUN, NOV 22", "₱4,000", 2, 4, R.drawable.mows_venue, "Open jam formatting. House backline provided.");

        myGigs.add(gig1);
        myGigs.add(gig2);
        myGigs.add(gig3);

        // Add applicants to Gig 1
        applicants.add(new GigApplicant(gig1, "Hiraya Collective", R.drawable.band1));
        
        // Add applicants to Gig 2
        applicants.add(new GigApplicant(gig2, "John Doe Music", R.drawable.band2));
        
        // Add multiple applicants to Gig 3 to test booking limit (Current: 2/4)
        applicants.add(new GigApplicant(gig3, "The Submarines", R.drawable.band3));
        applicants.add(new GigApplicant(gig3, "Lola Amour", R.drawable.band1));
        applicants.add(new GigApplicant(gig3, "Any Name's Okay", R.drawable.band2));
    }

    public static MockDataStore getInstance() {
        if (instance == null) {
            instance = new MockDataStore();
        }
        return instance;
    }
}
