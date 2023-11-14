package com.example.look.views;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.look.R;
import com.example.look.adpter.AboutListViewAdapter;
import com.example.look.bean.Person;

import java.util.ArrayList;
import java.util.List;

public class AboutListViewActivity extends AppCompatActivity {

    private List<Person> personList = new ArrayList<>();
    private AboutListViewAdapter listViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_list_view);
        initView();
    }

    private void initView() {

        for (int i = 0; i < 8; i++) {
            personList.add(new Person("张三" + i, "18" + i));
        }

        listViewAdapter = new AboutListViewAdapter(AboutListViewActivity.this, personList);
        ((ListView) findViewById(R.id.test_lv)).setAdapter(listViewAdapter);

    }

}