package com.ph.tugtog;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;
import com.ph.tugtog.model.DiscoverGig;

public class GigDetailActivity extends AppCompatActivity {

    public static final String EXTRA_GIG = "EXTRA_GIG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gig_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DiscoverGig gig = (DiscoverGig) getIntent().getSerializableExtra(EXTRA_GIG);
        if (gig == null) {
            finish();
            return;
        }

        // Hero header
        Chip chipVenueName = findViewById(R.id.chipVenueName);
        TextView tvTitle = findViewById(R.id.tvGigDetailTitle);
        TextView tvAddress = findViewById(R.id.tvGigDetailAddress);

        if (chipVenueName != null) chipVenueName.setText(gig.getVenueName().toUpperCase());
        if (tvTitle != null) tvTitle.setText(gig.getTitle());
        if (tvAddress != null) tvAddress.setText(gig.getVenueAddress());

        // Cover photo
        android.widget.ImageView ivCover = findViewById(R.id.ivGigDetailCover);
        if (ivCover != null) ivCover.setImageResource(gig.getCoverResId());

        // Info rows
        TextView tvDate = findViewById(R.id.tvGigDetailDate);
        TextView tvPay = findViewById(R.id.tvGigDetailPay);
        TextView tvSlots = findViewById(R.id.tvGigDetailSlots);
        TextView tvDesc = findViewById(R.id.tvGigDetailDescription);

        if (tvDate != null) tvDate.setText(gig.getDate());
        if (tvPay != null) tvPay.setText(gig.getPayRate());
        if (tvSlots != null) tvSlots.setText(gig.getCurrentSlots() + " / " + gig.getMaxSlots() + " booked");
        if (tvDesc != null) tvDesc.setText(gig.getDescription());

        // Back
        TextView tvBack = findViewById(R.id.tvBack);
        if (tvBack != null) tvBack.setOnClickListener(v -> finish());

        // Apply CTA — UI only for now
        com.google.android.material.button.MaterialButton btnApply = findViewById(R.id.btnApplyGig);
        if (btnApply != null) {
            btnApply.setOnClickListener(v ->
                    Toast.makeText(this, "Application feature coming soon!", Toast.LENGTH_SHORT).show());
        }
    }
}
