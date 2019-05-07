package com.wanglijun.payhelper.common;

import android.content.Context;
import android.content.SharedPreferences;

import static com.wanglijun.payhelper.common.Constants.SP_NAME;

public class PreferenceUtil {

    private static PreferenceUtil util;
    private SharedPreferences sharedPreferences;

    public static PreferenceUtil getInstance() {
        if (util == null) {
            synchronized (PreferenceUtil.class) {
                if (util == null) {
                    util = new PreferenceUtil();
                }
            }
        }
        return util;
    }

    private PreferenceUtil() {
    }

    /**
     * init once
     *
     * @param context
     */
    public void init(Context context) {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public long userId() {
        return sharedPreferences.getLong(Constants.SP_USER_ID, -1);
    }

    public void userId(long userId) {
        sharedPreferences.edit().putLong(Constants.SP_USER_ID, userId).commit();
    }

}
