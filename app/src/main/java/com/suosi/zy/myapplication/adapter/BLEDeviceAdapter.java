package com.suosi.zy.myapplication.adapter;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.suosi.zy.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by G on 2016/11/29.
 */

public class BLEDeviceAdapter extends BaseAdapter {


    private Context mContext;
    private List<BluetoothDevice> mDevices;


    public BLEDeviceAdapter(Activity mContext) {
        this.mContext = mContext;
        mDevices=new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDevices.size();
    }

    @Override
    public Object getItem(int i) {
        return mDevices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder mHolder;
        if (view==null){
            mHolder=new ViewHolder();
            view=LayoutInflater.from(mContext).inflate(R.layout.device_list_item,null);
            mHolder.name= (TextView) view.findViewById(R.id.name);
            mHolder.address= (TextView) view.findViewById(R.id.address);
            view.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) view.getTag();
        }
        String deviceName = mDevices.get(i).getName();
        if (deviceName != null && deviceName.length() > 0) {
            mHolder.name.setText(deviceName);
        }
        else {
            mHolder.name.setText("未知设备");
        }
        mHolder.address.setText(mDevices.get(i).getAddress());

        return view;
    }


    public BluetoothDevice getDevice(int position){
        return mDevices.get(position);
    }

    public void addDevice(BluetoothDevice device) {
        if(!mDevices.contains(device)) {
            mDevices.add(device);
        }
    }
    public void clear(){
        mDevices.clear();
    }

    class ViewHolder{
        private TextView name;
        private TextView address;
    }
}
