package com.example.look.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_search);
        initDatas();
        recyclerView = findViewById(R.id.rl_line_plane);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new LineAddressAdapter(mDatas));
        findViewById(R.id.tv_fault_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LineSearchActivity.this, ConfigureRouteActivity.class));
            }
        });
    }


    private void initDatas() {
        mDatas.add(new Person("龙海家园北区", "1.8KM"));
        mDatas.add(new Person("天健创智中心", "2.6KM"));
        mDatas.add(new Person("西丽湖绿岛", "3.4KM"));
    }
}
