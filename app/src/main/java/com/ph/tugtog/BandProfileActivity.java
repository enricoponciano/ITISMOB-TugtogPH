package com.ph.tugtog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;

public class BandProfileActivity extends AppCompatActivity {

    private TextView tvBandName;
    private TextView tvBandLocation;
    private TextView tvBandBio;
    private TextView tvTechRiderGear;
    private Chip chipGenre1;
    private Chip chipGenre2;
    private Chip chipGenre3;

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

        tvBandName = findViewById(R.id.tvBandName);
        tvBandLocation = findViewById(R.id.tvBandLocation);
        tvBandBio = findViewById(R.id.tvBandBio);
        tvTechRiderGear = findViewById(R.id.tvTechRiderGear);
        chipGenre1 = findViewById(R.id.chipGenre1);
        chipGenre2 = findViewById(R.id.chipGenre2);
        chipGenre3 = findViewById(R.id.chipGenre3);

        // Edit Profile
        MaterialButton btnEdit = findViewById(R.id.btnEditBandProfile);
        if (btnEdit != null) {
            btnEdit.setOnClickListener(v ->
                    startActivity(new Intent(BandProfileActivity.this, EditBandProfileActivity.class)));
        }

        // Log Out
        MaterialButton btnLogout = findViewById(R.id.btnBandLogout);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                Intent intent = new Intent(this, AuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }

        // Bottom navigation
        LinearLayout navDiscover = findViewById(R.id.navDiscover);
        LinearLayout navApplied = findViewById(R.id.navApplied);

        if (navDiscover != null) {
            navDiscover.setOnClickListener(v -> {
                startActivity(new Intent(this, BandDashboardActivity.class));
                finish();
            });
        }

        if (navApplied != null) {
            navApplied.setOnClickListener(v -> {
                startActivity(new Intent(this, MyApplicationsActivity.class));
                finish();
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfileData();
    }

    private void loadProfileData() {
        SharedPreferences prefs = getSharedPreferences("TugtogMockData", Context.MODE_PRIVATE);

        if (tvBandName != null) {
            tvBandName.setText(prefs.getString("band_name", "Hiraya Collective"));
        }

        if (tvBandLocation != null) {
            String city = prefs.getString("band_city", "Quezon City");
            String listeners = prefs.getString("band_listeners", "48.3k listeners");
            tvBandLocation.setText(city + " • " + listeners);
        }

        if (tvBandBio != null) {
            tvBandBio.setText(prefs.getString("band_bio", "5-piece alternative/math-rock band from QC."));
        }

        if (tvTechRiderGear != null) {
            tvTechRiderGear.setText(prefs.getString("band_tech_rider", "2x Guitar Amps, 1x Bass Rig, 5-pc Drum Kit (own breakables), 3x Vocal Mics."));
        }

        // Update genres
        String genresStr = prefs.getString("band_genres", "OPM Indie, Alt Rock, Math Rock");
        String[] genres = genresStr.split(",");
        Chip[] chips = new Chip[]{chipGenre1, chipGenre2, chipGenre3};
        for (int i = 0; i < chips.length; i++) {
            if (chips[i] != null) {
                if (i < genres.length && !genres[i].trim().isEmpty()) {
                    chips[i].setVisibility(View.VISIBLE);
                    chips[i].setText(genres[i].trim());
                } else {
                    chips[i].setVisibility(View.GONE);
                }
            }
        }
    }
}
