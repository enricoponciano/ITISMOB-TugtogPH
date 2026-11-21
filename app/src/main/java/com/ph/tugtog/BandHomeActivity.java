package com.ph.tugtog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.Slider;
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
    private TextView tvRadiusBadge;
    private Slider sliderRadius;
    private View mapRadiusOverlay;
    private TextView pinMows, pinSaguijo, pinHive, pin19East;
    private View layoutLocationHeader;
    private ImageView ivLocationToggle;
    private View cardMapPreview;
    private ChipGroup chipGroupGenre;
    private TextView tvResultCount;
    private TextView tvGreeting;
    private RecyclerView rvBandGigs;
    private View emptyState;
    private BandGigAdapter adapter;

    private int currentRadius = 20;

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
        tvRadiusBadge = findViewById(R.id.tvRadiusBadge);
        sliderRadius = findViewById(R.id.sliderRadius);
        mapRadiusOverlay = findViewById(R.id.mapRadiusOverlay);
        pinMows = findViewById(R.id.pinMows);
        pinSaguijo = findViewById(R.id.pinSaguijo);
        pinHive = findViewById(R.id.pinHive);
        pin19East = findViewById(R.id.pin19East);
        
        layoutLocationHeader = findViewById(R.id.layoutLocationHeader);
        ivLocationToggle = findViewById(R.id.ivLocationToggle);
        cardMapPreview = findViewById(R.id.cardMapPreview);
        
        chipGroupGenre = findViewById(R.id.chipGroupGenre);
        tvResultCount = findViewById(R.id.tvResultCount);
        tvGreeting = findViewById(R.id.tvGreeting);
        rvBandGigs = findViewById(R.id.rvBandGigs);
        emptyState = findViewById(R.id.emptyState);

        adapter = new BandGigAdapter(null);
        rvBandGigs.setLayoutManager(new LinearLayoutManager(this));
        rvBandGigs.setAdapter(adapter);

        // Live search as the user types
        TextWatcher searchWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) { applyFilters(); }
        };
        etSearch.addTextChangedListener(searchWatcher);

        // Map Radius Slider
        sliderRadius.addOnChangeListener((slider, value, fromUser) -> {
            currentRadius = (int) value;
            tvRadiusBadge.setText("Within " + currentRadius + " km");
            
            // Scale the map overlay visually (base size 100dp)
            float scale = 0.5f + (value / 50f) * 1.5f;
            mapRadiusOverlay.setScaleX(scale);
            mapRadiusOverlay.setScaleY(scale);
            
            // Fading logic for 19 East which is far away
            if (currentRadius >= 35) {
                pin19East.setAlpha(1.0f);
            } else {
                pin19East.setAlpha(0.5f);
            }
            
            applyFilters();
        });
        
        // Location Toggle Logic
        layoutLocationHeader.setOnClickListener(v -> {
            boolean isExpanded = sliderRadius.getVisibility() == View.VISIBLE;
            if (isExpanded) {
                sliderRadius.setVisibility(View.GONE);
                cardMapPreview.setVisibility(View.GONE);
                ivLocationToggle.setRotation(0);
            } else {
                sliderRadius.setVisibility(View.VISIBLE);
                cardMapPreview.setVisibility(View.VISIBLE);
                ivLocationToggle.setRotation(180);
            }
        });
        
        // Map pins mock filtering
        View.OnClickListener pinClickListener = v -> {
            // Reset all pins visually
            resetPins();
            // Highlight selected pin
            v.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.tugtog_emerald));
            if (v instanceof TextView) {
                ((TextView) v).setTextColor(ContextCompat.getColor(this, R.color.white));
                // Drawable tint update not natively supported on TextView in older APIs, but good enough for prototype
            }
            // Update search bar to venue name
            if (v.getId() == R.id.pinMows) etSearch.setText("Mow's Bar");
            if (v.getId() == R.id.pinSaguijo) etSearch.setText("Saguijo");
            if (v.getId() == R.id.pinHive) etSearch.setText("Hive Stage");
            if (v.getId() == R.id.pin19East) etSearch.setText("19 East");
        };
        
        pinMows.setOnClickListener(pinClickListener);
        pinSaguijo.setOnClickListener(pinClickListener);
        pinHive.setOnClickListener(pinClickListener);
        pin19East.setOnClickListener(pinClickListener);

        // Genre chips
        chipGroupGenre.setOnCheckedStateChangeListener((group, checkedIds) -> applyFilters());

        // Empty state "Clear Filters" resets search + genre
        findViewById(R.id.btnClearFilters).setOnClickListener(v -> {
            etSearch.setText("");
            sliderRadius.setValue(20.0f);
            resetPins();
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

    private void resetPins() {
        TextView[] pins = {pinMows, pinSaguijo, pinHive, pin19East};
        for (TextView pin : pins) {
            if (pin == pinMows) {
                pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.tugtog_emerald));
                pin.setTextColor(ContextCompat.getColor(this, R.color.white));
            } else {
                pin.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.white));
                pin.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
            }
        }
    }

    private void applyFilters() {
        String query = etSearch.getText().toString().trim().toLowerCase(Locale.ROOT);

        String genre = "All";
        Chip checked = findViewById(chipGroupGenre.getCheckedChipId());
        if (checked != null) genre = checked.getText().toString();

        List<BandGig> results = new ArrayList<>();
        for (BandGig gig : MockDataStore.getInstance().bandGigs) {
            boolean matchesGenre = genre.equals("All") || genre.equals("Nearest") || gig.getGenre().equalsIgnoreCase(genre);
            
            String haystack = (gig.getTitle() + " " + gig.getVenue() + " " + gig.getGenre()).toLowerCase(Locale.ROOT);
            boolean matchesQuery = query.isEmpty() || haystack.contains(query);
            
            // Hardcoded mock distance logic
            int mockDistance = 10; // default 10km
            String venue = gig.getVenue().toLowerCase(Locale.ROOT);
            if (venue.contains("mow's")) mockDistance = 5;
            else if (venue.contains("route 196")) mockDistance = 8;
            else if (venue.contains("saguijo")) mockDistance = 22;
            else if (venue.contains("hive stage")) mockDistance = 28;
            else if (venue.contains("19 east")) mockDistance = 35;
            
            boolean matchesDistance = mockDistance <= currentRadius;

            if (matchesGenre && matchesQuery && matchesDistance) results.add(gig);
        }

        adapter.setItems(results);
        tvResultCount.setText(results.size() + (results.size() == 1 ? " GIG FOUND" : " GIGS FOUND"));
        emptyState.setVisibility(results.isEmpty() ? View.VISIBLE : View.GONE);
        rvBandGigs.setVisibility(results.isEmpty() ? View.GONE : View.VISIBLE);
    }
}
