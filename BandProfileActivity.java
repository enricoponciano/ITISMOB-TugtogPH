package com.ph.tugtog;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.ph.tugtog.model.BandGig;
import com.ph.tugtog.model.MockDataStore;

/**
 * The band's own profile: what organizers see, plus edit, stats shortcuts and log out.
 */
public class BandProfileActivity extends AppCompatActivity {

    private static final String[] TRACK_META = {
            "LRT Line 2 EP • 412K plays",
            "LRT Line 2 EP • 289K plays"
    };

    private int playingTrack = -1; // -1 = nothing previewing

    private View[] trackBoxes;
    private ImageView[] trackIcons;
    private TextView[] trackMetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_band_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btnEditBandProfile).setOnClickListener(v ->
                startActivity(new Intent(this, EditBandActivity.class)));

        findViewById(R.id.btnBandLogout).setOnClickListener(v -> {
            // Clear the back stack and return to the login screen
            Intent intent = new Intent(this, AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Stat cards jump straight to the matching My Gigs tab
        findViewById(R.id.cardStatApplied).setOnClickListener(v -> openMyGigs(BandMyGigsActivity.TAB_APPLIED));
        findViewById(R.id.cardStatBooked).setOnClickListener(v -> openMyGigs(BandMyGigsActivity.TAB_BOOKED));
        findViewById(R.id.cardStatSaved).setOnClickListener(v -> openMyGigs(BandMyGigsActivity.TAB_SAVED));

        // Tap-to-preview tracks (mock playback: only one track "plays" at a time)
        trackBoxes = new View[]{findViewById(R.id.boxTrack1), findViewById(R.id.boxTrack2)};
        trackIcons = new ImageView[]{findViewById(R.id.ivTrack1), findViewById(R.id.ivTrack2)};
        trackMetas = new TextView[]{findViewById(R.id.tvTrack1Meta), findViewById(R.id.tvTrack2Meta)};
        findViewById(R.id.rowTrack1).setOnClickListener(v -> toggleTrack(0));
        findViewById(R.id.rowTrack2).setOnClickListener(v -> toggleTrack(1));

        BandNav.setup(this, BandNav.TAB_PROFILE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfile();
        loadStats();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop the mock preview when leaving the screen
        playingTrack = -1;
        renderTracks();
    }

    private void loadProfile() {
        setText(R.id.tvBandName, BandPrefs.get(this, BandPrefs.NAME));
        setText(R.id.tvBandSubtitle, BandPrefs.get(this, BandPrefs.CITY) + " • 48.3k listeners");
        setText(R.id.tvBandBio, BandPrefs.get(this, BandPrefs.BIO));
        setText(R.id.tvBandGear, BandPrefs.get(this, BandPrefs.GEAR));
        setText(R.id.tvBandContact,
                BandPrefs.get(this, BandPrefs.FB) + " • " + BandPrefs.get(this, BandPrefs.PHONE));

        // One chip per comma-separated genre
        ChipGroup chipGroup = findViewById(R.id.chipGroupBandGenres);
        chipGroup.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (String genre : BandPrefs.get(this, BandPrefs.GENRES).split(",")) {
            String label = genre.trim();
            if (label.isEmpty()) continue;
            Chip chip = (Chip) inflater.inflate(R.layout.chip_genre, chipGroup, false);
            chip.setText(label);
            chipGroup.addView(chip);
        }
    }

    private void loadStats() {
        int applied = 0, booked = 0, saved = 0;
        for (BandGig gig : MockDataStore.getInstance().bandGigs) {
            if (gig.getStatus() == BandGig.STATUS_APPLIED) applied++;
            if (gig.getStatus() == BandGig.STATUS_BOOKED) booked++;
            if (gig.isSaved()) saved++;
        }
        setText(R.id.tvStatApplied, String.valueOf(applied));
        setText(R.id.tvStatBooked, String.valueOf(booked));
        setText(R.id.tvStatSaved, String.valueOf(saved));
    }

    private void openMyGigs(int tab) {
        Intent intent = new Intent(this, BandMyGigsActivity.class);
        intent.putExtra(BandMyGigsActivity.EXTRA_TAB, tab);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    private void toggleTrack(int index) {
        playingTrack = (playingTrack == index) ? -1 : index;
        renderTracks();
    }

    private void renderTracks() {
        if (trackIcons == null) return;
        int emerald = ContextCompat.getColor(this, R.color.tugtog_emerald);
        int emeraldDark = ContextCompat.getColor(this, R.color.tugtog_emerald_dark);
        int dark = ContextCompat.getColor(this, R.color.bg_screen_dark);
        int muted = ContextCompat.getColor(this, R.color.text_muted_light);

        for (int i = 0; i < trackIcons.length; i++) {
            boolean playing = i == playingTrack;
            trackIcons[i].setImageResource(playing ? R.drawable.ic_pause : R.drawable.ic_play);
            trackBoxes[i].setBackgroundTintList(ColorStateList.valueOf(playing ? emerald : dark));
            trackMetas[i].setText(playing ? "Now previewing… tap to stop" : TRACK_META[i]);
            trackMetas[i].setTextColor(playing ? emeraldDark : muted);
        }
    }

    private void setText(int viewId, String text) {
        ((TextView) findViewById(viewId)).setText(text);
    }
}
