package com.ph.tugtog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.ph.tugtog.adapter.GigApplicantAdapter;
import com.ph.tugtog.model.GigApplicant;
import com.ph.tugtog.model.MockDataStore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Assuming MainActivity acts as the Organizer Dashboard right now
        setContentView(R.layout.activity_organizer_dashboard);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Setup Mock Data & RecyclerView
        RecyclerView rvGigApplicants = findViewById(R.id.rvGigApplicants);
        if (rvGigApplicants != null) {
            rvGigApplicants.setLayoutManager(new LinearLayoutManager(this));
            
            GigApplicantAdapter adapter = new GigApplicantAdapter(MockDataStore.getInstance().applicants);
            rvGigApplicants.setAdapter(adapter);
        }

        // Set up custom mocked Bottom Navigation links
        LinearLayout navApplicants = findViewById(R.id.navApplicants);
        LinearLayout navPost = findViewById(R.id.navPost);
        LinearLayout navVenue = findViewById(R.id.navVenue);
        
        if (navApplicants != null) {
            navApplicants.setOnClickListener(v -> Toast.makeText(this, "Applicants tab clicked", Toast.LENGTH_SHORT).show());
        }
        
        if (navPost != null) {
            navPost.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, ManageGigsActivity.class));
            });
        }
        
        if (navVenue != null) {
            navVenue.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, VenueProfileActivity.class));
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView rvGigApplicants = findViewById(R.id.rvGigApplicants);
        if (rvGigApplicants != null && rvGigApplicants.getAdapter() != null) {
            rvGigApplicants.getAdapter().notifyDataSetChanged();
        }
    }
}