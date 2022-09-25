package com.mfaigan.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private int totalCount = 0; // temporary

    private Button buttonCounter1;
    private Button buttonCounter2;
    private Button buttonCounter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView totalCountDisplay = findViewById(R.id.textViewTotalCountValue);
        totalCountDisplay.setText(Integer.toString(totalCount));

        buttonCounter1 = findViewById(R.id.buttonCounter1);
        buttonCounter2 = findViewById(R.id.buttonCounter2);
        buttonCounter3 = findViewById(R.id.buttonCounter3);

        findViewById(R.id.buttonSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSettings();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        CounterHelper controller = new CounterHelper(getApplicationContext());

        // If any settings are missing when we enter the activity, go to the settings activity instead.
        if (controller.areSettingsMissing())
        {
            goToSettings();
        }

        // Otherwise, get the counter names and set the button texts.
        String[] counterNames = controller.getAllCounterNames();
        buttonCounter1.setText(counterNames[0]);
        buttonCounter2.setText(counterNames[1]);
        buttonCounter3.setText(counterNames[2]);
    }

    private void goToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}