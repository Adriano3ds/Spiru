package ib2.spiru;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;

import java.util.ArrayList;
import java.util.List;

public class DeviceAdapter extends BaseAdapter {
    private List<BleDevice> bleDeviceList = new ArrayList();
    private Context context;
    /* access modifiers changed from: private */
    public OnDeviceClickListener mListener;

    public interface OnDeviceClickListener {
        void onConnect(BleDevice bleDevice);

        void onDetail(BleDevice bleDevice);

        void onDisConnect(BleDevice bleDevice);
    }

    public DeviceAdapter(Context context2) {
        this.context = context2;
    }

    public void addDevice(BleDevice bleDevice) {
        removeDevice(bleDevice);
        this.bleDeviceList.add(bleDevice);
    }

    public void removeDevice(BleDevice bleDevice) {
        for (int i = 0; i < this.bleDeviceList.size(); i++) {
            if (bleDevice.getKey().equals(this.bleDeviceList.get(i).getKey())) {
                this.bleDeviceList.remove(i);
            }
        }
    }

    public void clearConnectedDevice() {
        for (int i = 0; i < this.bleDeviceList.size(); i++) {
            if (BleManager.getInstance().isConnected(this.bleDeviceList.get(i))) {
                this.bleDeviceList.remove(i);
            }
        }
    }

    public void clearScanDevice() {
        for (int i = 0; i < this.bleDeviceList.size(); i++) {
            if (!BleManager.getInstance().isConnected(this.bleDeviceList.get(i))) {
                this.bleDeviceList.remove(i);
            }
        }
    }

    public void clear() {
        clearConnectedDevice();
        clearScanDevice();
    }

    public int getCount() {
        return this.bleDeviceList.size();
    }

    public BleDevice getItem(int position) {
        if (position > this.bleDeviceList.size()) {
            return null;
        }
        return this.bleDeviceList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = View.inflate(this.context, R.layout.adapter_device, (ViewGroup) null);
            holder = new ViewHolder();
            convertView.setTag(holder);
//            holder.img_blue = (ImageView) convertView.findViewById(C0242R.C0244id.img_blue);
            holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txt_mac = (TextView) convertView.findViewById(R.id.txt_mac);
            holder.txt_rssi = (TextView) convertView.findViewById(R.id.txt_rssi);
            holder.layout_idle = (LinearLayout) convertView.findViewById(R.id.layout_idle);
            holder.layout_connected = (LinearLayout) convertView.findViewById(R.id.layout_connected);
            holder.btn_disconnect = (Button) convertView.findViewById(R.id.btn_disconnect);
            holder.btn_connect = (Button) convertView.findViewById(R.id.btn_connect);
            holder.btn_detail = (Button) convertView.findViewById(R.id.btn_detail);
        }
        final BleDevice bleDevice = getItem(position);
        if (bleDevice != null) {
            boolean isConnected = BleManager.getInstance().isConnected(bleDevice);
            String name = bleDevice.getName();
            if (name == null) {
                name = "Sem nome";
            }
            String mac = bleDevice.getMac();
            int rssi = bleDevice.getRssi();
            holder.txt_name.setText(name);
            holder.txt_mac.setText(mac);
            holder.txt_rssi.setText(String.valueOf(rssi));
            if (isConnected) {
//                holder.img_blue.setImageResource(C0242R.mipmap.ic_blue_connected);
                holder.txt_name.setTextColor(-14816842);
                holder.txt_mac.setTextColor(-14816842);
                holder.layout_idle.setVisibility(View.GONE);
                holder.layout_connected.setVisibility(View.VISIBLE);
            } else {
//                holder.img_blue.setImageResource(C0242R.mipmap.ic_blue_remote);
                holder.txt_name.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                holder.txt_mac.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                holder.layout_idle.setVisibility(View.VISIBLE);
                holder.layout_connected.setVisibility(View.GONE);
            }
        }
        holder.btn_connect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (DeviceAdapter.this.mListener != null) {
                    DeviceAdapter.this.mListener.onConnect(bleDevice);
                }
            }
        });
        holder.btn_disconnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (DeviceAdapter.this.mListener != null) {
                    DeviceAdapter.this.mListener.onDisConnect(bleDevice);
                }
            }
        });
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (DeviceAdapter.this.mListener != null) {
                    DeviceAdapter.this.mListener.onDetail(bleDevice);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        Button btn_connect;
        Button btn_detail;
        Button btn_disconnect;
//        ImageView img_blue;
        LinearLayout layout_connected;
        LinearLayout layout_idle;
        TextView txt_mac;
        TextView txt_name;
        TextView txt_rssi;

        ViewHolder() {
        }
    }

    public void setOnDeviceClickListener(OnDeviceClickListener listener) {
        this.mListener = listener;
    }
}