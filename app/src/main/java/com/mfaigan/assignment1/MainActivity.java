package com.mfaigan.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int totalCount = 0; // temporary

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView totalCountDisplay =  findViewById(R.id.textViewTotalCount);
        totalCountDisplay.setText(String.format("Total Count: %d", totalCount));
    }
}