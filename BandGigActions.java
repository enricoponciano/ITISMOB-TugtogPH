package com.ph.tugtog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ph.tugtog.model.BandGig;

/**
 * Shared Band-side UI behaviour so the gig list and the gig details screen
 * always look and act the same (apply / withdraw / save + their visuals).
 */
public final class BandGigActions {

    private BandGigActions() { }

    /** Apply when not applied yet, or ask to withdraw when already applied. */
    public static void onActionClicked(Context context, BandGig gig, Runnable onChanged) {
        if (gig.getStatus() == BandGig.STATUS_NONE) {
            if (gig.isFull()) {
                Toast.makeText(context, "This gig is already full", Toast.LENGTH_SHORT).show();
                return;
            }
            gig.setStatus(BandGig.STATUS_APPLIED);
            Toast.makeText(context, "Applied to " + gig.getTitle(), Toast.LENGTH_SHORT).show();
            if (onChanged != null) onChanged.run();
        } else if (gig.getStatus() == BandGig.STATUS_APPLIED) {
            new MaterialAlertDialogBuilder(context)
                    .setTitle("Withdraw application?")
                    .setMessage("You will be removed from the applicant list for \""
                            + gig.getTitle() + "\" at " + gig.getVenue() + ".")
                    .setNegativeButton("Keep", null)
                    .setPositiveButton("Withdraw", (dialog, which) -> {
                        gig.setStatus(BandGig.STATUS_NONE);
                        Toast.makeText(context, "Application withdrawn", Toast.LENGTH_SHORT).show();
                        if (onChanged != null) onChanged.run();
                    })
                    .show();
        }
    }

    /** Flip the saved (bookmark) state. */
    public static void toggleSaved(Context context, BandGig gig) {
        gig.setSaved(!gig.isSaved());
        Toast.makeText(context,
                gig.isSaved() ? "Saved to My Gigs" : "Removed from saved",
                Toast.LENGTH_SHORT).show();
    }

    /** Styles the Apply / Withdraw / Booked / Full button for the gig's current state. */
    public static void bindActionButton(MaterialButton button, BandGig gig, boolean longLabels) {
        Context c = button.getContext();
        int bg;
        int text;
        int strokeDp = 0;
        boolean enabled = true;
        String label;

        if (gig.getStatus() == BandGig.STATUS_BOOKED) {
            label = longLabels ? "You're Booked" : "Booked";
            bg = R.color.tugtog_emerald_tint;
            text = R.color.tugtog_emerald_dark;
            enabled = false;
        } else if (gig.getStatus() == BandGig.STATUS_APPLIED) {
            label = longLabels ? "Withdraw Application" : "Withdraw";
            bg = R.color.bg_subtle_light;
            text = R.color.text_primary;
            strokeDp = 1;
        } else if (gig.isFull()) {
            label = longLabels ? "Lineup Full" : "Full";
            bg = R.color.border_light;
            text = R.color.text_muted;
            enabled = false;
        } else {
            label = longLabels ? "Apply for this Gig" : "Apply";
            bg = R.color.tugtog_emerald;
            text = R.color.white;
        }

        button.setText(label);
        button.setEnabled(enabled);
        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(c, bg)));
        button.setTextColor(ContextCompat.getColor(c, text));
        button.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(c, R.color.border_light)));
        button.setStrokeWidth(Math.round(strokeDp * c.getResources().getDisplayMetrics().density));
    }

    /** Shows PENDING / BOOKED on the status pill, or hides it. */
    public static void bindStatusPill(TextView pill, BandGig gig) {
        Context c = pill.getContext();
        if (gig.getStatus() == BandGig.STATUS_APPLIED) {
            pill.setVisibility(View.VISIBLE);
            pill.setText("PENDING");
            pill.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(c, R.color.tugtog_amber)));
        } else if (gig.getStatus() == BandGig.STATUS_BOOKED) {
            pill.setVisibility(View.VISIBLE);
            pill.setText("BOOKED");
            pill.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(c, R.color.tugtog_emerald)));
        } else {
            pill.setVisibility(View.GONE);
        }
    }

    /** Filled emerald bookmark when saved, grey outline when not. */
    public static void bindSaveIcon(ImageView icon, BandGig gig) {
        Context c = icon.getContext();
        icon.setImageResource(gig.isSaved() ? R.drawable.ic_bookmark : R.drawable.ic_bookmark_border);
        icon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(c,
                gig.isSaved() ? R.color.tugtog_emerald : R.color.text_secondary)));
    }
}
