package com.ph.tugtog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.content.SharedPreferences;
import android.content.Context;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.button.MaterialButton;

public class VenueProfileActivity extends AppCompatActivity {

    private TextView tvVenueName;
    private TextView tvVenueAddress;
    private TextView tvHouseBackline;
    private TextView tvBookingContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_venue_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvVenueName = findViewById(R.id.tvVenueName);
        tvVenueAddress = findViewById(R.id.tvVenueAddress);
        tvHouseBackline = findViewById(R.id.tvHouseBackline);
        tvBookingContact = findViewById(R.id.tvBookingContact);
        
        MaterialButton btnEditProfile = findViewById(R.id.btnEditProfile);
        if (btnEditProfile != null) {
            btnEditProfile.setOnClickListener(v -> {
                startActivity(new Intent(VenueProfileActivity.this, EditVenueActivity.class));
            });
        }
        
        MaterialButton btnLogout = findViewById(R.id.btnLogout);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                // Clear backstack and go to AuthActivity
                Intent intent = new Intent(VenueProfileActivity.this, AuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }
        
        // Handle mock bottom nav
        LinearLayout navApplicants = findViewById(R.id.navApplicants);
        if(navApplicants != null) {
            navApplicants.setOnClickListener(v -> {
                startActivity(new Intent(VenueProfileActivity.this, MainActivity.class));
                finish();
            });
        }
        
        LinearLayout navPost = findViewById(R.id.navPost);
        if(navPost != null) {
            navPost.setOnClickListener(v -> {
                startActivity(new Intent(VenueProfileActivity.this, ManageGigsActivity.class));
                finish();
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load data from shared preferences for interactivity
        SharedPreferences prefs = getSharedPreferences("TugtogMockData", Context.MODE_PRIVATE);
        
        if (tvVenueName != null) {
            tvVenueName.setText(prefs.getString("venue_name", "Mow's Bar QC"));
        }
        
        if (tvVenueAddress != null) {
            String address = prefs.getString("venue_address", "Matalino St.") + ", " + prefs.getString("venue_city", "Quezon City");
            tvVenueAddress.setText(address);
        }
        
        if (tvHouseBackline != null) {
            tvHouseBackline.setText(prefs.getString("venue_backline", "5-pc Drum Kit, Ampeg Bass Combo, Roland JC-120 + Marshall Guitar Amps, 4x Vocal Mics."));
        }
        
        if (tvBookingContact != null) {
            String contact = prefs.getString("venue_fb", "fb.com/mowsbarqc") + " • " + prefs.getString("venue_phone", "0917-302-9911");
            tvBookingContact.setText(contact);
        }
    }
}
