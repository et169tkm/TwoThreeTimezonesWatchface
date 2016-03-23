package com.erictang.twothreetimezoneswatchface;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by erictang on 3/23/16.
 */
public class ConfigUtil {
    private static final String SHARED_PREF_NAME = "CONFIG";
    private static final String SHARED_PREF_KEY_TIMEZONE_PREFIX = "TIMEZONE_";

    public static final String ACTION_TIMEZONE_UPDATED =
            "com.erictang.twothreetimezoneswatchface.ACTION_TIMEZONE_UPDATED";

    // util methods
    public static void setTimezoneId(Context context, int index, String tzid) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String originalTzid = getTimezoneId(context, index);

        if (!TextUtils.equals(originalTzid, tzid)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (tzid == null) {
                editor.remove(SHARED_PREF_KEY_TIMEZONE_PREFIX + index);
            } else {
                editor.putString(SHARED_PREF_KEY_TIMEZONE_PREFIX + index, tzid);
            }
            editor.commit();

            broadcastConfigUpdate(context);
        }
    }

    public static String getTimezoneId(Context context, int index) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SHARED_PREF_KEY_TIMEZONE_PREFIX + index, null);
    }

    private static void broadcastConfigUpdate(Context context) {
        Intent intent = new Intent(ACTION_TIMEZONE_UPDATED);
        context.sendBroadcast(intent);
    }
}
