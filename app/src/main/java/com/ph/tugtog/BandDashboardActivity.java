package com.ph.tugtog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ph.tugtog.adapter.DiscoverGigAdapter;
import com.ph.tugtog.model.MockDataStore;

public class BandDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_band_dashboard);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Gig discover feed
        RecyclerView rvDiscoverGigs = findViewById(R.id.rvDiscoverGigs);
        if (rvDiscoverGigs != null) {
            rvDiscoverGigs.setLayoutManager(new LinearLayoutManager(this));
            rvDiscoverGigs.setAdapter(new DiscoverGigAdapter(MockDataStore.getInstance().discoverGigs));
        }

        // Bottom navigation
        LinearLayout navApplied = findViewById(R.id.navApplied);
        LinearLayout navProfile = findViewById(R.id.navProfile);

        if (navApplied != null) {
            navApplied.setOnClickListener(v ->
                    startActivity(new Intent(this, MyApplicationsActivity.class)));
        }

        if (navProfile != null) {
            navProfile.setOnClickListener(v ->
                    startActivity(new Intent(this, BandProfileActivity.class)));
        }
    }
}
