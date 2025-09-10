package com.example.look.blue

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.look.MyBaseActivity
import com.example.look.R
import com.example.look.utils.BluetoothUtils
import kotlinx.android.synthetic.main.activity_bluetooth_task.iv_refreshDevice
import kotlinx.android.synthetic.main.activity_bluetooth_task.rv_btDevice


class BluetoothTaskActivity : MyBaseActivity() {
    /**
     * 设备列表数据
     */
    private val mDeviceList: MutableList<BtDeviceBean> = mutableListOf()
    private lateinit var mDeviceAdapter: BtDeviceAdapter
    private var mLastState = 0
    private var mLastConnectSuccessDeviceName = ""
    private val btUtil: BtUtil by lazy { BtUtil.get() }
    private var vmCode = ""
    private var vmAddress = ""
    private var vmTypeId = ""

    /**
     * 蓝牙适配器
     */
    private lateinit var mBluetoothAdapter: BluetoothAdapter
    private var mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //获取意图
            val action = intent?.action
            var bluetoothDevice =
                intent?.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)

            //todo 发现设备
            if (BluetoothDevice.ACTION_FOUND == action) {
                Log.d(
                    "",
                    "此意图用于 发现设备 action==${action} deviceClass==${bluetoothDevice?.bluetoothClass ?: ""} " +
                            "deviceName=${bluetoothDevice?.name ?: ""} deviceAddress=${bluetoothDevice?.address ?: ""}"
                )
                val deviceAddress = bluetoothDevice?.address ?: ""
                val name = bluetoothDevice?.name ?: ""
                val isPrinterType = bluetoothDevice?.bluetoothClass?.deviceClass == 1664
                var supportBluetoothType =
                    bluetoothDevice?.type == BluetoothDevice.DEVICE_TYPE_CLASSIC || bluetoothDevice?.type == BluetoothDevice.DEVICE_TYPE_DUAL
                if (!deviceAddress.isNullOrEmpty() && !name.isNullOrEmpty() && isPrinterType &&
                    !mDeviceAddressList.contains(deviceAddress) && supportBluetoothType
                ) {

                    mDeviceAddressList.add(deviceAddress)
                    var device: BtDeviceBean? = null
                    //显示已配对设备
                    if (bluetoothDevice?.bondState == BluetoothDevice.BOND_BONDED) {
                        device = BtDeviceBean(name, deviceAddress, 12)
                    } else if (bluetoothDevice?.bondState != BluetoothDevice.BOND_BONDED) {
                        device = BtDeviceBean(name, deviceAddress, 10)
                    }
                    device?.run {
                        mDeviceList.add(this)
                    }
                }
                runOnUiThread() {
                    mDeviceAdapter.notifyDataSetChanged()
                }
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED == action) {
                Log.d("", "此意图用于 状态改变")
                when (bluetoothDevice?.getBondState()) {
                    BluetoothDevice.BOND_NONE -> {
                        Log.d("", "此意图用于 取消配对")
                    }

                    BluetoothDevice.BOND_BONDING -> {
                        Log.d("", "此意图用于 配对中")
                    }

                    BluetoothDevice.BOND_BONDED -> {
                        Log.d("", "此意图用于 配对成功")
                    }
                }


                //todo 此意图用于 广播配对请求
            } else if (BluetoothDevice.ACTION_PAIRING_REQUEST == action) {
                Log.d("", "此意图用于 广播配对请求 action====${action}")

                try {
                    val deviceAddress = bluetoothDevice?.address ?: ""
                    //3.调用setPin方法进行配对...
                    BluetoothUtils.setPin(bluetoothDevice!!.javaClass, bluetoothDevice, "0000")
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
                        for (device in mDeviceList) {
                            if (deviceAddress == device.mBtAddress) {
                                device.mBtStatus = 12
                            }
                        }
                        mDeviceAdapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }

                //todo 此意图用于 表示已建立低级别（ACL）连接
            } else if (BluetoothDevice.ACTION_ACL_CONNECTED == action) {
                Log.d("", "此意图用于 表示已建立低级别（ACL）连接 action====${action}")
                val deviceAddress = bluetoothDevice?.address
                //如果上一次连接状态已配对
                if (mLastState == BluetoothDevice.BOND_BONDED) {
                    if (mDeviceAddressList.contains(deviceAddress)) {
                        for (device in mDeviceList) {
                            if (device.mBtAddress.equals(deviceAddress)) {
                                if (device.mBtStatus === 14) {
                                    runOnUiThread {
                                        // closeProgressDialog()  新注释
                                    }
                                    return
                                } else {
                                    device.mBtStatus = 14
                                    runOnUiThread {
                                        mDeviceAdapter.notifyDataSetChanged()
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
                if (mDeviceAddressList.contains(deviceAddress)) {
                    for (device in mDeviceList) {
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
                                    mDeviceAdapter.notifyDataSetChanged()
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

        intent?.apply {
            vmCode = this.getStringExtra("vmCode") ?: ""
            vmAddress = this.getStringExtra("vmAddress") ?: ""
            vmTypeId = this.getStringExtra("vmTypeId") ?: ""
        }
        initView()
        initEvent()
        setReceiver()
        searchBluetoothDevice()
    }

    override fun finish() {
        btUtil.defaultStartActivity = true
        super.finish()
    }

    fun initEvent() {
        mDeviceAdapter.setOnItemClickListener { position ->

            //连接前，请终止蓝牙搜索
            if (mBluetoothAdapter != null && mBluetoothAdapter.isDiscovering) {
                mBluetoothAdapter.cancelDiscovery()
            }

//            openProgressDialog() 新注释
            val device = mDeviceList[position]
            btUtil.mDevice = device

            val deviceAddress = device.mBtAddress
            val deviceStatus = device.mBtStatus
            //上一次连接时的状态
            mLastState = device.mBtStatus

            val bluetoothDevice =
                BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress)

            //未配对 进行配对
            if (device.mBtStatus == BluetoothDevice.BOND_NONE) {
                try {
                    // 与设备配对
                    if (BluetoothUtils.createBond(bluetoothDevice.javaClass, bluetoothDevice)) {
                        runOnUiThread {
                            showToast("开始配对", 0)
                        }
                    } else {
                        runOnUiThread {
                            showToast("配对失败", 0)
                        }
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

            //已配对 进行连接
            if (deviceStatus == BluetoothDevice.BOND_BONDED) {

            } else {
                showToast("连接失败", 0)
            }
        }


        mDeviceAdapter.notifyDataSetChanged()
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }

    private fun setReceiver() {
        //todo 发现远程设备。
        val mFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        //todo 表示已建立低级别（ACL）连接通过远程设备建立
        mFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        //todo 表示与网络的低级别（ACL）断开远程设备
        mFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //todo 此意图用于广播配对请求
            mFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST)
        }

        registerReceiver(mReceiver, mFilter)
    }

    /**
     * 设备mac地址
     */
    private val mDeviceAddressList: MutableList<String> = mutableListOf()

    /**
     * 上一次连接成功设备的位置
     */
    private var mLastConnectSuccessItemPosition = 0


    // 蓝牙权限集合
    private val permissionStr: Array<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) arrayOf(
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_ADMIN
    ) else arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_ADMIN
    )

    /**
     * 初始化打印控件
     */
    private fun searchBluetoothDevice() {
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
//                            openProgressDialog()  新注释
                            mDeviceList.clear()
                            mDeviceAddressList.clear()
                            mLastConnectSuccessItemPosition = -1
                            val pairedDevices = mBluetoothAdapter.bondedDevices
                            if (pairedDevices.size > 0) {
                                Log.d("", "此意图用于 pairedDevices==${pairedDevices}")
                                for (device in pairedDevices) {
                                    val deviceAddress = device.address
                                    val deviceName = device.name
                                    val isPrinterType = device.bluetoothClass.deviceClass == 1664
                                    var printer: BtDeviceBean
                                    if (!mDeviceAddressList.contains(deviceAddress) && !deviceName.isNullOrEmpty()
                                        && isPrinterType
                                    ) {
                                        mDeviceAddressList.add(deviceAddress)
                                        if (!mLastConnectSuccessDeviceName.isEmpty()
                                            && mLastConnectSuccessDeviceName.equals(deviceName)
//                                            && btUtil.mJCAPI.isConnection() == 0
                                        ) {
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
                                        mDeviceList.add(printer)
                                    }
                                }
                            }

                            Handler().postDelayed({
//                                closeProgressDialogAssert()  新注释
                                mDeviceAdapter.notifyDataSetChanged()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            searchBluetoothDevice()
        }
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(this)
        rv_btDevice.setLayoutManager(linearLayoutManager)
        mDeviceAdapter = BtDeviceAdapter(mDeviceList)
        rv_btDevice.setAdapter(mDeviceAdapter)
        iv_refreshDevice.setOnClickListener {
            searchBluetoothDevice()
        }
    }

}




