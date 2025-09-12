package com.example.look.blue

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.look.MyBaseActivity
import com.example.look.R
import com.example.look.utils.BluetoothUtils
import com.example.look.utils.LogUtil
import kotlinx.android.synthetic.main.activity_bluetooth_task.iv_refreshDevice
import kotlinx.android.synthetic.main.activity_bluetooth_task.rv_btDevice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.IOException
import java.io.OutputStream
import java.util.UUID


class BluetoothTaskActivity : MyBaseActivity() {

    // 蓝牙权限集合
    private val permissionStr: Array<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) arrayOf(
        BtPsUtil.BT_SCAN,
        BtPsUtil.BT_ADMIN,
        BtPsUtil.BLUETOOTH,
        BtPsUtil.FINE_LOCATION,
        BtPsUtil.COARSE_LOCATION
    ) else arrayOf(
        BtPsUtil.BT_ADMIN,
        BtPsUtil.BLUETOOTH,
        BtPsUtil.FINE_LOCATION,
        BtPsUtil.COARSE_LOCATION
    )

    /**
     * 设备列表数据
     */
    private val mBlueList: MutableList<BtDeviceBean> = mutableListOf()
    private lateinit var btDeviceAdapter: BtDeviceAdapter
    private var mLastState = 0
    private var mLastConnectSuccessDeviceName = ""
    private val btUtil: BtUtil by lazy { BtUtil.get() }

    /**
     * 设备mac地址
     */
    private val mBtAddressList: MutableList<String> = mutableListOf()

    /**
     * 上一次连接成功设备的位置
     */
    private var lastConnectSuccessItemPosition = 0

    /**
     * 蓝牙适配器
     */
    private lateinit var mBluetoothAdapter: BluetoothAdapter
    private var mBluetoothSocket: BluetoothSocket? = null
    private val BT_UUID = "00001101-0000-1000-8000-00805F9B34FB"

    private var mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //获取意图
            val action = intent?.action
            val bluetoothDevice = intent?.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            if (BluetoothDevice.ACTION_FOUND == action) {
                Log.d(
                    "发现蓝牙设备=",
                    "action=${action}=deviceClass=${bluetoothDevice?.bluetoothClass ?: ""}" +
                            "deviceName=${bluetoothDevice?.name ?: ""}deviceAddress=${bluetoothDevice?.address ?: ""}"
                )
                val deviceAddress = bluetoothDevice?.address ?: ""
                val name = bluetoothDevice?.name ?: ""
//                val isPrinterType = bluetoothDevice?.bluetoothClass?.deviceClass == 1664
                val supportBluetoothType = bluetoothDevice?.type == BluetoothDevice.DEVICE_TYPE_CLASSIC || bluetoothDevice?.type == BluetoothDevice.DEVICE_TYPE_DUAL
                if (deviceAddress.isNotEmpty() && name.isNotEmpty() && !mBtAddressList.contains(deviceAddress) && supportBluetoothType) {
                    mBtAddressList.add(deviceAddress)
                    var btDevice: BtDeviceBean? = null
                    //显示已配对设备
                    if (bluetoothDevice?.bondState == BluetoothDevice.BOND_BONDED) {
                        btDevice = BtDeviceBean(name, deviceAddress, 12)
                    } else if (bluetoothDevice?.bondState != BluetoothDevice.BOND_BONDED) {
                        btDevice = BtDeviceBean(name, deviceAddress, 10)
                    }
                    btDevice?.run {
                        mBlueList.add(this)
                    }
                }
                runOnUiThread() {
                    btDeviceAdapter.notifyDataSetChanged()
                }
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED == action) {
                Log.d("蓝牙设备状态=", "此意图用于状态改变")
                when (bluetoothDevice?.getBondState()) {
                    BluetoothDevice.BOND_NONE -> {
                        Log.d("蓝牙设备状态11=", "取消配对")
                    }

                    BluetoothDevice.BOND_BONDING -> {
                        Log.d("蓝牙设备状态22=", "配对中")
                    }

                    BluetoothDevice.BOND_BONDED -> {
                        Log.d("蓝牙设备状态33=", "配对成功")
                    }
                }
                //todo 此意图用于 广播配对请求
            } else if (BluetoothDevice.ACTION_PAIRING_REQUEST == action) {
                Log.d("蓝牙开始配对=", "广播配对请求action=${action}")
                try {
                    val deviceAddress = bluetoothDevice?.address ?: ""
                    //3.调用setPin方法进行配对...
                    BluetoothUtils.setPin(bluetoothDevice!!.javaClass, bluetoothDevice, "1234")
                    //1.确认配对
                    BluetoothUtils.setPairingConfirmation(
                        bluetoothDevice.javaClass,
                        bluetoothDevice,
                        true
                    )
                    abortBroadcast() //如果没有将广播终止，则会出现一个一闪而过的配对框。
                    runOnUiThread() {
                        showToast("开始配对", 0)
//                        closeProgressDialog()  新注释
                        for (device in mBlueList) {
                            if (deviceAddress == device.mBtAddress) {
                                device.mBtStatus = 12
                            }
                        }
                        btDeviceAdapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                //todo 此意图用于 表示已建立低级别（ACL）连接
            } else if (BluetoothDevice.ACTION_ACL_CONNECTED == action) {
                Log.d("", "此意图用于 表示已建立低级别（ACL）连接 action====${action}")
                val deviceAddress = bluetoothDevice?.address
                //如果上一次连接状态已配对
                if (mLastState == BluetoothDevice.BOND_BONDED) {
                    if (mBtAddressList.contains(deviceAddress)) {
                        for (device in mBlueList) {
                            if (device.mBtAddress.equals(deviceAddress)) {
                                if (device.mBtStatus === 14) {
                                    runOnUiThread {
                                        // closeProgressDialog()  新注释
                                    }
                                    return
                                } else {
                                    device.mBtStatus = 14
                                    runOnUiThread {
                                        btDeviceAdapter.notifyDataSetChanged()
                                        // closeProgressDialog()  新注释
                                    }
                                    return
                                }
                            }
                        }
                    }
                }
                //todo 此意图用于 表示与网络的低级别（ACL）断开
            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED == action) {
                Log.d("", "此意图用于 表示与网络的低级别（ACL）断开 action====${action}")
                val deviceAddress = bluetoothDevice?.address
                if (mBtAddressList.contains(deviceAddress)) {
                    for (device in mBlueList) {
                        if (device.mBtAddress.equals(deviceAddress)) {
                            if (device.mBtStatus === 12) {
                                runOnUiThread {
                                    // closeProgressDialog()  新注释
                                }
                                return
                            } else {
                                if (bluetoothDevice!!.bondState == BluetoothDevice.BOND_BONDED) {
                                    device.mBtStatus = 12
                                } else if (bluetoothDevice.bondState != BluetoothDevice.BOND_BONDED) {
                                    device.mBtStatus = 10
                                }
                                runOnUiThread {
                                    btDeviceAdapter.notifyDataSetChanged()
                                    // closeProgressDialog()  新注释
                                }
                                return
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_task)
        buildActionBar()
        setTitle("连接蓝牙设备")
        initView()
        connectBt()
        setReceiver()
        searchBtDevice()
    }


    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(this)
        rv_btDevice.setLayoutManager(linearLayoutManager)
        btDeviceAdapter = BtDeviceAdapter(mBlueList)
        rv_btDevice.setAdapter(btDeviceAdapter)
        iv_refreshDevice.setOnClickListener {
            searchBtDevice()
        }
    }

    private fun connectBt() {
        btDeviceAdapter.setOnItemClickListener { position ->

            //连接前，请终止蓝牙搜索
            if (mBluetoothAdapter != null && mBluetoothAdapter.isDiscovering) {
                mBluetoothAdapter.cancelDiscovery()
            }
//            openProgressDialog() 新注释
            val device = mBlueList[position]
            btUtil.mDevice = device

            val deviceAddress = device.mBtAddress
            var deviceStatus = device.mBtStatus
            //上一次连接时的状态
            mLastState = device.mBtStatus

            val bluetoothDevice =
                BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress)

//            //10未配对 进行配对
//            if (device.mBtStatus == BluetoothDevice.BOND_NONE) {
//                try {
//                    // 与设备配对
//                    if (BluetoothUtils.createBond(bluetoothDevice.javaClass, bluetoothDevice)) {
//                        deviceStatus = 12
//                        device.mBtStatus = 12
//                        runOnUiThread {
//                            showToast("开始配对", 0)
//                        }
//                    } else {
//                        deviceStatus = 10
//                        device.mBtStatus = 10
//                        runOnUiThread {
//                            showToast("配对失败", 0)
//                        }
//                    }
//                } catch (e: java.lang.Exception) {
//                    e.printStackTrace()
//                }
//            }

            //12已配对 进行连接
//            if (deviceStatus == BluetoothDevice.BOND_BONDED) {
            connectBt(bluetoothDevice)
            device.mBtStatus = 14
//            } else {
//                showToast("连接失败", 0)
//            }
        }
        btDeviceAdapter.notifyDataSetChanged()
    }


    private fun setReceiver() {
        //发现远程设备。
        val mFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        //表示已建立低级别（ACL）连接通过远程设备建立
        mFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        //表示与网络的低级别（ACL）断开远程设备
        mFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //此意图用于广播配对请求
            mFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST)
        }
        registerReceiver(mReceiver, mFilter)
    }


    /**
     * 初始化打印控件    YXM5622_99903097
     */
    private fun searchBtDevice() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
            requestEscortPermission(
                object : BtPsUtil.BtPsCallBack {
                    override fun allowProcess(permission: String) {
                        //连接前，请终止蓝牙搜索
                        if (mBluetoothAdapter != null && mBluetoothAdapter.isDiscovering) {
                            mBluetoothAdapter.cancelDiscovery()
                        }
                        runOnUiThread() {
                            mBlueList.clear()
                            mBtAddressList.clear()
                            lastConnectSuccessItemPosition = -1
                            val pairedDevices = mBluetoothAdapter.bondedDevices
                            if (pairedDevices.size > 0) {
                                for (device in pairedDevices) {
                                    val deviceAddress = device.address
                                    val deviceName = device.name
                                    mBtAddressList.add(deviceAddress)
                                    var printer: BtDeviceBean
                                    if (!mBtAddressList.contains(deviceAddress) && !deviceName.isNullOrEmpty()) {
                                        mBtAddressList.add(deviceAddress)
                                        if (mLastConnectSuccessDeviceName.equals(deviceName)) {
                                            // 10未配对 12已配对 14已连接
                                            printer = BtDeviceBean(
                                                deviceName,
                                                deviceAddress,
                                                14
                                            )
                                        } else {
                                            printer = BtDeviceBean(
                                                device.getName(),
                                                device.getAddress(),
                                                BluetoothDevice.BOND_BONDED
                                            )
                                        }
                                        mBlueList.add(printer)
                                    }

                                }
                            }
                            Handler().postDelayed({
                                btDeviceAdapter.notifyDataSetChanged()
                            }, 2600)
                        }
                        mBluetoothAdapter.startDiscovery()
                    }

                    override fun refuseProcess(permission: String?) {

                    }
                },
                *permissionStr
            )
        } else {
            showToast("请开启蓝牙", 0)
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, 100)
        }
    }


    /**
     * 连接 （在配对之后调用）
     * @param device
     */
    private fun connectBt(device: BluetoothDevice) {
        if (mBluetoothAdapter!!.isDiscovering) {
            mBluetoothAdapter!!.cancelDiscovery()
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    // createInsecureRfcommSocketToServiceRecord 选这个
                    // createRfcommSocketToServiceRecord
                    mBluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(BT_UUID))
                    if (mBluetoothSocket != null && !mBluetoothSocket!!.isConnected) {
                        mBluetoothSocket!!.connect()
                        //  showToast("设备连接成功", 0)
                        Log.d("蓝牙socket=", "设备连接成功")
                    }
                } catch (e: IOException) {
                    // showToast("设备连接失败", 0)
                    LogUtil.logStack(e, "蓝牙socket")
                    Log.d("蓝牙socket=", "设备连接失败")
                    try {
                        mBluetoothSocket!!.close()
                    } catch (e1: IOException) {
                        e1.printStackTrace()
                        Log.d("蓝牙socket=", "socket关闭失败")
                        // showToast("socket关闭失败", 0)
                    }
                }
            }
        }
    }

    /**
     * 读取数据
     */
    private suspend fun readDataFromSocket(): String {
        val sb = StringBuffer()
        var inputStream: BufferedInputStream? = null
        try {
            inputStream = BufferedInputStream(mBluetoothSocket!!.inputStream)
            var length = 0;
            val buf = ByteArray(1024)

            while ((inputStream.read(buf)).also { length = it } != -1) {
                sb.append(String(buf, 0, length));
            }
            return sb.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream!!.close();
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return ""
    }

    /**
     * 写数据
     */
    private suspend fun writeDataToSocket(str: String) {
        var outputstream: OutputStream? = null
        try {
            outputstream = mBluetoothSocket!!.outputStream
            outputstream.write(str.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                outputstream!!.close();
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            searchBtDevice()
        }
    }


    override fun finish() {
        btUtil.defaultStartActivity = true
        super.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }
}




