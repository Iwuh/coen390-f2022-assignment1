package com.mfaigan.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int totalCount = 0; // temporary

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView totalCountDisplay =  findViewById(R.id.textViewTotalCountValue);
        totalCountDisplay.setText(Integer.toString(totalCount));

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
        String counter1Name = controller.getCounter1Name();
        String counter2Name = controller.getCounter2Name();
        String counter3Name = controller.getCounter3Name();

        if (counter1Name != null)
        {
            ((Button)findViewById(R.id.buttonCounter1)).setText(counter1Name);
        }
        if (counter2Name != null)
        {
            ((Button)findViewById(R.id.buttonCounter2)).setText(counter2Name);
        }
        if (counter3Name != null)
        {
            ((Button)findViewById(R.id.buttonCounter3)).setText(counter3Name);
        }
    }

    private void goToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}