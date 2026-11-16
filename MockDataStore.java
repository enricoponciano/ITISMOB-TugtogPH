package com.ph.tugtog.model;

import com.ph.tugtog.R;

import java.util.ArrayList;
import java.util.List;

public class MockDataStore {
    private static MockDataStore instance;

    public List<MyGig> myGigs;
    public List<GigApplicant> applicants;

    // Band side: gigs a band can browse, save and apply to
    public List<BandGig> bandGigs;

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

        // ---- Band side mock gigs ----
        bandGigs = new ArrayList<>();

        String mowsBackline = "5-pc Drum Kit, Ampeg Bass Combo, Roland JC-120 + Marshall Guitar Amps, 4x Vocal Mics.";
        String routeBackline = "4-pc Drum Kit, Bass Amp, 2x Guitar Amps, 3x Vocal Mics, DI boxes for acoustic sets.";
        String saguijoBackline = "5-pc Drum Kit, Hartke Bass Rig, Fender Twin + Marshall Half Stack, 4x Vocal Mics, Stage Monitors.";

        bandGigs.add(new BandGig("Indie & Math Rock Night", "Mow's Bar", "Matalino St.", "Quezon City",
                "SAT, NOV 21", "8:00 PM", "₱3,500", "Indie", 3, 5, R.drawable.mows_venue,
                "Looking for high energy bands for a 30-minute set. Originals preferred. Bring your own breakables.",
                mowsBackline, BandGig.STATUS_NONE, false));

        bandGigs.add(new BandGig("Acoustic Fridays", "Route 196", "Katipunan Ave.", "Quezon City",
                "FRI, NOV 20", "7:30 PM", "₱2,500", "Acoustic", 1, 3, R.drawable.route196_venue,
                "Chill acoustic sets, 45 minutes each. Covers and originals are both welcome.",
                routeBackline, BandGig.STATUS_APPLIED, false));

        bandGigs.add(new BandGig("Saguijo Rock Weekender", "Saguijo Cafe + Bar", "Guijo St.", "Makati",
                "SAT, NOV 28", "9:00 PM", "₱5,000", "Rock", 2, 4, R.drawable.saguijo_venue,
                "Four-band rock lineup. 40-minute sets, sound check at 6:00 PM. Merch table available.",
                saguijoBackline, BandGig.STATUS_NONE, true));

        bandGigs.add(new BandGig("Sunday Jam Session", "Mow's Bar", "Matalino St.", "Quezon City",
                "SUN, NOV 22", "6:00 PM", "₱4,000", "Open Jam", 2, 4, R.drawable.mows_venue,
                "Open jam format. Host band opens, then guests rotate in. House backline provided.",
                mowsBackline, BandGig.STATUS_BOOKED, false));

        bandGigs.add(new BandGig("Jazz & Soul Lounge", "Saguijo Cafe + Bar", "Guijo St.", "Makati",
                "FRI, DEC 4", "8:30 PM", "₱4,500", "Jazz", 4, 4, R.drawable.saguijo_venue,
                "Laid-back jazz, soul and R&B night. Two 45-minute sets per act.",
                saguijoBackline, BandGig.STATUS_NONE, false));

        bandGigs.add(new BandGig("Katipunan Indie Fest Pre-Party", "Route 196", "Katipunan Ave.", "Quezon City",
                "SAT, DEC 5", "7:00 PM", "₱6,000", "Indie", 0, 6, R.drawable.route196_venue,
                "Six-band showcase ahead of the fest. 25-minute sets, quick changeovers, shared drum kit.",
                routeBackline, BandGig.STATUS_NONE, false));
    }

    public static MockDataStore getInstance() {
        if (instance == null) {
            instance = new MockDataStore();
        }
        return instance;
    }
}
