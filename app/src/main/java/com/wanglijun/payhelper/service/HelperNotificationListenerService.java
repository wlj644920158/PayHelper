package com.wanglijun.payhelper.service;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.wanglijun.payhelper.HelperApplication;
import com.wanglijun.payhelper.NotificationUtils;
import com.wanglijun.payhelper.common.Constants;
import com.wanglijun.payhelper.common.PreferenceUtil;
import com.wanglijun.payhelper.entity.Bill;
import com.wanglijun.payhelper.entity.Result;
import com.wanglijun.payhelper.net.RetrofitUtil;
import com.wanglijun.payhelper.utils.AesUtil;

import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.wanglijun.payhelper.common.Constants.LISTENING_TARGET_PKG;

public class HelperNotificationListenerService extends NotificationListenerService {

    private static final String TAG = "通知监听服务";

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.sendNotification("收款助手", "服务正在运行...");
    }


    @Override
    public void onListenerConnected() {
        sendBroadcast(Constants.BROADCAST_TYPE_CONNECTED_SERVICE, "服务正在运行...");
    }

    @Override
    public void onListenerDisconnected() {
        sendBroadcast(Constants.BROADCAST_TYPE_DISCONNECTED_SERVICE, "监听服务已经断开...");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        final long uid = PreferenceUtil.getInstance().userId();
        if (uid == -1) {
            return;
        }

        Notification notification = sbn.getNotification();
        String pkg = sbn.getPackageName();
        if (notification == null) {
            return;
        }

        Bundle extras = notification.extras;
        if (extras == null)
            return;

        Log.d(TAG, "********************************************");
        final String title = getNotificationTitle(extras);
        final String content = getNotificationContent(extras);
        final String date = getNotificationTime(notification);
        printNotify(date, title, content);

        if (LISTENING_TARGET_PKG.equals(pkg)) {
            if (getNotificationTitle(extras).contains("交易成功")) {
                final String money = findMoney(content);
                postMoney((int) uid, notification.when, date, title, content, money);
            }
        }
        Log.d(TAG, "********************************************");
    }


    private void postMoney(final int uid, long when, final String date, final String title, final String content, final String money) {
        try {
            String signSrc = "q_id=" + uid + "&pay_money=" + money + "&time=" + when;
            String signEnc = AesUtil.encrypt(signSrc);
            RetrofitUtil.getInstance().userService().callback(signEnc)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<Result>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Result userResult) {
                            postCallback(uid, date, title, content, money, 1);
                            if (userResult.getStatus() != 1) {
                                Toast.makeText(HelperApplication.getContext(), userResult.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Toast.makeText(HelperApplication.getContext(), "出错了", Toast.LENGTH_SHORT).show();
                            if (e instanceof TimeoutException || e instanceof ConnectException) {
                                postCallback(uid, date, title, content, money, 0);
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            postCallback(uid, date, title, content, money, 0);
        }


    }


    private void postCallback(final int uid, final String date, final String title, final String content, final String money, int sync) {
        Bill bill = new Bill();
        bill.setTitle(title);
        bill.setContent(content);
        bill.setDate(date);
        bill.setMoney(money);
        bill.setSync(sync);
        bill.setUser_id(uid);
        HelperApplication.billDao().insertBill(bill);
        sendBroadcast(Constants.BROADCAST_TYPE_NEW_BILL, "收到新的订单");
    }

    /**
     * 从通知内容中提取出金额
     * @param content
     * @return
     */
    private String findMoney(String content) {
        String pattern = "(\\d+\\.\\d{2})元$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(content);
        if (m.find()) {
            return m.group(1);
        }
        return "0.00";
    }


    private void sendBroadcast(int type, String msg) {
        Intent intent = new Intent(Constants.SERVICE_ACTION);
        intent.putExtra("type", type);
        intent.putExtra("msg", msg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d(TAG, "onListenerConnected");
    }

    private String getNotificationTime(Notification notification) {
        long when = notification.when;
        Date date = new Date(when);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = format.format(date);
        return time;

    }

    private String getNotificationTitle(Bundle extras) {
        String title = null;
        title = extras.getString(Notification.EXTRA_TITLE, "");
        return title;
    }

    private String getNotificationContent(Bundle extras) {
        String content = null;
        content = extras.getString(Notification.EXTRA_TEXT, "");
        return content;
    }

    private void printNotify(String time, String title, String content) {
        Log.d(TAG, time);
        Log.d(TAG, title);
        Log.d(TAG, content);
    }


}
