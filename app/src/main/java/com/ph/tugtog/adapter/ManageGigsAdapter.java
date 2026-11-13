package com.ph.tugtog.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.ph.tugtog.PostGigActivity;
import com.ph.tugtog.R;
import com.ph.tugtog.model.MyGig;

import java.util.List;

public class ManageGigsAdapter extends RecyclerView.Adapter<ManageGigsAdapter.ViewHolder> {

    private List<MyGig> gigs;

    public ManageGigsAdapter(List<MyGig> gigs) {
        this.gigs = gigs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage_gig, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyGig gig = gigs.get(position);
        holder.tvGigTitle.setText(gig.getTitle());
        holder.tvGigDate.setText(gig.getDate());
        String statsText = gig.getPayRate() + " • " + gig.getCurrentBookings() + "/" + gig.getMaxBookings() + " booked";
        holder.tvGigStats.setText(statsText);
        holder.ivGigCover.setImageResource(gig.getCoverResId());
        
        if (gig.getDescription() != null && !gig.getDescription().isEmpty()) {
            holder.tvGigDescription.setVisibility(View.VISIBLE);
            holder.tvGigDescription.setText(gig.getDescription());
        } else {
            holder.tvGigDescription.setVisibility(View.GONE);
        }

        holder.btnEdit.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(v.getContext(), PostGigActivity.class);
                intent.putExtra("EDIT_INDEX", currentPosition);
                v.getContext().startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                gigs.remove(currentPosition);
                notifyItemRemoved(currentPosition);
                notifyItemRangeChanged(currentPosition, gigs.size());
                Toast.makeText(v.getContext(), "Post deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return gigs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGigTitle, tvGigDate, tvGigStats, tvGigDescription;
        ImageView ivGigCover;
        MaterialButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGigTitle = itemView.findViewById(R.id.tvGigTitle);
            tvGigDate = itemView.findViewById(R.id.tvGigDate);
            tvGigStats = itemView.findViewById(R.id.tvGigStats);
            tvGigDescription = itemView.findViewById(R.id.tvGigDescription);
            ivGigCover = itemView.findViewById(R.id.ivGigCover);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
