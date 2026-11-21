package com.ph.tugtog.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.ph.tugtog.BandGigActions;
import com.ph.tugtog.GigDetailsActivity;
import com.ph.tugtog.R;
import com.ph.tugtog.model.BandGig;
import com.ph.tugtog.model.MockDataStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Gig cards for the Band side. Used by both Find Gigs and My Gigs.
 */
public class BandGigAdapter extends RecyclerView.Adapter<BandGigAdapter.ViewHolder> {

    private final List<BandGig> gigs = new ArrayList<>();
    private final Runnable onDataChanged;

    /**
     * @param onDataChanged called after a gig is applied to, withdrawn from, saved or unsaved,
     *                      so the screen can refresh its counts / filtered list.
     */
    public BandGigAdapter(Runnable onDataChanged) {
        this.onDataChanged = onDataChanged;
    }

    public void setItems(List<BandGig> newGigs) {
        gigs.clear();
        gigs.addAll(newGigs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_band_gig, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BandGig gig = gigs.get(position);

        holder.ivGigCover.setImageResource(gig.getCoverResId());
        holder.tvGigGenre.setText(gig.getGenre().toUpperCase());
        holder.tvGigMeta.setText(gig.getVenue().toUpperCase() + " • " + gig.getDate() + " • " + gig.getTime());
        holder.tvGigTitle.setText(gig.getTitle());
        holder.tvGigLocation.setText(gig.getLocation());
        holder.tvGigPay.setText(gig.getPayRate());
        holder.tvGigSlots.setText(gig.isFull()
                ? "Lineup full"
                : gig.getBookedSlots() + "/" + gig.getMaxSlots() + " slots filled");

        BandGigActions.bindStatusPill(holder.tvGigStatus, gig);
        BandGigActions.bindSaveIcon(holder.ivSave, gig);
        BandGigActions.bindActionButton(holder.btnApply, gig, false);

        // Apply / Withdraw
        holder.btnApply.setOnClickListener(v ->
                BandGigActions.onActionClicked(v.getContext(), gig, () -> refresh(holder)));

        // Bookmark toggle
        holder.ivSave.setOnClickListener(v -> {
            BandGigActions.toggleSaved(v.getContext(), gig);
            refresh(holder);
        });

        // Tap the card to open the full gig details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), GigDetailsActivity.class);
            intent.putExtra(GigDetailsActivity.EXTRA_GIG_INDEX,
                    MockDataStore.getInstance().bandGigs.indexOf(gig));
            v.getContext().startActivity(intent);
        });
    }

    private void refresh(ViewHolder holder) {
        int position = holder.getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) notifyItemChanged(position);
        if (onDataChanged != null) onDataChanged.run();
    }

    @Override
    public int getItemCount() {
        return gigs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGigCover, ivSave;
        TextView tvGigGenre, tvGigStatus, tvGigMeta, tvGigTitle, tvGigLocation, tvGigPay, tvGigSlots;
        MaterialButton btnApply;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGigCover = itemView.findViewById(R.id.ivGigCover);
            ivSave = itemView.findViewById(R.id.ivSave);
            tvGigGenre = itemView.findViewById(R.id.tvGigGenre);
            tvGigStatus = itemView.findViewById(R.id.tvGigStatus);
            tvGigMeta = itemView.findViewById(R.id.tvGigMeta);
            tvGigTitle = itemView.findViewById(R.id.tvGigTitle);
            tvGigLocation = itemView.findViewById(R.id.tvGigLocation);
            tvGigPay = itemView.findViewById(R.id.tvGigPay);
            tvGigSlots = itemView.findViewById(R.id.tvGigSlots);
            btnApply = itemView.findViewById(R.id.btnApply);
        }
    }
}
