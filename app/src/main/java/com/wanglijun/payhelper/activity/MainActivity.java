package com.wanglijun.payhelper.activity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Process;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanglijun.payhelper.HelperApplication;
import com.wanglijun.payhelper.R;
import com.wanglijun.payhelper.utils.AppUtil;
import com.wanglijun.payhelper.common.Constants;
import com.wanglijun.payhelper.common.PreferenceUtil;
import com.wanglijun.payhelper.entity.Bill;
import com.wanglijun.payhelper.service.HelperNotificationListenerService;

import java.util.List;

import static com.wanglijun.payhelper.common.Constants.LISTENING_TARGET_PKG;

public class MainActivity extends AppCompatActivity {
    private TextView tv_date;
    private TextView tv_money;
    private TextView tv_content;
    private TextView tv_sync;
    private TextView tv_tip;

    private LinearLayout ll_empty;

    private class ServiceBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("type", -1)) {
                case Constants.BROADCAST_TYPE_CONNECTED_SERVICE:
                case Constants.BROADCAST_TYPE_DISCONNECTED_SERVICE:
                    tv_tip.setText(intent.getStringExtra("msg"));
                    break;
                case Constants.BROADCAST_TYPE_NEW_BILL:
                    handleUser();
                    break;
            }
        }


    }

    private ServiceBroadcastReceiver serviceBroadcastReceiver = new ServiceBroadcastReceiver();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter(Constants.SERVICE_ACTION);
        LocalBroadcastManager.getInstance(HelperApplication.getContext()).registerReceiver(serviceBroadcastReceiver, intentFilter);

        ll_empty = findViewById(R.id.ll_empty);
        tv_date = findViewById(R.id.tv_date);
        tv_money = findViewById(R.id.tv_money);
        tv_content = findViewById(R.id.tv_content);
        tv_sync = findViewById(R.id.tv_sync);
        tv_tip = findViewById(R.id.tv_tip);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(HelperApplication.getContext()).unregisterReceiver(serviceBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppUtil.isAvailable(getApplicationContext(), LISTENING_TARGET_PKG)) {
            ensureCollectorRunning();
            handleUser();
        } else {
            tv_tip.setText("未安装xxx应用,服务无法开启!");
        }
    }

    private void ensureCollectorRunning() {
        tv_tip.setText("检查服务启动情况...");
        ComponentName collectorComponent = new ComponentName(this, HelperNotificationListenerService.class);
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        boolean collectorRunning = false;
        List<ActivityManager.RunningServiceInfo> runningServices = manager.getRunningServices(Integer.MAX_VALUE);
        if (runningServices == null) {
            return;
        }
        for (ActivityManager.RunningServiceInfo service : runningServices) {
            if (service.service.equals(collectorComponent)) {
                if (service.pid == Process.myPid()) {
                    collectorRunning = true;
                }
            }
        }
        if (collectorRunning) {
            tv_tip.setText("服务正在运行...");
            return;
        }
        toggleNotificationListenerService();
    }

    private void toggleNotificationListenerService() {
        tv_tip.setText("准备启动监听服务...");
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName(this, HelperNotificationListenerService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        pm.setComponentEnabledSetting(
                new ComponentName(this, HelperNotificationListenerService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }


    private void handleUser() {
        int uid = (int) PreferenceUtil.getInstance().userId();
        if (uid != -1L) {
            Bill bill = HelperApplication.billDao().lastedBill();
            if (bill != null) {
                ll_empty.setVisibility(View.GONE);
                tv_date.setVisibility(View.VISIBLE);
                tv_money.setVisibility(View.VISIBLE);
                tv_content.setVisibility(View.VISIBLE);
                tv_sync.setVisibility(View.VISIBLE);
                tv_date.setText(bill.getDate());
                tv_money.setText(String.valueOf(bill.getMoney()));
                tv_content.setText(bill.getContent());
                tv_sync.setText(bill.getSync() == 0 ? "未同步" : "已同步");
                tv_sync.setTextColor(bill.getSync() == 0 ? 0xffff1212 : 0xff007410);
            } else {
                ll_empty.setVisibility(View.VISIBLE);
                tv_date.setVisibility(View.GONE);
                tv_money.setVisibility(View.GONE);
                tv_content.setVisibility(View.GONE);
                tv_sync.setVisibility(View.GONE);
            }

        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                PreferenceUtil.getInstance().userId(-1);
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.action_history:
                startActivity(new Intent(this, HistoryActivity.class));
                return true;
            case R.id.action_unbind:
                startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
