package com.ph.tugtog;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Band profile fields kept in the same mock SharedPreferences file the Venue profile uses.
 */
public final class BandPrefs {

    public static final String NAME = "band_name";
    public static final String CITY = "band_city";
    public static final String GENRES = "band_genres";
    public static final String BIO = "band_bio";
    public static final String GEAR = "band_gear";
    public static final String FB = "band_fb";
    public static final String PHONE = "band_phone";

    private BandPrefs() { }

    public static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences("TugtogMockData", Context.MODE_PRIVATE);
    }

    public static String get(Context context, String key) {
        return prefs(context).getString(key, defaultFor(key));
    }

    private static String defaultFor(String key) {
        switch (key) {
            case NAME: return "Hiraya Collective";
            case CITY: return "Quezon City";
            case GENRES: return "OPM Indie, Alt Rock";
            case BIO: return "5-piece alternative/math-rock band from QC.";
            case GEAR: return "2x Guitar Amps, 1x Bass Rig, 5-pc Drum Kit (own breakables), 3x Vocal Mics.";
            case FB: return "fb.com/hirayacollective";
            case PHONE: return "0917-555-0142";
            default: return "";
        }
    }
}
