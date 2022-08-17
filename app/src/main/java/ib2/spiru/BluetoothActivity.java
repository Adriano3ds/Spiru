package ib2.spiru;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleIndicateCallback;
import com.clj.fastble.callback.BleMtuChangedCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleReadCallback;
import com.clj.fastble.callback.BleRssiCallback;
import com.clj.fastble.callback.BleScanAndConnectCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity {

    private ListView listView;
    public DeviceAdapter mDeviceAdapter;
    public static final String TAG = BluetoothActivity.class.getSimpleName();

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        listView = findViewById(R.id.listView);
        this.mDeviceAdapter = new DeviceAdapter(this);
        this.mDeviceAdapter.setOnDeviceClickListener(new DeviceAdapter.OnDeviceClickListener() {
            public void onConnect(BleDevice bleDevice) {
                if (!BleManager.getInstance().isConnected(bleDevice)) {
                    BleManager.getInstance().cancelScan();
                    BluetoothActivity.this.connect(bleDevice);
                }
            }

            public void onDisConnect(BleDevice bleDevice) {
                if (BleManager.getInstance().isConnected(bleDevice)) {
                    BleManager.getInstance().disconnect(bleDevice);
                }
            }

            public void onDetail(BleDevice bleDevice) {
                if (BleManager.getInstance().isConnected(bleDevice)) {
//                    Intent intent = new Intent(this, OperationActivity.class);
//                    intent.putExtra(OperationActivity.KEY_DATA, bleDevice);
//                    BluetoothActivity.this.startActivity(intent);
                }
            }
        });
        listView.setAdapter(this.mDeviceAdapter);


        BleManager.getInstance().init(getApplication());
        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setSplitWriteNum(20)
                .setConnectOverTime(20000)
                .setOperateTimeout(5000);
        Log.i("BLE", "Bluetooth: " + BleManager.getInstance().isBlueEnable());
        if(!BleManager.getInstance().isBlueEnable()){
            BleManager.getInstance().enableBluetooth();
        }
        checkPermissions();

    }

    public void connect(BleDevice bleDevice) {
        BleManager.getInstance().connect(bleDevice, (BleGattCallback) new BleGattCallback() {
            public void onStartConnect() {
//                MainActivity.this.progressDialog.show();
            }

            public void onConnectFail(BleDevice bleDevice, BleException exception) {
//                this.img_loading.clearAnimation();
//                this.img_loading.setVisibility(4);
//                MainActivity.this.btn_scan.setText(MainActivity.this.getString(C0242R.string.start_scan));
//                MainActivity.this.progressDialog.dismiss();
                Toast.makeText(BluetoothActivity.this, "Falhou ao conectar", Toast.LENGTH_LONG).show();
            }

            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
//                MainActivity.this.progressDialog.dismiss();
                BluetoothActivity.this.mDeviceAdapter.addDevice(bleDevice);
                BluetoothActivity.this.mDeviceAdapter.notifyDataSetChanged();
            }

            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
//                MainActivity.this.progressDialog.dismiss();
                BluetoothActivity.this.mDeviceAdapter.removeDevice(bleDevice);
                BluetoothActivity.this.mDeviceAdapter.notifyDataSetChanged();
                if (isActiveDisConnected) {
                    Toast.makeText(BluetoothActivity.this, "Conectador", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(BluetoothActivity.this, "Desconectado", Toast.LENGTH_LONG).show();
                ObserverManager.getInstance().notifyObserver(bleDevice);
            }
        });
    }

    private void readRssi(BleDevice bleDevice) {
        BleManager.getInstance().readRssi(bleDevice, new BleRssiCallback() {
            public void onRssiFailure(BleException exception) {
                Log.i(BluetoothActivity.TAG, "onRssiFailure" + exception.toString());
            }

            public void onRssiSuccess(int rssi) {
                Log.i(BluetoothActivity.TAG, "onRssiSuccess: " + rssi);
            }
        });
    }

    private void setMtu(BleDevice bleDevice, int mtu) {
        BleManager.getInstance().setMtu(bleDevice, mtu, new BleMtuChangedCallback() {
            public void onSetMTUFailure(BleException exception) {
                Log.i(BluetoothActivity.TAG, "onsetMTUFailure" + exception.toString());
            }

            public void onMtuChanged(int mtu) {
                Log.i(BluetoothActivity.TAG, "onMtuChanged: " + mtu);
            }
        });
    }

    public void startScan(){
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                Log.i("BLE", "STARTED: ");
            }

            @Override
            public void onScanning(BleDevice bleDevice) {
                Log.i("BLE", "ON SCANNING: " + bleDevice.getName() + " " + bleDevice.getMac());
                BluetoothActivity.this.mDeviceAdapter.addDevice(bleDevice);
                BluetoothActivity.this.mDeviceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
                Log.i("BLE", "SCAN FINISHED: ");
                for(BleDevice bleDevice : scanResultList){
                    Log.i("BLE", "DEVICES: " + bleDevice.getName()+ " " + bleDevice.getMac());
//                    Toast.makeText(BluetoothActivity.this, " " + bleDevice.getMac(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == 0) {
                            onPermissionGranted(permissions[i]);
                        }
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void checkPermissions() {
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            Toast.makeText(this, "Please Enable Bluetooth", Toast.LENGTH_SHORT).show();
            return;
        }
        List<String> permissionDeniedList = new ArrayList<>();
        for (String permission : new String[]{"android.permission.ACCESS_FINE_LOCATION"}) {
            if (ContextCompat.checkSelfPermission(this, permission) == 0) {
                onPermissionGranted(permission);
            } else {
                permissionDeniedList.add(permission);
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            ActivityCompat.requestPermissions(this, (String[])
                    permissionDeniedList.toArray(new String[permissionDeniedList.size()]), 2);
        }
    }

    private void onPermissionGranted(String permission) {
        char c = 65535;
        switch (permission.hashCode()) {
            case -1888586689:
                if (permission.equals("android.permission.ACCESS_FINE_LOCATION")) {
                    c = 0;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                if (Build.VERSION.SDK_INT < 23 || checkGPSIsOpen()) {
                    setScanRule();
                    startScan();
                    return;
                }
                new AlertDialog.Builder(this).setTitle("Titulo").setMessage("Mensagem").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        BluetoothActivity.this.finish();
                    }
                }).setPositiveButton("Configs", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        BluetoothActivity.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 1);
                    }
                }).setCancelable(false).show();
                return;
            default:
                return;
        }
    }
    private void setScanRule() {
        String[] uuids;
        String[] names;
        String str_uuid = "";
        if (TextUtils.isEmpty(str_uuid)) {
            uuids = null;
        } else {
            uuids = str_uuid.split(",");
        }
        UUID[] serviceUuids = null;
        if (uuids != null && uuids.length > 0) {
            serviceUuids = new UUID[uuids.length];
            for (int i = 0; i < uuids.length; i++) {
                if (uuids[i].split("-").length != 5) {
                    serviceUuids[i] = null;
                } else {
                    serviceUuids[i] = UUID.fromString(uuids[i]);
                }
            }
        }
        String str_name = "Spiru Band";
        if (TextUtils.isEmpty(str_name)) {
            names = null;
        } else {
            names = str_name.split(",");
        }
        String mac = "";
        BleManager.getInstance().initScanRule(new BleScanRuleConfig.Builder().
                setServiceUuids(serviceUuids).setDeviceName(true, names).setDeviceMac(mac).
                setAutoConnect(true).setScanTimeOut(10000).build());
    }

    private boolean checkGPSIsOpen() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return false;
        }
        return locationManager.isProviderEnabled("gps");
    }
}