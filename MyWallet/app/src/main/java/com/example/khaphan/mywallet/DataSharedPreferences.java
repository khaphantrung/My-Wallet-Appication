package com.example.khaphan.mywallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by kha.phan on 6/16/2016.
 */
public class DataSharedPreferences {
    public static DataSharedPreferences mDataSharedPreferences;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public DataSharedPreferences(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mSharedPreferences.edit();
    }

    public static DataSharedPreferences getDataSharedPreferences(Context context) {
        if (mDataSharedPreferences == null)
            mDataSharedPreferences = new DataSharedPreferences(context);
        return mDataSharedPreferences;
    }

    public String getPreferencesString(String key) {
        return this.mSharedPreferences.getString(key, "");
    }

    public void setPreferencesString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }


}
