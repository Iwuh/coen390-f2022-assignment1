package com.mfaigan.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private EditText editTextCounter1Name;
    private EditText editTextCounter2Name;
    private EditText editTextCounter3Name;
    private EditText editTextMaximumCounts;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editTextCounter1Name = findViewById(R.id.editTextCounter1Name);
        editTextCounter2Name = findViewById(R.id.editTextCounter2Name);
        editTextCounter3Name = findViewById(R.id.editTextCounter3Name);
        editTextMaximumCounts = findViewById(R.id.editTextMaximumCounts);
        buttonSave = findViewById(R.id.buttonSave);

        setSupportActionBar(findViewById(R.id.toolbarSettings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs())
                {
                    storeSettings();
                    setEditMode(false);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        CounterHelper controller = new CounterHelper(getApplicationContext());
        if (controller.areSettingsMissing())
        {
            setEditMode(true);
        }
        else
        {
            String[] counterNames = controller.getAllCounterNames();
            editTextCounter1Name.setText(counterNames[0]);
            editTextCounter2Name.setText(counterNames[1]);
            editTextCounter3Name.setText(counterNames[2]);

            int maximumCounts = controller.getMaximumCounts();
            editTextMaximumCounts.setText(Integer.toString(maximumCounts));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit_settings) {
            setEditMode(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param onOff Whether edit mode should be enabled (true) or disabled (false).
     */
    private void setEditMode(boolean onOff)
    {
        if (onOff)
        {
            editTextCounter1Name.setEnabled(true);
            editTextCounter2Name.setEnabled(true);
            editTextCounter3Name.setEnabled(true);
            editTextMaximumCounts.setEnabled(true);
            buttonSave.setVisibility(View.VISIBLE);
        }
        else
        {
            editTextCounter1Name.setEnabled(false);
            editTextCounter2Name.setEnabled(false);
            editTextCounter3Name.setEnabled(false);
            editTextMaximumCounts.setEnabled(false);
            buttonSave.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Validates user input in the counter name and maximum counts EditTexts, and displays a toast in the case of invalid input.
     * @return True if all four user inputs are valid, otherwise false.
     */
    private boolean validateInputs()
    {
        // All fields must be non-empty.
        if (editTextCounter1Name.getText().length() == 0 ||
            editTextCounter2Name.getText().length() == 0 ||
            editTextCounter3Name.getText().length() == 0 ||
            editTextMaximumCounts.getText().length() == 0)
        {
            Toast toast = Toast.makeText(this, R.string.error_field_empty, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }

        // All counter names should be no more than 20 characters.
        // Only alphabetical and space characters are allowed, this is enforced by the digits XML property.
        if (editTextCounter1Name.getText().length() > 20 ||
            editTextCounter2Name.getText().length() > 20 ||
            editTextCounter3Name.getText().length() > 20)
        {
            Toast toast = Toast.makeText(this, R.string.error_counter_name_too_long, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }

        // The maximum counts should be between 5 and 200, inclusive.
        int maxCountsInput = Integer.parseInt(editTextMaximumCounts.getText().toString());
        if (maxCountsInput < 5 || maxCountsInput > 200)
        {
            Toast toast = Toast.makeText(this, R.string.error_maximum_counts_out_of_range, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }

        return true;
    }

    /**
     * Stores the values of the EditText fields using CounterHelper. This function does not perform input validation.
     */
    private void storeSettings()
    {
        CounterHelper controller = new CounterHelper(getApplicationContext());
        controller.setCounterName(CounterHelper.Counter.Counter1, editTextCounter1Name.getText().toString());
        controller.setCounterName(CounterHelper.Counter.Counter2, editTextCounter2Name.getText().toString());
        controller.setCounterName(CounterHelper.Counter.Counter3, editTextCounter3Name.getText().toString());
        controller.setMaximumCounts(Integer.parseInt(editTextMaximumCounts.getText().toString()));
    }
}