package com.example.look.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.look.MyBaseActivity;
import com.example.look.R;
import com.example.look.bean.DataSingleton;
import com.example.look.bean.Student;
import com.example.look.bean.Student2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * desc: 数据接收方
 * <p>
 * Created by hxj on
 */
public class DataBTransferActivity extends MyBaseActivity {

    private Intent intentB;
    private TextView mContent;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_b_transfer);
        buildActionBar();
        mContent = (TextView) findViewById(R.id.tv_content);
        intentB = getIntent();
        flag = intentB.getIntExtra("flag", 1);
        switch (flag) {
            case 1:
                showView1();
                break;
        }
    }

    private void showView1() {
        String text = intentB.getStringExtra("text");
        mContent.setText(text);
    }

    private void showView2() {
        Bundle bundle = intentB.getExtras();
        ArrayList list = bundle.getStringArrayList("list");
        List<Map<String, String>> mapdata = (List<Map<String, String>>) list.get(0);
        String result = "";
        for (Map<String, String> m : mapdata) {
            for (String a : m.keySet()) {
                result = m.get(a);
            }
        }
        mContent.setText(result);
    }

    private void showView3() {
        Student student = (Student) intentB.getSerializableExtra("serializable");
        mContent.setText(student.getName());
    }

    private void showView4() {
        Student2 student2 = intentB.getParcelableExtra("parcelable");
        mContent.setText(student2.getContent());
    }

    private void showView5() {
        //接收参数
        HashMap<String, Object> map = DataSingleton.getInstance().mMap;
        String sResult = "map.size() =" + map.size();

        //遍历参数
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            sResult += (String) key;
            sResult += (String) value;
        }

        mContent.setText(sResult);
    }


}