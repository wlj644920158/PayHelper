package com.wanglijun.payhelper;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.wanglijun.payhelper.common.PreferenceUtil;
import com.wanglijun.payhelper.db.BillDao;

public class HelperApplication extends Application {
    private static Context context;
    private static BillDao billDao;

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceUtil.getInstance().init(this);
        context = this;
        billDao = new BillDao(this);
    }

    public static Context getContext() {
        return context;
    }

    public static BillDao billDao() {
        return billDao;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
