package com.ph.tugtog;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

/**
 * AuthActivity (Java)
 * Handles Log In and Sign Up for both Band and Organizer roles in Light Mode.
 * Uses the shared colors.xml, themes.xml, and vector drawables.
 */
public class AuthActivity extends AppCompatActivity {

    // UI State Flags
    private boolean isLoginMode = true; // true = Log In, false = Sign Up
    private boolean isBandRole = true;  // true = Band, false = Organizer

    // Mode Toggle Buttons
    private MaterialButton btnModeLogin;
    private MaterialButton btnModeSignup;

    // Role Selection Cards, Icon Containers & Vector Icons
    private MaterialCardView cardRoleBand;
    private MaterialCardView cardRoleOrganizer;
    private FrameLayout iconBoxBand;
    private FrameLayout iconBoxOrganizer;
    private ImageView ivIconBand;
    private ImageView ivIconOrganizer;

    // Form Fields & Containers
    private LinearLayout layoutAccountName;
    private TextView tvNameLabel;
    private TextInputEditText etAccountName;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;

    // Spotify Link Option (Band Sign Up only)
    private MaterialCardView cardSpotifyLink;
    private CheckBox cbSpotifyLink;

    // Primary Submit & Demo Buttons
    private MaterialButton btnSubmitAuth;
    private MaterialButton btnDemoBand;
    private MaterialButton btnDemoOrganizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Force Light Mode across the entire app even if the phone is in Dark Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        bindViews();
        setupListeners();
        updateAuthUI();
    }

    /**
     * Binds all XML views from activity_auth.xml
     */
    private void bindViews() {
        btnModeLogin = findViewById(R.id.btnModeLogin);
        btnModeSignup = findViewById(R.id.btnModeSignup);

        cardRoleBand = findViewById(R.id.cardRoleBand);
        cardRoleOrganizer = findViewById(R.id.cardRoleOrganizer);
        iconBoxBand = findViewById(R.id.iconBoxBand);
        iconBoxOrganizer = findViewById(R.id.iconBoxOrganizer);
        ivIconBand = findViewById(R.id.ivIconBand);
        ivIconOrganizer = findViewById(R.id.ivIconOrganizer);

        layoutAccountName = findViewById(R.id.layoutAccountName);
        tvNameLabel = findViewById(R.id.tvNameLabel);
        etAccountName = findViewById(R.id.etAccountName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        cardSpotifyLink = findViewById(R.id.cardSpotifyLink);
        cbSpotifyLink = findViewById(R.id.cbSpotifyLink);

        btnSubmitAuth = findViewById(R.id.btnSubmitAuth);
        btnDemoBand = findViewById(R.id.btnDemoBand);
        btnDemoOrganizer = findViewById(R.id.btnDemoOrganizer);
    }

    /**
     * Attaches click listeners for mode switching, role selection, and form submission.
     */
    private void setupListeners() {
        // 1. Switch between Log In and Sign Up
        btnModeLogin.setOnClickListener(v -> {
            isLoginMode = true;
            updateAuthUI();
        });

        btnModeSignup.setOnClickListener(v -> {
            isLoginMode = false;
            updateAuthUI();
        });

        // 2. Switch between Band and Organizer account types
        cardRoleBand.setOnClickListener(v -> {
            isBandRole = true;
            updateAuthUI();
        });

        cardRoleOrganizer.setOnClickListener(v -> {
            isBandRole = false;
            updateAuthUI();
        });

        // 3. Toggle Spotify Checkbox when clicking anywhere on the Spotify card
        cardSpotifyLink.setOnClickListener(v -> {
            cbSpotifyLink.setChecked(!cbSpotifyLink.isChecked());
        });

        // 4. Handle Primary Auth Submit Button
        btnSubmitAuth.setOnClickListener(v -> handleAuthSubmit());

        // 5. Quick 1-Tap Demo Access Buttons for testing/presentations
        btnDemoBand.setOnClickListener(v -> {
            isBandRole = true;
            updateAuthUI();
            proceedToMainScreen("Hiraya Collective (Demo Band)");
        });

        btnDemoOrganizer.setOnClickListener(v -> {
            isBandRole = false;
            updateAuthUI();
            proceedToMainScreen("Mow's Bar QC (Demo Organizer)");
        });
    }

    /**
     * Synchronizes colors, icon tints, labels, and field visibility based on isLoginMode and isBandRole.
     */
    private void updateAuthUI() {
        int emerald = ContextCompat.getColor(this, R.color.tugtog_emerald);
        int emeraldTint = ContextCompat.getColor(this, R.color.tugtog_emerald_tint);
        int amber = ContextCompat.getColor(this, R.color.tugtog_amber);
        int amberTint = ContextCompat.getColor(this, R.color.tugtog_amber_tint);
        int cardLight = ContextCompat.getColor(this, R.color.bg_card_light);
        int subtleLight = ContextCompat.getColor(this, R.color.bg_subtle_light);
        int borderLight = ContextCompat.getColor(this, R.color.border_light);
        int white = ContextCompat.getColor(this, R.color.white);
        int textSecondary = ContextCompat.getColor(this, R.color.text_secondary);

        // A. Update Log In / Sign Up Segmented Toggle
        if (isLoginMode) {
            btnModeLogin.setBackgroundTintList(ColorStateList.valueOf(emerald));
            btnModeLogin.setTextColor(white);
            btnModeSignup.setBackgroundTintList(ColorStateList.valueOf(subtleLight));
            btnModeSignup.setTextColor(textSecondary);
        } else {
            btnModeSignup.setBackgroundTintList(ColorStateList.valueOf(emerald));
            btnModeSignup.setTextColor(white);
            btnModeLogin.setBackgroundTintList(ColorStateList.valueOf(subtleLight));
            btnModeLogin.setTextColor(textSecondary);
        }

        // B. Update Band vs Organizer Role Cards & Rounded Icon Boxes
        if (isBandRole) {
            cardRoleBand.setCardBackgroundColor(emeraldTint);
            cardRoleBand.setStrokeColor(emerald);
            cardRoleBand.setStrokeWidth(dpToPx(2));
            iconBoxBand.setBackgroundTintList(ColorStateList.valueOf(emerald));
            ivIconBand.setImageTintList(ColorStateList.valueOf(white));

            cardRoleOrganizer.setCardBackgroundColor(cardLight);
            cardRoleOrganizer.setStrokeColor(borderLight);
            cardRoleOrganizer.setStrokeWidth(dpToPx(1));
            iconBoxOrganizer.setBackgroundTintList(ColorStateList.valueOf(subtleLight));
            ivIconOrganizer.setImageTintList(ColorStateList.valueOf(textSecondary));
        } else {
            cardRoleOrganizer.setCardBackgroundColor(amberTint);
            cardRoleOrganizer.setStrokeColor(amber);
            cardRoleOrganizer.setStrokeWidth(dpToPx(2));
            iconBoxOrganizer.setBackgroundTintList(ColorStateList.valueOf(amber));
            ivIconOrganizer.setImageTintList(ColorStateList.valueOf(white));

            cardRoleBand.setCardBackgroundColor(cardLight);
            cardRoleBand.setStrokeColor(borderLight);
            cardRoleBand.setStrokeWidth(dpToPx(1));
            iconBoxBand.setBackgroundTintList(ColorStateList.valueOf(subtleLight));
            ivIconBand.setImageTintList(ColorStateList.valueOf(textSecondary));
        }

        // C. Show/Hide Sign-Up Specific Fields (Account Name + Spotify Link)
        if (isLoginMode) {
            layoutAccountName.setVisibility(View.GONE);
            cardSpotifyLink.setVisibility(View.GONE);
        } else {
            layoutAccountName.setVisibility(View.VISIBLE);
            if (isBandRole) {
                tvNameLabel.setText("Band / Artist Name");
                etAccountName.setHint("e.g., Hiraya Collective");
                cardSpotifyLink.setVisibility(View.VISIBLE);
            } else {
                tvNameLabel.setText("Venue / Collective Name");
                etAccountName.setHint("e.g., Mow's Bar QC");
                cardSpotifyLink.setVisibility(View.GONE);
            }
        }

        // D. Update Primary Submit Button Text & Brand Color
        String roleName = isBandRole ? "Band" : "Organizer";
        String actionText = isLoginMode
                ? "Log In as " + roleName
                : "Create " + roleName + " Account";
        btnSubmitAuth.setText(actionText);
        btnSubmitAuth.setBackgroundTintList(
                ColorStateList.valueOf(isBandRole ? emerald : amber)
        );
    }

    /**
     * Validates form inputs and routes the user to either the Band or Organizer view.
     */
    private void handleAuthSubmit() {
        String name = etAccountName.getText() != null
                ? etAccountName.getText().toString().trim() : "";
        String email = etEmail.getText() != null
                ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null
                ? etPassword.getText().toString().trim() : "";

        if (!isLoginMode && TextUtils.isEmpty(name)) {
            etAccountName.setError("Please enter a name");
            etAccountName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Please enter your email");
            etEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Please enter your password");
            etPassword.requestFocus();
            return;
        }

        String displayTarget = !isLoginMode && !name.isEmpty()
                ? name
                : (isBandRole ? "Hiraya Collective" : "Mow's Bar QC");

        proceedToMainScreen(displayTarget);
    }

    /**
     * Displays a confirmation Toast and navigates to MainActivity.
     */
    private void proceedToMainScreen(String accountLabel) {
        String roleTag = isBandRole ? "Band" : "Organizer";
        Toast.makeText(
                this,
                "Signed in as " + accountLabel + " (" + roleTag + ")",
                Toast.LENGTH_SHORT
        ).show();

        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        intent.putExtra("ACCOUNT_NAME", accountLabel);
        intent.putExtra("ACCOUNT_ROLE", roleTag);
        intent.putExtra("SPOTIFY_LINKED", cbSpotifyLink.isChecked());
        startActivity(intent);
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}