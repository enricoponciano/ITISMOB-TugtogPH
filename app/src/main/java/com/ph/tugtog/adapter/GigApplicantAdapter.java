package com.ph.tugtog.adapter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.ph.tugtog.BandPreviewActivity;
import com.ph.tugtog.R;
import com.ph.tugtog.model.GigApplicant;
import com.ph.tugtog.model.MyGig;

import java.util.List;

public class GigApplicantAdapter extends RecyclerView.Adapter<GigApplicantAdapter.ViewHolder> {

    private List<GigApplicant> applicants;

    public GigApplicantAdapter(List<GigApplicant> applicants) {
        this.applicants = applicants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gig_applicant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GigApplicant applicant = applicants.get(position);
        MyGig gig = applicant.getGig();
        
        holder.tvGigDetails.setText("MOW'S BAR • " + gig.getDate());
        holder.tvGigTitle.setText(gig.getTitle());
        
        String statsText = gig.getPayRate() + " • " + gig.getCurrentBookings() + "/" + gig.getMaxBookings() + " booked";
        holder.tvGigStats.setText(statsText);
        
        holder.tvApplicantName.setText(applicant.getApplicantName());
        
        holder.ivGigCover.setImageResource(gig.getCoverResId());
        holder.ivApplicantAvatar.setImageResource(applicant.getAvatarResId());

        if (applicant.isBooked()) {
            holder.btnBook.setText("Booked");
            holder.btnBook.setEnabled(false);
            holder.btnBook.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(holder.itemView.getContext(), R.color.border_light)
            ));
            holder.btnBook.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.text_muted_light));
            
            holder.tvPass.setEnabled(false);
            holder.tvPass.setAlpha(0.5f);
        } else {
            holder.btnBook.setText("Book");
            holder.btnBook.setEnabled(true);
            holder.btnBook.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(holder.itemView.getContext(), R.color.tugtog_emerald)
            ));
            holder.btnBook.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
            
            holder.tvPass.setEnabled(true);
            holder.tvPass.setAlpha(1.0f);
        }

        holder.btnBook.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition == RecyclerView.NO_POSITION) return;
            GigApplicant currentApp = applicants.get(currentPosition);
            MyGig currentGig = currentApp.getGig();

            if (currentGig.getCurrentBookings() < currentGig.getMaxBookings()) {
                currentGig.setCurrentBookings(currentGig.getCurrentBookings() + 1);
                currentApp.setBooked(true);
                // Update this item
                notifyItemChanged(currentPosition);
                // We should also update any other items that share the SAME gig so their stats update
                for(int i = 0; i < applicants.size(); i++) {
                    if(applicants.get(i).getGig() == currentGig && i != currentPosition) {
                        notifyItemChanged(i);
                    }
                }
                
                Toast.makeText(v.getContext(), "Booked " + currentApp.getApplicantName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "Booking limit reached! (Max " + currentGig.getMaxBookings() + ")", Toast.LENGTH_SHORT).show();
            }
        });

        holder.tvPass.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition == RecyclerView.NO_POSITION) return;
            
            GigApplicant currentApp = applicants.get(currentPosition);
            applicants.remove(currentPosition);
            notifyItemRemoved(currentPosition);
            // Refresh remaining items in case of index shifts
            notifyItemRangeChanged(currentPosition, applicants.size());
            Toast.makeText(v.getContext(), "Passed on " + currentApp.getApplicantName(), Toast.LENGTH_SHORT).show();
        });
        
        // Make the "View Profile" button and Avatar clickable to launch BandPreviewActivity
        View.OnClickListener previewClickListener = v -> {
            Intent intent = new Intent(v.getContext(), BandPreviewActivity.class);
            v.getContext().startActivity(intent);
        };
        
        holder.tvViewProfile.setOnClickListener(previewClickListener);
        holder.ivApplicantAvatar.setOnClickListener(previewClickListener);
    }

    @Override
    public int getItemCount() {
        return applicants.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGigDetails, tvGigTitle, tvGigStats, tvApplicantName, tvPass, tvViewProfile;
        ImageView ivGigCover, ivApplicantAvatar;
        MaterialButton btnBook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGigDetails = itemView.findViewById(R.id.tvGigDetails);
            tvGigTitle = itemView.findViewById(R.id.tvGigTitle);
            tvGigStats = itemView.findViewById(R.id.tvGigStats);
            tvApplicantName = itemView.findViewById(R.id.tvApplicantName);
            tvPass = itemView.findViewById(R.id.tvPass);
            tvViewProfile = itemView.findViewById(R.id.tvViewProfile);
            ivGigCover = itemView.findViewById(R.id.ivGigCover);
            ivApplicantAvatar = itemView.findViewById(R.id.ivApplicantAvatar);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}
