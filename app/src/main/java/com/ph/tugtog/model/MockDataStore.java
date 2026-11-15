package com.ph.tugtog.model;

import com.ph.tugtog.R;

import java.util.ArrayList;
import java.util.List;

public class MockDataStore {
    private static MockDataStore instance;

    public List<MyGig> myGigs;
    public List<GigApplicant> applicants;
    public List<DiscoverGig> discoverGigs;
    public List<MyApplication> myApplications;

    private MockDataStore() {
        myGigs = new ArrayList<>();
        applicants = new ArrayList<>();
        discoverGigs = new ArrayList<>();
        myApplications = new ArrayList<>();

        // --- Organizer mock data ---
        MyGig gig1 = new MyGig("Indie & Math Rock Night", "SAT, NOV 7", "₱3,500", 3, 5, R.drawable.mows_venue, "Looking for high energy bands. Bring own breakables.");
        MyGig gig2 = new MyGig("Acoustic Fridays", "FRI, NOV 13", "₱2,500", 1, 3, R.drawable.mows_venue, "Chill acoustic sets. Covers and originals welcome.");
        MyGig gig3 = new MyGig("Sunday Jam", "SUN, NOV 22", "₱4,000", 2, 4, R.drawable.mows_venue, "Open jam formatting. House backline provided.");

        myGigs.add(gig1);
        myGigs.add(gig2);
        myGigs.add(gig3);

        applicants.add(new GigApplicant(gig1, "Hiraya Collective", R.drawable.band1));
        applicants.add(new GigApplicant(gig2, "John Doe Music", R.drawable.band2));
        applicants.add(new GigApplicant(gig3, "The Submarines", R.drawable.band3));
        applicants.add(new GigApplicant(gig3, "Lola Amour", R.drawable.band1));
        applicants.add(new GigApplicant(gig3, "Any Name's Okay", R.drawable.band2));

        // --- Band mock data (Discover feed) ---
        DiscoverGig dg1 = new DiscoverGig(
                "Indie & Math Rock Night", "Mow's Bar QC", "SAT, NOV 7", "₱3,500",
                3, 5, R.drawable.mows_venue,
                "Looking for high energy bands. Bring own breakables.",
                "Matalino St., Quezon City");
        DiscoverGig dg2 = new DiscoverGig(
                "Acoustic Fridays", "Route 196", "FRI, NOV 13", "₱2,500",
                1, 3, R.drawable.route196_venue,
                "Chill acoustic sets. Covers and originals welcome.",
                "Katipunan Ave., Quezon City");
        DiscoverGig dg3 = new DiscoverGig(
                "Sunday Jam", "Saguijo", "SUN, NOV 22", "₱4,000",
                2, 4, R.drawable.saguijo_venue,
                "Open jam format. House backline provided.",
                "Guijo St., Makati");

        discoverGigs.add(dg1);
        discoverGigs.add(dg2);
        discoverGigs.add(dg3);

        // Band's existing applications
        myApplications.add(new MyApplication(dg1, MyApplication.Status.PENDING));
        myApplications.add(new MyApplication(dg3, MyApplication.Status.BOOKED));
    }

    public static MockDataStore getInstance() {
        if (instance == null) {
            instance = new MockDataStore();
        }
        return instance;
    }
}
