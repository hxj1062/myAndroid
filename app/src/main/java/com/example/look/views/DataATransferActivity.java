package com.example.look.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.look.R;
import com.example.look.bean.DataSingleton;
import com.example.look.bean.Student;
import com.example.look.bean.Student2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataATransferActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_a_transfer);

        findViewById(R.id.btn_simple).setOnClickListener(this);
        findViewById(R.id.btn_complex).setOnClickListener(this);
        findViewById(R.id.btn_Serializable).setOnClickListener(this);
        findViewById(R.id.btn_Parcelable).setOnClickListener(this);
        findViewById(R.id.btn_Singleton).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        Intent intentA = new Intent(DataATransferActivity.this, DataBTransferActivity.class);
        switch (view.getId()) {
            case R.id.btn_simple:
                testSimpleData(intentA);
                break;
            case R.id.btn_complex:
                testComplexData(intentA);
                break;
            case R.id.btn_Serializable:
                break;
            case R.id.btn_Parcelable:
                break;
            case R.id.btn_Singleton:
                break;
            default:
                break;
        }
    }

    private void testSimpleData(Intent intent) {

        intent.putExtra("flag", 1);
        intent.putExtra("text", "利用Intent对象携带简单数据");

//        Bundle bundleA = new Bundle();
//        bundleA.putInt("flag",1);
//        bundleA.putString("name","bundleA方式传递数据");
//        intentA.putExtras(bundleA);

        startActivity(intent);
    }

    private void testComplexData(Intent intent) {

        Map<String,String> strMap = new HashMap<>();
        strMap.put("flag","2");
        strMap.put("str","利用Intent对象携带如ArrayList之类复杂些的数据");
        List<Map<String ,String>> list = new ArrayList<>();

        Bundle bundleList = new Bundle();
        // 在bundle中携带集合数据时,必须先定义一个 ArrayList<String> 类型
        ArrayList arrayList = new ArrayList();
        arrayList.add(list);

        bundleList.putStringArrayList("list",arrayList);
        intent.putExtras(bundleList);
        startActivity(intent);

    }

    private void testSerializableData(Intent intent) {
        Student student = new Student();
        student.setAge(19);
        student.setName("通过Serializable传递对象");
        intent.putExtra("serializable",student);
        startActivity(intent);
    }

    private void testParcelableData(Intent intent) {
        Student2 student2 = new Student2();
        student2.setAge(18);
        student2.setContent("通过Parcelable传递对象");
        intent.putExtra("parcelable",student2);
        startActivity(intent);
    }

    private void testSingletonData(Intent intent) {
        DataSingleton.getInstance().put("key1", "value1");
        DataSingleton.getInstance().put("key2", "value2");

        startActivity(intent);
    }
}