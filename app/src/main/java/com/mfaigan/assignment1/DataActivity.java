package com.mfaigan.assignment1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DataActivity extends AppCompatActivity {

    private DataRecyclerViewAdapter recyclerViewAdapter;
    private CounterHelper counterHelper;

    private TextView textViewCounter1History;
    private TextView textViewCounter2History;
    private TextView textViewCounter3History;
    private TextView textViewAllCountersHistory;

    private boolean showEventNames = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        // Initialize the toolbar for this activity and show the back button.
        setSupportActionBar(findViewById(R.id.toolbarData));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewCounter1History = findViewById(R.id.textViewCounter1History);
        textViewCounter2History = findViewById(R.id.textViewCounter2History);
        textViewCounter3History = findViewById(R.id.textViewCounter3History);
        textViewAllCountersHistory = findViewById(R.id.textViewAllCountersHistory);

        counterHelper = new CounterHelper(getApplicationContext());

        // Set up the recycler view to use a linear layout with our custom adapter.
        RecyclerView recyclerViewCounterHistory = findViewById(R.id.recyclerViewCounterHistory);
        recyclerViewAdapter = new DataRecyclerViewAdapter(counterHelper, counterHelper.getCountHistoryList(), showEventNames);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewCounterHistory.setLayoutManager(linearLayoutManager);
        recyclerViewCounterHistory.setAdapter(recyclerViewAdapter);
        // Add a DividerItemDecoration to the RecyclerView, which inserts a dividing line between each element.
        recyclerViewCounterHistory.addItemDecoration(new DividerItemDecoration(recyclerViewCounterHistory.getContext(), linearLayoutManager.getOrientation()));
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Whenever we re-enter this activity from Main, assume the worst and regenerate the entire RecyclerView data.
        List<CounterHelper.Counter> countHistoryList = counterHelper.getCountHistoryList();
        recyclerViewAdapter.setCounterHistory(countHistoryList);
        showEventNames = true;
        recyclerViewAdapter.setShowEventNames(true);
        recyclerViewAdapter.notifyDataSetChanged();

        // Additionally, update the textViews to show the most recent count data.
        updateTextViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_toggle_event_names) {
            // Toggle the event name display in the adapter and update the entire range.
            recyclerViewAdapter.setShowEventNames(!showEventNames);
            recyclerViewAdapter.notifyItemRangeChanged(0, recyclerViewAdapter.getItemCount());

            // Store the event name display status for future toggles.
            showEventNames = !showEventNames;

            // Finally, update the text views accordingly.
            updateTextViews();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateTextViews() {
        // Get a mapping of counter to number of events, and use it to fill out the textViews.
        Map<CounterHelper.Counter, Long> groupedCounts = counterHelper.getGroupedCountHistory();
        if (showEventNames) {
            String[] counterNames = counterHelper.getAllCounterNames();

            textViewCounter1History.setText(String.format(Locale.ENGLISH,
                    "%s: %d events",
                    counterNames[0],
                    groupedCounts.getOrDefault(CounterHelper.Counter.Counter1, 0L)));

            textViewCounter2History.setText(String.format(Locale.ENGLISH,
                    "%s: %d events",
                    counterNames[1],
                    groupedCounts.getOrDefault(CounterHelper.Counter.Counter2, 0L)));

            textViewCounter3History.setText(String.format(Locale.ENGLISH,
                    "%s: %d events",
                    counterNames[2],
                    groupedCounts.getOrDefault(CounterHelper.Counter.Counter3, 0L)));
        } else {
            textViewCounter1History.setText(String.format(Locale.ENGLISH,
                    "Counter 1: %d events",
                    groupedCounts.getOrDefault(CounterHelper.Counter.Counter1, 0L)));

            textViewCounter2History.setText(String.format(Locale.ENGLISH,
                    "Counter 2: %d events",
                    groupedCounts.getOrDefault(CounterHelper.Counter.Counter2, 0L)));

            textViewCounter3History.setText(String.format(Locale.ENGLISH,
                    "Counter 3: %d events",
                    groupedCounts.getOrDefault(CounterHelper.Counter.Counter3, 0L)));
        }
        // Use the total number of events to fill out the final textView.
        textViewAllCountersHistory.setText(String.format(Locale.ENGLISH, "Total events: %d", counterHelper.getCountHistory().length()));
    }
}