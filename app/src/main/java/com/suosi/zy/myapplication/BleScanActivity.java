package com.suosi.zy.myapplication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.suosi.zy.myapplication.adapter.BLEDeviceAdapter;

import java.util.ArrayList;
import java.util.List;

public class BleScanActivity extends AppCompatActivity {


    private Button btn_scan, btn_stop;
    private ListView mListView;

    private BLEDeviceAdapter bleDeviceAdapter;

    private BluetoothAdapter mBluetoothAdapter;

    private static final int REQUEST_ENABLE_BT = 1;

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    private Handler mHandler;

    private boolean mScanning;


    // 扫描蓝牙回调
    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice bluetoothDevice, int rssi, byte[] bytes) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bleDeviceAdapter.addDevice(bluetoothDevice);
                    bleDeviceAdapter.notifyDataSetChanged();
                }
            });


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        initble();
        initView();
        initEvent();
    }

    private void initble() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "不支持ble", Toast.LENGTH_LONG).show();
            finish();
        }

        final BluetoothManager blutoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = blutoothManager.getAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "不支持蓝牙", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    private void initEvent() {
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bleDeviceAdapter.clear();

                scanBleDevice(true);
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanBleDevice(false);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BluetoothDevice device = bleDeviceAdapter.getDevice(i);
                Intent mIntent = new Intent(BleScanActivity.this, BleControlActivity.class);
                mIntent.putExtra(BleControlActivity.EXTRAS_DEVICE_NAME, device.getName());
                mIntent.putExtra(BleControlActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
                if (mScanning) {
                    mBluetoothAdapter.stopLeScan(leScanCallback);
                    mScanning = false;
                }
                startActivity(mIntent);
            }
        });
    }

    private void initView() {
        btn_scan = (Button) findViewById(R.id.btn_scan);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        mListView = (ListView) findViewById(R.id.lv);

    }


    @Override
    protected void onResume() {
        super.onResume();
        //有没有打开蓝牙
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
        //
        bleDeviceAdapter = new BLEDeviceAdapter(this);
        mListView.setAdapter(bleDeviceAdapter);
        scanBleDevice(true);
    }

    private void scanBleDevice(boolean enable) {

        if (enable) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(leScanCallback);
                }
            }, SCAN_PERIOD);
            mScanning = true;
            mBluetoothAdapter.startLeScan(leScanCallback);

        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(leScanCallback);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanBleDevice(false);
        bleDeviceAdapter.clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}


