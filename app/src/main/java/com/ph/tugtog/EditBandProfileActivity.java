package com.ph.tugtog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class EditBandProfileActivity extends AppCompatActivity {

    private EditText etBandName;
    private EditText etCity;
    private EditText etListeners;
    private EditText etGenres;
    private EditText etBio;
    private EditText etTechRider;
    private EditText etSpotify;
    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_band_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Cancel button
        TextView tvCancel = findViewById(R.id.tvCancel);
        if (tvCancel != null) {
            tvCancel.setOnClickListener(v -> finish());
        }

        etBandName = findViewById(R.id.etBandName);
        etCity = findViewById(R.id.etCity);
        etListeners = findViewById(R.id.etListeners);
        etGenres = findViewById(R.id.etGenres);
        etBio = findViewById(R.id.etBio);
        etTechRider = findViewById(R.id.etTechRider);
        etSpotify = findViewById(R.id.etSpotify);
        etPhone = findViewById(R.id.etPhone);

        // Save button
        MaterialButton btnSaveProfile = findViewById(R.id.btnSaveProfile);
        if (btnSaveProfile != null) {
            btnSaveProfile.setOnClickListener(v -> {
                saveProfileData();
                Toast.makeText(this, "Profile Saved", Toast.LENGTH_SHORT).show();
                finish();
            });
        }

        loadCurrentData();
    }

    private void loadCurrentData() {
        SharedPreferences prefs = getSharedPreferences("TugtogMockData", Context.MODE_PRIVATE);
        if (etBandName != null) etBandName.setText(prefs.getString("band_name", "Hiraya Collective"));
        if (etCity != null) etCity.setText(prefs.getString("band_city", "Quezon City"));
        if (etListeners != null) etListeners.setText(prefs.getString("band_listeners", "48.3k listeners"));
        if (etGenres != null) etGenres.setText(prefs.getString("band_genres", "OPM Indie, Alt Rock, Math Rock"));
        if (etBio != null) etBio.setText(prefs.getString("band_bio", "5-piece alternative/math-rock band from QC."));
        if (etTechRider != null) etTechRider.setText(prefs.getString("band_tech_rider", "2x Guitar Amps, 1x Bass Rig, 5-pc Drum Kit (own breakables), 3x Vocal Mics."));
        if (etSpotify != null) etSpotify.setText(prefs.getString("band_spotify", "open.spotify.com/artist/hiraya"));
        if (etPhone != null) etPhone.setText(prefs.getString("band_phone", "0917-123-4567"));
    }

    private void saveProfileData() {
        SharedPreferences prefs = getSharedPreferences("TugtogMockData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (etBandName != null) editor.putString("band_name", etBandName.getText().toString());
        if (etCity != null) editor.putString("band_city", etCity.getText().toString());
        if (etListeners != null) editor.putString("band_listeners", etListeners.getText().toString());
        if (etGenres != null) editor.putString("band_genres", etGenres.getText().toString());
        if (etBio != null) editor.putString("band_bio", etBio.getText().toString());
        if (etTechRider != null) editor.putString("band_tech_rider", etTechRider.getText().toString());
        if (etSpotify != null) editor.putString("band_spotify", etSpotify.getText().toString());
        if (etPhone != null) editor.putString("band_phone", etPhone.getText().toString());

        editor.apply();
    }
}
