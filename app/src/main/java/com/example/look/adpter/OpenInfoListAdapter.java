package com.example.look.adpter;

import static com.example.look.MainActivity.maskBankNum;
import static com.example.look.MainActivity.maskPhoneNum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.look.R;
import com.example.look.bean.AccountOpenInfo;

import java.util.List;

public class OpenInfoListAdapter extends BaseAdapter {

    private List<AccountOpenInfo> testData;
    Context myContext;

    public OpenInfoListAdapter(List<AccountOpenInfo> list, Context context) {
        myContext = context;
        testData = list;
    }

    public List<AccountOpenInfo> getAccountData() {
        return testData;
    }

    public void setAccountData(List<AccountOpenInfo> data) {
        testData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return testData.size();
    }

    @Override
    public AccountOpenInfo getItem(int position) {
        return testData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OpenInfoHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(myContext).inflate(R.layout.item_open_info, null);
            holder = new OpenInfoHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (OpenInfoHolder) convertView.getTag();
        }
        final AccountOpenInfo info = testData.get(position);
        holder.tvOpenNum.setText("客户编号：" + info.getOpenNum());
        holder.tvOpenName.setText("客户名称：" + info.getOpenName());
        holder.tvOpenPhone.setText("资金存管手机号：" + maskPhoneNum(info.getOpenPhone()));
        holder.tvOpenBank.setText(info.getOpenBank() + "：" + maskBankNum(info.getBankNum()));
        holder.imgSelect.setImageDrawable(info.selectState ? myContext.getDrawable(R.drawable.ico_checkbox_high) : myContext.getDrawable(R.drawable.ico_checkbox_normal));
        return convertView;
    }

    static class OpenInfoHolder extends RecyclerView.ViewHolder {

        TextView tvOpenNum, tvOpenName, tvOpenPhone, tvOpenBank;
        ImageView imgSelect;

        public OpenInfoHolder(View view) {
            super(view);
            tvOpenNum = view.findViewById(R.id.tv_open_num);
            tvOpenName = view.findViewById(R.id.tv_open_name);
            tvOpenPhone = view.findViewById(R.id.tv_open_phone);
            tvOpenBank = view.findViewById(R.id.tv_open_bank);
            imgSelect = view.findViewById(R.id.img_select);
        }
    }
}
