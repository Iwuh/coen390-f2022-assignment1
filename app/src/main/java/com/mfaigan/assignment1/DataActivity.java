package com.mfaigan.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCounterHistory;
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

        setSupportActionBar(findViewById(R.id.toolbarData));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewCounter1History = findViewById(R.id.textViewCounter1History);
        textViewCounter2History = findViewById(R.id.textViewCounter2History);
        textViewCounter3History = findViewById(R.id.textViewCounter3History);
        textViewAllCountersHistory = findViewById(R.id.textViewAllCountersHistory);

        counterHelper = new CounterHelper(getApplicationContext());

        // Set up the recycler view to use a linear layout with our custom adapter.
        recyclerViewCounterHistory = findViewById(R.id.recyclerViewCounterHistory);
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
        if (item.getItemId() == R.id.action_toggle_event_names)
        {
            // Toggle the event name display in the adapter and update the entire range.
            recyclerViewAdapter.setShowEventNames(!showEventNames);
            recyclerViewAdapter.notifyItemRangeChanged(0, recyclerViewAdapter.getItemCount());
            // Store the event name display status for future toggles.
            showEventNames = !showEventNames;
            updateTextViews();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateTextViews()
    {
        Map<CounterHelper.Counter, Long> groupedCounts = counterHelper.getGroupedCountHistory();
        if (showEventNames)
        {
            String[] counterNames = counterHelper.getAllCounterNames();
            textViewCounter1History.setText(String.format(Locale.ENGLISH,
                    "%s: %d events",
                    counterNames[0],
                    groupedCounts.get(CounterHelper.Counter.Counter1)));

            textViewCounter2History.setText(String.format(Locale.ENGLISH,
                    "%s: %d events",
                    counterNames[1],
                    groupedCounts.get(CounterHelper.Counter.Counter2)));

            textViewCounter3History.setText(String.format(Locale.ENGLISH,
                    "%s: %d events",
                    counterNames[2],
                    groupedCounts.get(CounterHelper.Counter.Counter3)));
        }
        else
        {
            textViewCounter1History.setText(String.format(Locale.ENGLISH, "Counter 1: %d events", groupedCounts.get(CounterHelper.Counter.Counter1)));
            textViewCounter2History.setText(String.format(Locale.ENGLISH, "Counter 2: %d events", groupedCounts.get(CounterHelper.Counter.Counter2)));
            textViewCounter3History.setText(String.format(Locale.ENGLISH, "Counter 3: %d events", groupedCounts.get(CounterHelper.Counter.Counter3)));
        }
        textViewAllCountersHistory.setText(String.format(Locale.ENGLISH,"Total events: %d", counterHelper.getCountHistory().length()));
    }
}