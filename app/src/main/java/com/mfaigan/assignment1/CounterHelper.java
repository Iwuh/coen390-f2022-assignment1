package com.mfaigan.assignment1;

import android.content.Context;
import android.content.SharedPreferences;

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

}
