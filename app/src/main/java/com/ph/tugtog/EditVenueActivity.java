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

public class EditVenueActivity extends AppCompatActivity {

    private EditText etVenueName;
    private EditText etCity;
    private EditText etAddress;
    private EditText etBackline;
    private EditText etFbPage;
    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_venue);

        // Apply window insets to root view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Wire up "Cancel" button to go back
        TextView tvCancel = findViewById(R.id.tvCancel);
        if (tvCancel != null) {
            tvCancel.setOnClickListener(v -> finish());
        }
        
        etVenueName = findViewById(R.id.etVenueName);
        etCity = findViewById(R.id.etCity);
        etAddress = findViewById(R.id.etAddress);
        etBackline = findViewById(R.id.etBackline);
        etFbPage = findViewById(R.id.etFbPage);
        etPhone = findViewById(R.id.etPhone);

        // Wire up Bottom "Save Venue Profile" button
        MaterialButton btnSaveProfile = findViewById(R.id.btnSaveProfile);
        if (btnSaveProfile != null) {
            btnSaveProfile.setOnClickListener(v -> {
                saveProfileData();
                Toast.makeText(this, "Profile Saved", Toast.LENGTH_SHORT).show();
                finish(); // Close screen on save
            });
        }
        
        loadCurrentData();
    }
    
    private void loadCurrentData() {
        SharedPreferences prefs = getSharedPreferences("TugtogMockData", Context.MODE_PRIVATE);
        if(etVenueName != null) etVenueName.setText(prefs.getString("venue_name", "Mow's Bar QC"));
        if(etCity != null) etCity.setText(prefs.getString("venue_city", "Quezon City"));
        if(etAddress != null) etAddress.setText(prefs.getString("venue_address", "Matalino St."));
        if(etBackline != null) etBackline.setText(prefs.getString("venue_backline", "5-pc Drum Kit, Ampeg Bass Combo, Roland JC-120 + Marshall Guitar Amps, 4x Vocal Mics."));
        if(etFbPage != null) etFbPage.setText(prefs.getString("venue_fb", "fb.com/mowsbarqc"));
        if(etPhone != null) etPhone.setText(prefs.getString("venue_phone", "0917-302-9911"));
    }
    
    private void saveProfileData() {
        SharedPreferences prefs = getSharedPreferences("TugtogMockData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        
        if (etVenueName != null) editor.putString("venue_name", etVenueName.getText().toString());
        if (etCity != null) editor.putString("venue_city", etCity.getText().toString());
        if (etAddress != null) editor.putString("venue_address", etAddress.getText().toString());
        if (etBackline != null) editor.putString("venue_backline", etBackline.getText().toString());
        if (etFbPage != null) editor.putString("venue_fb", etFbPage.getText().toString());
        if (etPhone != null) editor.putString("venue_phone", etPhone.getText().toString());
        
        editor.apply();
    }
}
