package com.wanglijun.payhelper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wanglijun.payhelper.entity.Bill;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<Bill> bills;


    public HistoryAdapter() {
        bills = new ArrayList<>();
    }

    public void addBills(List<Bill> list) {
        bills.addAll(list);
        notifyDataSetChanged();
    }

    public void refreshBills(List<Bill> list) {
        bills.clear();
        bills.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HistoryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bill, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
        Bill bill = bills.get(i);
        historyViewHolder.tv_date.setText(bill.getDate());
        historyViewHolder.tv_money.setText(String.valueOf(bill.getMoney()));
        historyViewHolder.tv_content.setText(bill.getContent());
        historyViewHolder.tv_sync.setText(bill.getSync() == 0 ? "未同步" : "已同步");
        historyViewHolder.tv_sync.setTextColor(bill.getSync() == 0 ? 0xffff1212 : 0xff007410);
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date;
        private TextView tv_money;
        private TextView tv_content;
        private TextView tv_sync;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_money = itemView.findViewById(R.id.tv_money);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_sync = itemView.findViewById(R.id.tv_sync);
        }
    }

}
