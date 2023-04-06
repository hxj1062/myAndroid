package com.example.look.views;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.os.Bundle;

import com.example.demo.R;

public class BlueToothActivity extends AppCompatActivity {

    // 控制蓝牙的服务代理
    private BluetoothHeadset mBluetoothHeadset ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);
    }

    private void blueToothSetting(){
        // 获取蓝牙适配器
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // 设置 BluetoothProfile.ServiceListener,该监听器,会在客户端连接和断连时发出通知


    }

    private BluetoothProfile.ServiceListener profileListener = new BluetoothProfile.ServiceListener() {
        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
           if(profile ==BluetoothProfile.HEADSET){
               mBluetoothHeadset = (BluetoothHeadset)proxy ;
           }
        }

        @Override
        public void onServiceDisconnected(int profile) {
            if (profile == BluetoothProfile.HEADSET) {
                mBluetoothHeadset = null;
            }
        }
    } ;


}