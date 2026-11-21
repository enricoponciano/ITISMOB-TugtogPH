package com.ph.tugtog;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Edit form for the band profile. Saves to the mock SharedPreferences store.
 */
public class EditBandActivity extends AppCompatActivity {

    // Each field id is paired with the preference key it loads from / saves to
    private static final int[] FIELD_IDS = {
            R.id.etBandName, R.id.etBandCity, R.id.etBandGenres, R.id.etBandBio,
            R.id.etBandGear, R.id.etBandFb, R.id.etBandPhone
    };
    private static final String[] FIELD_KEYS = {
            BandPrefs.NAME, BandPrefs.CITY, BandPrefs.GENRES, BandPrefs.BIO,
            BandPrefs.GEAR, BandPrefs.FB, BandPrefs.PHONE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_band);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.tvCancel).setOnClickListener(v -> finish());

        // Only pre-fill on first open so a rotation doesn't wipe what the user typed
        if (savedInstanceState == null) {
            for (int i = 0; i < FIELD_IDS.length; i++) {
                ((EditText) findViewById(FIELD_IDS[i])).setText(BandPrefs.get(this, FIELD_KEYS[i]));
            }
        }

        findViewById(R.id.btnSaveBandProfile).setOnClickListener(v -> save());
    }

    private void save() {
        EditText etName = findViewById(R.id.etBandName);
        if (etName.getText().toString().trim().isEmpty()) {
            etName.setError("Please enter your band name");
            etName.requestFocus();
            return;
        }

        SharedPreferences.Editor editor = BandPrefs.prefs(this).edit();
        for (int i = 0; i < FIELD_IDS.length; i++) {
            EditText field = findViewById(FIELD_IDS[i]);
            editor.putString(FIELD_KEYS[i], field.getText().toString().trim());
        }
        editor.apply();

        Toast.makeText(this, "Band Profile Saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
