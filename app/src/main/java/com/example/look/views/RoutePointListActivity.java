package com.example.look.views;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.look.MyBaseActivity;
import com.example.look.R;
import com.example.look.adpter.RoutePointListAdapter;

public class RoutePointListActivity extends MyBaseActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_point_list);
        buildActionBar();
        recyclerView = findViewById(R.id.rl_route_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RoutePointListAdapter());
    }
}
