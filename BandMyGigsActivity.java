package com.ph.tugtog;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.ph.tugtog.adapter.BandGigAdapter;
import com.ph.tugtog.model.BandGig;
import com.ph.tugtog.model.MockDataStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Band "My Gigs": applications waiting on the organizer, confirmed bookings, and saved gigs.
 */
public class BandMyGigsActivity extends AppCompatActivity {

    public static final String EXTRA_TAB = "TAB";
    public static final int TAB_APPLIED = 0;
    public static final int TAB_BOOKED = 1;
    public static final int TAB_SAVED = 2;

    private int currentTab = TAB_APPLIED;

    private MaterialButton btnTabApplied, btnTabBooked, btnTabSaved;
    private RecyclerView rvMyBandGigs;
    private View emptyState;
    private TextView tvEmptyTitle, tvEmptyBody;
    private BandGigAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_band_my_gigs);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnTabApplied = findViewById(R.id.btnTabApplied);
        btnTabBooked = findViewById(R.id.btnTabBooked);
        btnTabSaved = findViewById(R.id.btnTabSaved);
        rvMyBandGigs = findViewById(R.id.rvMyBandGigs);
        emptyState = findViewById(R.id.emptyState);
        tvEmptyTitle = findViewById(R.id.tvEmptyTitle);
        tvEmptyBody = findViewById(R.id.tvEmptyBody);

        currentTab = savedInstanceState != null
                ? savedInstanceState.getInt(EXTRA_TAB, TAB_APPLIED)
                : getIntent().getIntExtra(EXTRA_TAB, TAB_APPLIED);

        // Refresh the list + tab counts whenever a card is applied / withdrawn / unsaved
        adapter = new BandGigAdapter(this::render);
        rvMyBandGigs.setLayoutManager(new LinearLayoutManager(this));
        rvMyBandGigs.setAdapter(adapter);

        btnTabApplied.setOnClickListener(v -> selectTab(TAB_APPLIED));
        btnTabBooked.setOnClickListener(v -> selectTab(TAB_BOOKED));
        btnTabSaved.setOnClickListener(v -> selectTab(TAB_SAVED));

        findViewById(R.id.btnBrowseGigs).setOnClickListener(v ->
                BandNav.open(this, BandNav.TAB_MY_GIGS, BandNav.TAB_GIGS));

        BandNav.setup(this, BandNav.TAB_MY_GIGS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        render();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_TAB, currentTab);
    }

    private void selectTab(int tab) {
        currentTab = tab;
        render();
    }

    private boolean belongsTo(BandGig gig, int tab) {
        if (tab == TAB_APPLIED) return gig.getStatus() == BandGig.STATUS_APPLIED;
        if (tab == TAB_BOOKED) return gig.getStatus() == BandGig.STATUS_BOOKED;
        return gig.isSaved();
    }

    private void render() {
        int[] counts = new int[3];
        List<BandGig> visible = new ArrayList<>();
        for (BandGig gig : MockDataStore.getInstance().bandGigs) {
            for (int tab = 0; tab < 3; tab++) {
                if (belongsTo(gig, tab)) counts[tab]++;
            }
            if (belongsTo(gig, currentTab)) visible.add(gig);
        }

        styleTab(btnTabApplied, "Applied (" + counts[TAB_APPLIED] + ")", currentTab == TAB_APPLIED);
        styleTab(btnTabBooked, "Booked (" + counts[TAB_BOOKED] + ")", currentTab == TAB_BOOKED);
        styleTab(btnTabSaved, "Saved (" + counts[TAB_SAVED] + ")", currentTab == TAB_SAVED);

        adapter.setItems(visible);
        emptyState.setVisibility(visible.isEmpty() ? View.VISIBLE : View.GONE);
        rvMyBandGigs.setVisibility(visible.isEmpty() ? View.GONE : View.VISIBLE);

        if (currentTab == TAB_APPLIED) {
            tvEmptyTitle.setText("No pending applications");
            tvEmptyBody.setText("Gigs you apply to will wait here until the organizer responds.");
        } else if (currentTab == TAB_BOOKED) {
            tvEmptyTitle.setText("No confirmed bookings yet");
            tvEmptyBody.setText("Once an organizer books you, the gig moves here.");
        } else {
            tvEmptyTitle.setText("Nothing saved yet");
            tvEmptyBody.setText("Tap the bookmark on any gig to keep it for later.");
        }
    }

    private void styleTab(MaterialButton button, String label, boolean selected) {
        button.setText(label);
        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,
                selected ? R.color.tugtog_emerald : R.color.bg_subtle_light)));
        button.setTextColor(ContextCompat.getColor(this,
                selected ? R.color.white : R.color.text_secondary));
    }
}
