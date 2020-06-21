package ru.home.government.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashSet;
import java.util.Set;

import ru.home.government.App;

public class CacheRepository {

    private static final String KEY_LAWS = "KEY_LAWS";
    
    private SharedPreferences pref;

    public CacheRepository(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveLawCode(String code) {
        Set<String> values = getLawCodes();
        values.add(code);
        saveAsJson(values);
    }

    public Set<String> getLawCodes() {
        Set<String> result = new HashSet<>();
        try {
            String codes = pref.getString(KEY_LAWS, "[]");
            JSONArray json = new JSONArray(codes);
            for (int idx = 0; idx < json.length(); idx++) {
                result.add(json.getString(idx));
            }
        } catch (JSONException e) {
            Log.e(App.TAG, "Failed to process cache", e);
        }
        return result;
    }

    public void removeLawCode(String lawCode) {
        Set<String> values = getLawCodes();
        values.remove(lawCode);
        saveAsJson(values);
    }

    public Boolean isCodeInCache(String lawCode) {
        return getLawCodes().contains(lawCode);
    }

    private void saveAsJson(Set<String> values) {
        JSONArray json = new JSONArray(values);
        pref.edit()
                .putString(KEY_LAWS, json.toString())
                .apply();
    }
}
