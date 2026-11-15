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
import com.ph.tugtog.GigDetailActivity;
import com.ph.tugtog.R;
import com.ph.tugtog.model.DiscoverGig;

import java.util.List;

public class DiscoverGigAdapter extends RecyclerView.Adapter<DiscoverGigAdapter.ViewHolder> {

    private final List<DiscoverGig> gigs;

    public DiscoverGigAdapter(List<DiscoverGig> gigs) {
        this.gigs = gigs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_discover_gig, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiscoverGig gig = gigs.get(position);

        holder.ivGigCover.setImageResource(gig.getCoverResId());
        holder.tvGigDate.setText(gig.getVenueName().toUpperCase() + " • " + gig.getDate());
        holder.tvGigTitle.setText(gig.getTitle());
        holder.tvGigPay.setText(gig.getPayRate() + " • " + gig.getCurrentSlots() + "/" + gig.getMaxSlots() + " slots");

        if (gig.getDescription() != null && !gig.getDescription().isEmpty()) {
            holder.tvGigDescription.setVisibility(View.VISIBLE);
            holder.tvGigDescription.setText(gig.getDescription());
        } else {
            holder.tvGigDescription.setVisibility(View.GONE);
        }

        // Open gig detail on card or button tap
        View.OnClickListener openDetail = v -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;
            Intent intent = new Intent(v.getContext(), GigDetailActivity.class);
            intent.putExtra(GigDetailActivity.EXTRA_GIG, gigs.get(pos));
            v.getContext().startActivity(intent);
        };

        holder.itemView.setOnClickListener(openDetail);
        holder.btnViewDetails.setOnClickListener(openDetail);
    }

    @Override
    public int getItemCount() {
        return gigs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGigCover;
        TextView tvGigDate, tvGigTitle, tvGigPay, tvGigDescription;
        MaterialButton btnViewDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGigCover = itemView.findViewById(R.id.ivGigCover);
            tvGigDate = itemView.findViewById(R.id.tvGigDate);
            tvGigTitle = itemView.findViewById(R.id.tvGigTitle);
            tvGigPay = itemView.findViewById(R.id.tvGigPay);
            tvGigDescription = itemView.findViewById(R.id.tvGigDescription);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
        }
    }
}
