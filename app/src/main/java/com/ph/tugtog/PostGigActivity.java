package com.ph.tugtog;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.ph.tugtog.model.MockDataStore;
import com.ph.tugtog.model.MyGig;

public class PostGigActivity extends AppCompatActivity {

    private EditText etTitle, etDate, etPay, etMaxBands, etDescription;
    private int editIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_post_gig);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        etTitle = findViewById(R.id.etTitle);
        etDate = findViewById(R.id.etDate);
        etPay = findViewById(R.id.etPay);
        etMaxBands = findViewById(R.id.etMaxBands);
        etDescription = findViewById(R.id.etDescription);
        
        editIndex = getIntent().getIntExtra("EDIT_INDEX", -1);
        
        // Find cancel button and set it to close activity
        TextView tvCancel = findViewById(R.id.tvCancel);
        if (tvCancel != null) {
            tvCancel.setOnClickListener(v -> finish());
        }
        
        MaterialButton btnSubmitPost = findViewById(R.id.btnSubmitPost);
        
        if (editIndex != -1) {
            // Edit mode: prepopulate data
            MyGig gigToEdit = MockDataStore.getInstance().myGigs.get(editIndex);
            
            if (etTitle != null) etTitle.setText(gigToEdit.getTitle());
            if (etDate != null) etDate.setText(gigToEdit.getDate());
            if (etPay != null) etPay.setText(gigToEdit.getPayRate().replace("₱", "").replace(",", "").trim());
            if (etMaxBands != null) etMaxBands.setText(String.valueOf(gigToEdit.getMaxBookings()));
            if (etDescription != null) etDescription.setText(gigToEdit.getDescription());
            
            TextView tvTopHeading = findViewById(R.id.tvTopHeading);
            if (tvTopHeading != null) tvTopHeading.setText("Edit Gig");
            
            if (btnSubmitPost != null) btnSubmitPost.setText("Save Changes");
        }
        
        if (btnSubmitPost != null) {
            btnSubmitPost.setOnClickListener(v -> submitPost());
        }
    }
    
    private void submitPost() {
        String title = etTitle != null && etTitle.getText() != null ? etTitle.getText().toString() : "New Gig Post";
        if(title.trim().isEmpty()) title = "New Gig Post";
        
        String date = etDate != null && etDate.getText() != null ? etDate.getText().toString() : "TBD";
        if(date.trim().isEmpty()) date = "TBD";
        
        String pay = etPay != null && etPay.getText() != null ? "₱" + etPay.getText().toString() : "₱TBD";
        if(pay.equals("₱")) pay = "₱TBD";
        
        int max = 4;
        if(etMaxBands != null && etMaxBands.getText() != null) {
            try { max = Integer.parseInt(etMaxBands.getText().toString()); } 
            catch(Exception e) { /* default to 4 */ }
        }
        
        String desc = etDescription != null && etDescription.getText() != null ? etDescription.getText().toString() : "Come play!";
        if(desc.trim().isEmpty()) desc = "Come play!";
        
        if (editIndex != -1) {
            // Update existing gig
            MyGig gigToEdit = MockDataStore.getInstance().myGigs.get(editIndex);
            gigToEdit.setTitle(title);
            gigToEdit.setDate(date);
            gigToEdit.setPayRate(pay);
            gigToEdit.setMaxBookings(max);
            gigToEdit.setDescription(desc);
            Toast.makeText(this, "Gig Updated Successfully!", Toast.LENGTH_SHORT).show();
        } else {
            // Create new gig
            MyGig newGig = new MyGig(title, date, pay, 0, max, R.drawable.auth_background, desc);
            MockDataStore.getInstance().myGigs.add(0, newGig); // Add to top of list
            Toast.makeText(this, "Gig Posted Successfully!", Toast.LENGTH_SHORT).show();
        }
        
        finish();
    }
}
