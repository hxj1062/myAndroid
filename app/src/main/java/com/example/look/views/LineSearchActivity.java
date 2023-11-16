package com.example.look.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.look.R;
import com.example.look.adpter.LineAddressAdapter;
import com.example.look.bean.Person;

import java.util.ArrayList;
import java.util.List;

public class LineSearchActivity extends AppCompatActivity {

    public List<Person> mDatas = new ArrayList<>();
    RecyclerView recyclerView;
    EditText fault_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_search);
        fault_search = findViewById(R.id.edt_FaultSearch);
        findViewById(R.id.tv_fault_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        recyclerView = findViewById(R.id.rl_line_plane);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        String flag = intent.getStringExtra("address_select");
        if (flag.equals("startPoint")) {
            initStartData();
            fault_search.setHint("请输入补货出发地");
            LineAddressAdapter adapter = new LineAddressAdapter(mDatas);
            recyclerView.setAdapter(adapter);
            adapter.setmOnItemClickListener(new LineAddressAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent startPoint = new Intent();
                    startPoint.putExtra("point_address", mDatas.get(position).getName());
                    setResult(16, startPoint);
                    finish();
                }
            });
        } else {
            initEndData();
            fault_search.setHint("请输入补货目的地");
            LineAddressAdapter adapter = new LineAddressAdapter(mDatas);
            recyclerView.setAdapter(adapter);
            adapter.setmOnItemClickListener(new LineAddressAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent endPoint = new Intent();
                    endPoint.putExtra("point_address", mDatas.get(position).getName());
                    setResult(18, endPoint);
                    finish();
                }
            });

        }
    }


    private void initStartData() {
        mDatas.add(new Person("龙海家园北区", "1.8KM"));
        mDatas.add(new Person("天健创智中心", "2.6KM"));
        mDatas.add(new Person("西丽湖绿岛", "3.4KM"));
    }

    private void initEndData() {
        mDatas.add(new Person("深圳仓库", "1.2KM"));
        mDatas.add(new Person("深圳东仓库", "2.1KM"));
        mDatas.add(new Person("西丽仓库", "4.2KM"));
    }


}
