package com.example.look.views;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.look.R;

import java.util.List;

public class LinearLayoutListView {

    private LinearLayout listLayout;
    private Context context;
    List<GoodsListActivityMy.TestModel.TestChild> goodsInfo;


    public LinearLayoutListView(LinearLayout listLayout, List<GoodsListActivityMy.TestModel.TestChild> data) {
        this.listLayout = listLayout;
        this.context = listLayout.getContext();
        this.goodsInfo = data;
        for (int i = 0; i < goodsInfo.size(); i++) {
            initView(goodsInfo.get(i).child);
        }
    }

    private void initView(String goodsInfo) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_goods_info, listLayout, false);
        TextView goodsName = view.findViewById(R.id.tv_goods_name);
        goodsName.setText(goodsInfo);
        TextView goodsPrice = view.findViewById(R.id.tv_goods_price);
        goodsPrice.setText(goodsInfo);
        TextView goodsNum = view.findViewById(R.id.tv_goods_num);
        goodsNum.setText(goodsInfo);
        ImageView goodsImg = view.findViewById(R.id.iv_orderic);
        goodsImg.setBackgroundColor(Color.BLUE);
        listLayout.addView(view);
    }

    private View onCreateView(int layout) {
        return LayoutInflater.from(context).inflate(layout, listLayout, false);
    }
}
