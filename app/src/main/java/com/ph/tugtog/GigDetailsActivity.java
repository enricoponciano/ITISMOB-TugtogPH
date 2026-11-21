package com.ph.tugtog;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.ph.tugtog.model.BandGig;
import com.ph.tugtog.model.MockDataStore;

import java.util.List;

/**
 * Full gig details for a band: info, house backline, save, and apply / withdraw.
 */
public class GigDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_GIG_INDEX = "GIG_INDEX";

    private BandGig gig;
    private TextView tvGigStatus, tvBarHint;
    private ImageView ivSave;
    private MaterialButton btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gig_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<BandGig> gigs = MockDataStore.getInstance().bandGigs;
        int index = getIntent().getIntExtra(EXTRA_GIG_INDEX, -1);
        if (index < 0 || index >= gigs.size()) {
            finish();
            return;
        }
        gig = gigs.get(index);

        tvGigStatus = findViewById(R.id.tvGigStatus);
        tvBarHint = findViewById(R.id.tvBarHint);
        ivSave = findViewById(R.id.ivSave);
        btnApply = findViewById(R.id.btnApply);

        ((ImageView) findViewById(R.id.ivGigCover)).setImageResource(gig.getCoverResId());
        setText(R.id.tvGigGenre, gig.getGenre().toUpperCase());
        setText(R.id.tvGigMeta, gig.getVenue().toUpperCase() + " • " + gig.getDate());
        setText(R.id.tvGigTitle, gig.getTitle());
        setText(R.id.tvGigLocation, gig.getLocation());
        setText(R.id.tvFactPay, gig.getPayRate());
        setText(R.id.tvFactTime, gig.getTime());
        setText(R.id.tvFactSlots, gig.getBookedSlots() + "/" + gig.getMaxSlots() + " filled");
        setText(R.id.tvGigDescription, gig.getDescription());
        setText(R.id.tvGigBackline, gig.getBackline());
        setText(R.id.tvOrganizerName, gig.getVenue());
        setText(R.id.tvBarPay, gig.getPayRate());

        findViewById(R.id.ivBack).setOnClickListener(v -> finish());

        ivSave.setOnClickListener(v -> {
            BandGigActions.toggleSaved(this, gig);
            render();
        });

        btnApply.setOnClickListener(v -> BandGigActions.onActionClicked(this, gig, this::render));

        render();
    }

    /** Re-draws everything that depends on the applied / saved state. */
    private void render() {
        BandGigActions.bindStatusPill(tvGigStatus, gig);
        BandGigActions.bindSaveIcon(ivSave, gig);
        BandGigActions.bindActionButton(btnApply, gig, true);

        String hint;
        if (gig.getStatus() == BandGig.STATUS_BOOKED) {
            hint = "per set • you're on the lineup";
        } else if (gig.getStatus() == BandGig.STATUS_APPLIED) {
            hint = "per set • waiting for organizer";
        } else if (gig.isFull()) {
            hint = "per set • no slots left";
        } else {
            int left = gig.getSlotsLeft();
            hint = "per set • " + left + (left == 1 ? " slot left" : " slots left");
        }
        tvBarHint.setText(hint);
    }

    private void setText(int viewId, String text) {
        ((TextView) findViewById(viewId)).setText(text);
    }
}
