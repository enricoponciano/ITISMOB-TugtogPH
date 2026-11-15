package com.ph.tugtog.adapter;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.ph.tugtog.R;
import com.ph.tugtog.model.DiscoverGig;
import com.ph.tugtog.model.MyApplication;

import java.util.List;

public class MyApplicationAdapter extends RecyclerView.Adapter<MyApplicationAdapter.ViewHolder> {

    private final List<MyApplication> applications;

    public MyApplicationAdapter(List<MyApplication> applications) {
        this.applications = applications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_application, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyApplication app = applications.get(position);
        DiscoverGig gig = app.getGig();

        holder.tvAppGigTitle.setText(gig.getTitle());
        holder.tvAppVenue.setText(gig.getVenueName());
        holder.tvAppGigMeta.setText(gig.getDate() + "  •  " + gig.getPayRate());

        // Status chip: emerald = BOOKED, amber = PENDING
        boolean isBooked = app.getStatus() == MyApplication.Status.BOOKED;
        int chipColor = ContextCompat.getColor(holder.itemView.getContext(),
                isBooked ? R.color.tugtog_emerald : R.color.tugtog_amber);
        holder.chipStatus.setChipBackgroundColor(ColorStateList.valueOf(chipColor));
        holder.chipStatus.setText(isBooked ? "BOOKED" : "PENDING");

        holder.btnWithdraw.setOnClickListener(v ->
                Toast.makeText(v.getContext(), "Withdraw feature coming soon!", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Chip chipStatus;
        TextView tvAppVenue, tvAppGigTitle, tvAppGigMeta;
        MaterialButton btnWithdraw;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chipStatus = itemView.findViewById(R.id.chipStatus);
            tvAppVenue = itemView.findViewById(R.id.tvAppVenue);
            tvAppGigTitle = itemView.findViewById(R.id.tvAppGigTitle);
            tvAppGigMeta = itemView.findViewById(R.id.tvAppGigMeta);
            btnWithdraw = itemView.findViewById(R.id.btnWithdraw);
        }
    }
}
