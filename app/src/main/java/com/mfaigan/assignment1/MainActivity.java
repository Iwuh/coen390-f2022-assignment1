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

    private Button buttonCounter1;
    private Button buttonCounter2;
    private Button buttonCounter3;
    private TextView totalCountDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalCountDisplay = findViewById(R.id.textViewTotalCountValue);

        buttonCounter1 = findViewById(R.id.buttonCounter1);
        buttonCounter2 = findViewById(R.id.buttonCounter2);
        buttonCounter3 = findViewById(R.id.buttonCounter3);

        findViewById(R.id.buttonSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSettings();
            }
        });
        findViewById(R.id.buttonShowCounts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToData();
            }
        });

        buttonCounter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCount(CounterHelper.Counter.Counter1);
            }
        });
        buttonCounter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCount(CounterHelper.Counter.Counter2);
            }
        });
        buttonCounter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCount(CounterHelper.Counter.Counter3);
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

        // Additionally, set the total count display.
        String countHistory = controller.getCountHistory();
        totalCountDisplay.setText(Integer.toString(countHistory.length()));
    }

    private void updateCount(CounterHelper.Counter c)
    {
        CounterHelper controller = new CounterHelper(getApplicationContext());
        int newCount = controller.appendToCountHistory(c);
        totalCountDisplay.setText(Integer.toString(newCount));
    }

    private void goToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void goToData()
    {
        Intent intent = new Intent(this, DataActivity.class);
        startActivity(intent);
    }
}