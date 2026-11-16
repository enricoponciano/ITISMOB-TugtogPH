package com.ph.tugtog;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/**
 * Wires up the shared Band bottom navigation (view_band_bottom_nav.xml).
 * Find Gigs stays at the bottom of the back stack; the other tabs sit on top of it.
 */
public final class BandNav {

    public static final int TAB_GIGS = 0;
    public static final int TAB_MY_GIGS = 1;
    public static final int TAB_PROFILE = 2;

    private BandNav() { }

    public static void setup(AppCompatActivity activity, int activeTab) {
        bind(activity, activeTab, TAB_GIGS, R.id.navBandGigs, R.id.ivNavBandGigs, R.id.tvNavBandGigs);
        bind(activity, activeTab, TAB_MY_GIGS, R.id.navBandMyGigs, R.id.ivNavBandMyGigs, R.id.tvNavBandMyGigs);
        bind(activity, activeTab, TAB_PROFILE, R.id.navBandProfile, R.id.ivNavBandProfile, R.id.tvNavBandProfile);
    }

    private static void bind(AppCompatActivity activity, int activeTab, int tab,
                             int itemId, int iconId, int labelId) {
        View item = activity.findViewById(itemId);
        ImageView icon = activity.findViewById(iconId);
        TextView label = activity.findViewById(labelId);
        if (item == null || icon == null || label == null) return;

        int color = ContextCompat.getColor(activity,
                tab == activeTab ? R.color.tugtog_emerald : R.color.text_muted);
        icon.setImageTintList(ColorStateList.valueOf(color));
        label.setTextColor(color);

        item.setOnClickListener(v -> {
            if (tab != activeTab) open(activity, activeTab, tab);
        });
    }

    /** Switches tabs without piling screens onto the back stack. */
    public static void open(AppCompatActivity activity, int fromTab, int toTab) {
        if (toTab == TAB_GIGS) {
            Intent intent = new Intent(activity, BandHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent);
        } else {
            Class<?> target = toTab == TAB_MY_GIGS ? BandMyGigsActivity.class : BandProfileActivity.class;
            activity.startActivity(new Intent(activity, target));
            if (fromTab != TAB_GIGS) activity.finish();
        }
        activity.overridePendingTransition(0, 0);
    }
}
