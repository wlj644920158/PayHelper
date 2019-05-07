package com.wanglijun.payhelper.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wanglijun.payhelper.HelperApplication;
import com.wanglijun.payhelper.HistoryAdapter;
import com.wanglijun.payhelper.R;
import com.wanglijun.payhelper.entity.Bill;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView billListView;
    private HistoryAdapter historyAdapter;
    private LinearLayout ll_empty;
    private SmartRefreshLayout refreshLayout;

    private int page = 0;
    private int size = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* 显示App icon左侧的back键 */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_list);
        setTitle("历史订单");
        billListView = findViewById(R.id.recyclerView);
        ll_empty = findViewById(R.id.ll_empty);
        refreshLayout = findViewById(R.id.refreshLayout);
        //设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                List<Bill> bills = HelperApplication.billDao().bills(page, size);
                if (bills != null && bills.size() > 0) {
                    ll_empty.setVisibility(View.GONE);
                    historyAdapter.refreshBills(bills);

                } else {
                    ll_empty.setVisibility(View.VISIBLE);
                }
                refreshlayout.finishRefresh();//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                page++;
                List<Bill> bills = HelperApplication.billDao().bills(page, size);
                if (bills != null && bills.size() > 0) {
                    historyAdapter.addBills(bills);

                }
                refreshlayout.finishLoadMore();//传入false表示刷新失败
            }
        });

        historyAdapter = new HistoryAdapter();
        billListView.setLayoutManager(new LinearLayoutManager(this));
        billListView.setAdapter(historyAdapter);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.autoRefresh();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
