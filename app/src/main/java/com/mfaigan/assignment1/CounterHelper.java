package com.mfaigan.assignment1;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.Objects;

public class CounterHelper {
    private SharedPreferences counterPreferences;
    private Context context;

    public CounterHelper(Context ctx)
    {
        context = ctx;
        counterPreferences = context.getSharedPreferences(context.getString(R.string.counter_preferences), Context.MODE_PRIVATE);
    }

    public String getCounter1Name()
    {
        return counterPreferences.getString(context.getString(R.string.counter_preferences_counter1_key), null);
    }
    public String getCounter2Name()
    {
        return counterPreferences.getString(context.getString(R.string.counter_preferences_counter2_key), null);
    }
    public String getCounter3Name()
    {
        return counterPreferences.getString(context.getString(R.string.counter_preferences_counter3_key), null);
    }
    public String[] getAllCounterNames()
    {
        return new String[]{getCounter1Name(), getCounter2Name(), getCounter3Name()};
    }

    public int getMaximumCounts()
    {
        return counterPreferences.getInt(context.getString(R.string.counter_preferences_maximum_counts_key), 0);
    }

    /**
     * @return True if any counters are missing names or the maximum counts is not set.
     */
    public boolean areSettingsMissing()
    {
        String[] counterNames = getAllCounterNames();
        int maximumCounts = getMaximumCounts();

        return Arrays.stream(counterNames).anyMatch(Objects::isNull) || maximumCounts == 0;
    }

    public void setCounter1Name(String counter1Name)
    {
        SharedPreferences.Editor editor = counterPreferences.edit();
        editor.putString(context.getString(R.string.counter_preferences_counter1_key), counter1Name);
        editor.apply();
    }
    public void setCounter2Name(String counter2Name)
    {
        SharedPreferences.Editor editor = counterPreferences.edit();
        editor.putString(context.getString(R.string.counter_preferences_counter2_key), counter2Name);
        editor.apply();
    }
    public void setCounter3Name(String counter3Name)
    {
        SharedPreferences.Editor editor = counterPreferences.edit();
        editor.putString(context.getString(R.string.counter_preferences_counter3_key), counter3Name);
        editor.apply();
    }
    public void setMaximumCounts(int maximumCounts)
    {
        SharedPreferences.Editor editor = counterPreferences.edit();
        editor.putInt(context.getString(R.string.counter_preferences_maximum_counts_key), maximumCounts);
        editor.apply();
    }
}
