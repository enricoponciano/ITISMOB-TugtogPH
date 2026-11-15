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

import com.ph.tugtog.adapter.MyApplicationAdapter;
import com.ph.tugtog.model.MockDataStore;

public class MyApplicationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_applications);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Applications list
        RecyclerView rvMyApplications = findViewById(R.id.rvMyApplications);
        if (rvMyApplications != null) {
            rvMyApplications.setLayoutManager(new LinearLayoutManager(this));
            rvMyApplications.setAdapter(new MyApplicationAdapter(MockDataStore.getInstance().myApplications));
        }

        // Bottom navigation
        LinearLayout navDiscover = findViewById(R.id.navDiscover);
        LinearLayout navProfile = findViewById(R.id.navProfile);

        if (navDiscover != null) {
            navDiscover.setOnClickListener(v -> {
                startActivity(new Intent(this, BandDashboardActivity.class));
                finish();
            });
        }

        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                startActivity(new Intent(this, BandProfileActivity.class));
                finish();
            });
        }
    }
}
