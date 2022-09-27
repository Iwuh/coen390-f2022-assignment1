package com.mfaigan.assignment1;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class CounterHelper {
    private final SharedPreferences counterPreferences;
    private final Context context;

    public enum Counter
    {
        Counter1, Counter2, Counter3;

        public static Counter fromString(String s)
        {
            switch (s)
            {
                case "1":
                    return Counter1;
                case "2":
                    return Counter2;
                case "3":
                    return Counter3;
                default:
                    throw new IllegalArgumentException("Unknown counter");
            }
        }

        public static Counter fromChar(char c)
        {
            return fromString(Character.toString(c));
        }

        @NonNull
        @Override
        public String toString() {
            switch (this)
            {
                case Counter1:
                    return "1";
                case Counter2:
                    return "2";
                case Counter3:
                    return "3";
                default:
                    throw new IllegalArgumentException("Unknown counter");
            }
        }
    }

    public CounterHelper(Context ctx)
    {
        context = ctx;
        counterPreferences = context.getSharedPreferences(context.getString(R.string.counter_preferences), Context.MODE_PRIVATE);
    }

    /**
     * Returns the user-configured name of a given counter.
     * @param counter A value of the counter enum, indicating which name to return.
     * @return The name of the counter if it is set, otherwise null.
     */
    public String getCounterName(Counter counter)
    {
        int keyId;
        switch (counter)
        {
            case Counter1:
                keyId = R.string.counter_preferences_counter1_key;
                break;
            case Counter2:
                keyId = R.string.counter_preferences_counter2_key;
                break;
            case Counter3:
                keyId = R.string.counter_preferences_counter3_key;
                break;
            default:
                throw new IllegalArgumentException("Unknown counter");
        }
        return counterPreferences.getString(context.getString(keyId), null);
    }
    public String[] getAllCounterNames()
    {
        return new String[]{getCounterName(Counter.Counter1), getCounterName(Counter.Counter2), getCounterName(Counter.Counter3)};
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

    public void setCounterName(Counter counter, String counterName)
    {
        int keyId;
        switch (counter)
        {
            case Counter1:
                keyId = R.string.counter_preferences_counter1_key;
                break;
            case Counter2:
                keyId = R.string.counter_preferences_counter2_key;
                break;
            case Counter3:
                keyId = R.string.counter_preferences_counter3_key;
                break;
            default:
                throw new IllegalArgumentException("Unknown counter");
        }

        SharedPreferences.Editor editor = counterPreferences.edit();
        editor.putString(context.getString(keyId), counterName);
        editor.apply();
    }
    public void setMaximumCounts(int maximumCounts)
    {
        SharedPreferences.Editor editor = counterPreferences.edit();
        editor.putInt(context.getString(R.string.counter_preferences_maximum_counts_key), maximumCounts);
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
     * Obtains the ordered history of all counter presses since the last reset.
     * @return The history as a list of Counter values. Lower indices are older.
     */
    public List<Counter> getCountHistoryList()
    {
        // Convert the history string to a stream of integer code points.
        // Cast those to chars, then map each char to one of the counter enum values.
        // Finally, collect those into a list and return it.
        return getCountHistory().chars().mapToObj(i -> (char)i).map(Counter::fromChar).collect(Collectors.toList());
    }

    /**
     * Obtains the history of all counter presses since the last reset, grouped by counter number.
     * @return A map of counter enum values to the quantity of events corresponding to that counter.
     */
    public Map<Counter, Long> getGroupedCountHistory()
    {
        // The groupingBy collector takes a predicate function to determine equality of list entries,
        // and a reducer function applied to the list of items that belong to each group.
        // Combined, we group entries by enum value and then find the number of entries that corresponded to each enum value.
        return getCountHistoryList().stream().collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    }

    /**
     * Appends a new counter event to the count history. Resets the count history if this would exceed the maximum allowed counts.
     * @param c Indicates which counter identifier should be appended to the history string.
     * @return The new length of the history string, after modification.
     */
    public int appendToCountHistory(Counter c)
    {
        final String KEY = context.getString(R.string.counter_preferences_count_history_key);

        SharedPreferences.Editor editor = counterPreferences.edit();
        int retVal;
        if (!counterPreferences.contains(KEY))
        {
            // If the count history does not exist, initialize it.
            editor.putString(KEY, c.toString());
            retVal = 1;
        }
        else
        {
            String currentHistory = counterPreferences.getString(KEY, "");
            if (currentHistory.length() >= getMaximumCounts())
            {
                // If the count history does exist but its length exceeds the maximum allowed counts, reset the history.
                editor.putString(KEY, c.toString());
                retVal = 1;
            }
            else
            {
                // Otherwise, append the new event to the end of the history string.
                editor.putString(KEY, currentHistory + c.toString());
                retVal = currentHistory.length() + 1;
            }
        }
        editor.apply();
        return retVal;
    }
}
