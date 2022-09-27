package com.mfaigan.assignment1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataRecyclerViewAdapter extends RecyclerView.Adapter<DataRecyclerViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewDataCounterName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDataCounterName = itemView.findViewById(R.id.textViewDataCounterName);
        }

        public void setData(CounterHelper controller, CounterHelper.Counter counter, boolean useEventName) {
            if (useEventName)
            {
                textViewDataCounterName.setText(controller.getCounterName(counter));
            }
            else
            {
                textViewDataCounterName.setText(counter.toString());
            }
        }
    }

    private final CounterHelper counterHelper;
    private List<CounterHelper.Counter> counterHistory;
    private boolean showEventNames;

    public DataRecyclerViewAdapter(CounterHelper counterHelper, List<CounterHelper.Counter> counterHistory, boolean showEventNames) {
        this.counterHelper = counterHelper;
        this.counterHistory = counterHistory;
        this.showEventNames = showEventNames;
    }

    public void setCounterHistory(List<CounterHelper.Counter> counterHistory)
    {
        this.counterHistory = counterHistory;
    }
    public void setShowEventNames(boolean showEventNames)
    {
        this.showEventNames = showEventNames;
    }

    @NonNull
    @Override
    public DataRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_data_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.setData(counterHelper, counterHistory.get(position), showEventNames);
    }

    @Override
    public int getItemCount() {
        return counterHistory.size();
    }
}
