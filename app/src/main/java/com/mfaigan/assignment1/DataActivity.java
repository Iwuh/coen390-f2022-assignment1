package com.mfaigan.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCounterHistory;
    private DataRecyclerViewAdapter recyclerViewAdapter;
    private CounterHelper counterHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        setSupportActionBar(findViewById(R.id.toolbarData));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        counterHelper = new CounterHelper(getApplicationContext());

        // Set up the recycler view to use a linear layout with our custom adapter.
        recyclerViewCounterHistory = findViewById(R.id.recyclerViewCounterHistory);
        recyclerViewAdapter = new DataRecyclerViewAdapter(counterHelper, counterHelper.getCountHistoryList(), true);

        recyclerViewCounterHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCounterHistory.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Whenever we re-enter this activity from Main, assume the worst and regenerate the entire RecyclerView data.
        recyclerViewAdapter.setCounterHistory(counterHelper.getCountHistoryList());
        recyclerViewAdapter.setShowEventNames(true);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO add functionality
        return super.onOptionsItemSelected(item);
    }
}