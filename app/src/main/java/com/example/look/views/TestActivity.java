package com.example.look.views;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.look.R;
import com.example.look.utils.CommonUtils;

public class TestActivity extends AppCompatActivity {

    private int listabc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_two, menu);
        MenuItem item = menu.findItem(R.id.menu_two_01);
        View abc = item.getActionView();

//        tvLine = abc.findViewById(R.id.tv_line);
//        abc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isChange) {
//                    isChange = false;
//                    tvLine.setText("孙悟空");
//                } else {
//                    isChange = true;
//                    tvLine.setText("唐三藏");
//                }
//            }
//        });

        if (listabc == 1) {
            // 假设menu中有一个特定的项的ID是R.id.some_menu_item
            menu.findItem(R.id.menu_two_01).setVisible(true);
        } else {
            menu.findItem(R.id.menu_two_01).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_two_01) {
            CommonUtils.showToast(this, "右上角机器配置");
        }
        return super.onOptionsItemSelected(item);
    }

}