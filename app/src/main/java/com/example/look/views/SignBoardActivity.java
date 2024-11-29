package com.example.look.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.look.R;
import com.example.look.customview.SignBoardView;

public class SignBoardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_board);
        SignBoardView draw_sign = findViewById(R.id.draw_sign);
        draw_sign.start();
    }

}





