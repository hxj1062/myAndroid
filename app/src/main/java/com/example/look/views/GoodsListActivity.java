package com.example.look.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.look.R;

import java.util.ArrayList;
import java.util.List;

public class GoodsListActivity extends AppCompatActivity {

    public static class TestModel {
        public String name;
        public String age;
        public List<TestChild> childList;

        public TestModel(String name, String age, List<TestChild> childList) {
            this.name = name;
            this.age = age;
            this.childList = childList;
        }

        public static class TestChild {
            public String child = "";

            public TestChild(String child) {
                this.child = child;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);

        ListView lvGoods = findViewById(R.id.lv_goods);

        List<TestModel> datas = new ArrayList<TestModel>();
        for (int i = 0; i <= 3; i++) {
            List<TestModel.TestChild> childList = new ArrayList<TestModel.TestChild>();
            childList.add(new TestModel.TestChild("1"));
            childList.add(new TestModel.TestChild("2"));
            datas.add(new TestModel(i + "name", i + "", childList));
        }

        lvGoods.setAdapter(new GoodsListAdapter(datas, this));
    }


    class GoodsListAdapter extends BaseAdapter {

        Context context;
        List<TestModel> datas;


        public GoodsListAdapter(List<TestModel> datas, Context context) {
            this.datas = datas;
            this.context = context;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public TestModel getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TestModel model = getItem(position);

//            ListViewHolder vh;
//            if (convertView == null) {
//                convertView = LayoutInflater.from(context).inflate(R.layout.item_order_info, null);
//                vh = new ListViewHolder();
//                vh.tvTime = convertView.findViewById(R.id.tv_time);
//                vh.mLayout = convertView.findViewById(R.id.listLayout);
//                convertView.setTag(vh);
//                vh.mLayout.setTag(model);
//            }
//            else {
//                vh = (ListViewHolder) convertView.getTag();
//            }

            convertView = LayoutInflater.from(context).inflate(R.layout.item_order_info, null);
            TextView tvTime = convertView.findViewById(R.id.tv_time);
            LinearLayout mLayout = convertView.findViewById(R.id.listLayout);
            tvTime.setText(model.name);
            for (TestModel.TestChild testChild : model.childList) {
                @SuppressLint("ViewHolder") View view = LayoutInflater.from(context).inflate(R.layout.item_goods_info, null);
                mLayout.addView(view);
            }

            return convertView;

        }


//        private final class ListViewHolder {
//            public TextView tvTime;
//            public LinearLayout mLayout;
//        }
    }
}