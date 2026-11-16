package com.ph.tugtog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.ph.tugtog.adapter.BandGigAdapter;
import com.ph.tugtog.model.BandGig;
import com.ph.tugtog.model.MockDataStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Band home screen: browse open gigs, search, filter by genre, save and apply.
 */
public class BandHomeActivity extends AppCompatActivity {

    private EditText etSearch;
    private ChipGroup chipGroupGenre;
    private TextView tvResultCount;
    private TextView tvGreeting;
    private RecyclerView rvBandGigs;
    private View emptyState;
    private BandGigAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_band_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etSearch = findViewById(R.id.etSearch);
        chipGroupGenre = findViewById(R.id.chipGroupGenre);
        tvResultCount = findViewById(R.id.tvResultCount);
        tvGreeting = findViewById(R.id.tvGreeting);
        rvBandGigs = findViewById(R.id.rvBandGigs);
        emptyState = findViewById(R.id.emptyState);

        adapter = new BandGigAdapter(null);
        rvBandGigs.setLayoutManager(new LinearLayoutManager(this));
        rvBandGigs.setAdapter(adapter);

        // Live search as the user types
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) { applyFilters(); }
        });

        // Genre chips
        chipGroupGenre.setOnCheckedStateChangeListener((group, checkedIds) -> applyFilters());

        // Empty state "Clear Filters" resets search + genre
        findViewById(R.id.btnClearFilters).setOnClickListener(v -> {
            etSearch.setText("");
            chipGroupGenre.check(R.id.chipAll);
        });

        // Avatar shortcut to the band profile
        findViewById(R.id.cardHomeAvatar).setOnClickListener(v ->
                BandNav.open(this, BandNav.TAB_GIGS, BandNav.TAB_PROFILE));

        BandNav.setup(this, BandNav.TAB_GIGS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Band name may have been edited, and gig statuses may have changed on other screens
        tvGreeting.setText("HI, " + BandPrefs.get(this, BandPrefs.NAME).toUpperCase(Locale.ROOT));
        applyFilters();
    }

    private void applyFilters() {
        String query = etSearch.getText().toString().trim().toLowerCase(Locale.ROOT);

        String genre = "All";
        Chip checked = findViewById(chipGroupGenre.getCheckedChipId());
        if (checked != null) genre = checked.getText().toString();

        List<BandGig> results = new ArrayList<>();
        for (BandGig gig : MockDataStore.getInstance().bandGigs) {
            boolean matchesGenre = genre.equals("All") || gig.getGenre().equalsIgnoreCase(genre);
            String haystack = (gig.getTitle() + " " + gig.getVenue() + " " + gig.getLocation()
                    + " " + gig.getGenre()).toLowerCase(Locale.ROOT);
            boolean matchesQuery = query.isEmpty() || haystack.contains(query);
            if (matchesGenre && matchesQuery) results.add(gig);
        }

        adapter.setItems(results);
        tvResultCount.setText(results.size() + (results.size() == 1 ? " GIG FOUND" : " GIGS FOUND"));
        emptyState.setVisibility(results.isEmpty() ? View.VISIBLE : View.GONE);
        rvBandGigs.setVisibility(results.isEmpty() ? View.GONE : View.VISIBLE);
    }
}
