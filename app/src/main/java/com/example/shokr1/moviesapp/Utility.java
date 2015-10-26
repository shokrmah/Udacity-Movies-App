package com.example.shokr1.moviesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by shokr 1 on 10/10/2015.
 */
public class Utility {

    static public int update_sorting_type(Context context)
    {
        int sorting_type = 0;
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        String sorting =  SP.getString(context.getString(R.string.sorting_key),
            "popular");

        if(sorting != null) {
            if (sorting.equals("popular"))
                sorting_type = 0;
            else if (sorting.equals("rating"))
                sorting_type = 1;
            else sorting_type = 2;
        }
        return sorting_type;
    }


}
