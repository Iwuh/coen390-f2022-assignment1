package com.mfaigan.assignment1;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.Objects;

public class CounterHelper {
    private final SharedPreferences counterPreferences;
    private final Context context;

    public enum Counter
    {
        Counter1, Counter2, Counter3
    }

    public CounterHelper(Context ctx)
    {
        context = ctx;
        counterPreferences = context.getSharedPreferences(context.getString(R.string.counter_preferences), Context.MODE_PRIVATE);
    }

    // TODO refactor these methods to use the Counter enum
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

    /**
     * Resets the counter history back to an empty string.
     */
    public void resetCountHistory()
    {
        SharedPreferences.Editor editor = counterPreferences.edit();
        editor.putString(context.getString(R.string.counter_preferences_count_history_key), "");
        editor.apply();
    }

    /**
     * Obtains the ordered history of all counter presses since the last reset.
     * @return The history, in string representation. Each character is either '1', '2', or '3' to represent a counter. Lower indices are older.
     */
    public String getCountHistory()
    {
        // We represent an empty count history using an empty string rather than a null reference.
        return counterPreferences.getString(context.getString(R.string.counter_preferences_count_history_key), "");
    }

    /**
     * Appends a new counter event to the count history. Resets the count history if this would exceed the maximum allowed counts.
     * @param c Indicates which counter identifier should be appended to the history string.
     * @return The new length of the history string, after modification.
     */
    public int appendToCountHistory(Counter c)
    {
        final String KEY = context.getString(R.string.counter_preferences_count_history_key);

        String toAppend;
        switch (c)
        {
            case Counter1:
                toAppend = "1";
                break;
            case Counter2:
                toAppend = "2";
                break;
            case Counter3:
                toAppend = "3";
                break;
            default:
                throw new IllegalArgumentException("Unknown counter");
        }

        SharedPreferences.Editor editor = counterPreferences.edit();
        int retVal;
        if (!counterPreferences.contains(KEY))
        {
            // If the count history does not exist, initialize it.
            editor.putString(KEY, toAppend);
            retVal = 1;
        }
        else
        {
            String currentHistory = counterPreferences.getString(KEY, "");
            if (currentHistory.length() >= getMaximumCounts())
            {
                // If the count history does exist but its length exceeds the maximum allowed counts, reset the history.
                editor.putString(KEY, toAppend);
                retVal = 1;
            }
            else
            {
                // Otherwise, append the new event to the end of the history string.
                editor.putString(KEY, currentHistory + toAppend);
                retVal = currentHistory.length() + 1;
            }
        }
        editor.apply();
        return retVal;
    }
}
