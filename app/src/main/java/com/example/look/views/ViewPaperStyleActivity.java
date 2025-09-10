package com.example.look.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.example.look.MyBaseActivity;
import com.example.look.R;
import com.example.look.adpter.ViewPaperStyleAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPaperStyleActivity extends MyBaseActivity {

    List<String> mStrings;
    List<View> mViews;
    ViewPaperStyleAdapter mAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paper_style);
        buildActionBar();
        mViewPager = (ViewPager) findViewById(R.id.papaer);
        mStrings = new ArrayList<>();

        mStrings.add("案例1");
        mStrings.add("案例2");
        mStrings.add("案例3");

        mViews = new ArrayList<>();
        LayoutInflater inflater = getLayoutInflater();
        mViews.add(inflater.inflate(R.layout.paper_view01,null,false));
        mViews.add(inflater.inflate(R.layout.paper_view02,null,false));
        mViews.add(inflater.inflate(R.layout.paper_view03,null,false));
        mAdapter = new ViewPaperStyleAdapter(mStrings,mViews);
        mViewPager.setAdapter(mAdapter);


    }
}