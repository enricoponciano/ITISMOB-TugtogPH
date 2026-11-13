package com.ph.tugtog;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.ph.tugtog.adapter.ManageGigsAdapter;
import com.ph.tugtog.model.MockDataStore;
import com.ph.tugtog.model.MyGig;

import java.util.ArrayList;
import java.util.List;

public class ManageGigsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_gigs);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Top button creates a post
        MaterialButton btnCreatePost = findViewById(R.id.btnCreatePost);
        if (btnCreatePost != null) {
            btnCreatePost.setOnClickListener(v -> {
                startActivity(new Intent(this, PostGigActivity.class));
            });
        }

        // RecyclerView
        RecyclerView rvMyGigs = findViewById(R.id.rvMyGigs);
        if (rvMyGigs != null) {
            rvMyGigs.setLayoutManager(new LinearLayoutManager(this));
            
            ManageGigsAdapter adapter = new ManageGigsAdapter(MockDataStore.getInstance().myGigs);
            rvMyGigs.setAdapter(adapter);
        }

        // Bottom Navigation
        LinearLayout navApplicants = findViewById(R.id.navApplicants);
        LinearLayout navPost = findViewById(R.id.navPost);
        LinearLayout navVenue = findViewById(R.id.navVenue);
        
        if (navApplicants != null) {
            navApplicants.setOnClickListener(v -> {
                startActivity(new Intent(ManageGigsActivity.this, MainActivity.class));
            });
        }
        
        if (navVenue != null) {
            navVenue.setOnClickListener(v -> {
                startActivity(new Intent(ManageGigsActivity.this, VenueProfileActivity.class));
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView rvMyGigs = findViewById(R.id.rvMyGigs);
        if (rvMyGigs != null && rvMyGigs.getAdapter() != null) {
            rvMyGigs.getAdapter().notifyDataSetChanged();
        }
    }
}
