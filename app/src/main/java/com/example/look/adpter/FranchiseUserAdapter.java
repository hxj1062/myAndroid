package com.example.look.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.look.R;
import com.example.look.bean.FranchiseBean;

import java.util.List;

public class FranchiseUserAdapter extends BaseAdapter {

    private List<FranchiseBean> testData;
    Context myContext;

    public FranchiseUserAdapter(List<FranchiseBean> list, Context context) {
        myContext = context;
        testData = list;
    }

    public List<FranchiseBean> getAccountData() {
        return testData;
    }

    public void setAccountData(List<FranchiseBean> data) {
        testData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return testData.size();
    }

    @Override
    public FranchiseBean getItem(int position) {
        return testData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JiaHoler holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(myContext).inflate(R.layout.item_account, null);
            holder = new JiaHoler(convertView);
            convertView.setTag(holder);
        } else {
            holder = (JiaHoler) convertView.getTag();
        }
        final FranchiseBean info = testData.get(position);
        holder.tvOpenNum.setText("客户编号:" + info.num);
        holder.tvOpenName.setText(info.name);
        String str = info.flag == 0 ? "(主)" : "(子)";
        holder.tvFlag.setText(str);
        holder.imgSelect.setImageDrawable(info.selectState ? myContext.getDrawable(R.drawable.ico_checkbox_high) : myContext.getDrawable(R.drawable.ico_checkbox_normal));
        return convertView;
    }

    static class JiaHoler extends RecyclerView.ViewHolder {

        TextView tvOpenNum, tvOpenName, tvFlag;
        ImageView imgSelect;

        public JiaHoler(View view) {
            super(view);
            tvOpenNum = view.findViewById(R.id.tvGuestNo);
            tvOpenName = view.findViewById(R.id.tvGuestName);
            tvFlag = view.findViewById(R.id.tvGuestFlag);
            imgSelect = view.findViewById(R.id.imgGuestSelect);
        }
    }
}

