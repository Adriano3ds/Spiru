package ib2.spiru;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
    UUID uuid_service;
    UUID uuid_chara;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        BleManager.getInstance().init(getApplication());
        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setOperateTimeout(5000);
        BleManager.getInstance().isSupportBle();
        BleManager.getInstance().isBlueEnable();
        BleManager.getInstance().enableBluetooth();
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, 0x01);
        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                .setAutoConnect(true)      // 连接时的autoConnect参数，可选，默认false
                .setScanTimeOut(10000)              // 扫描超时时间，可选，默认10秒
                .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);
//        BleManager.getInstance().scanAndConnect(new BleScanAndConnectCallback() {
//            @Override
//            public void onScanStarted(boolean success) {
//
//            }
//
//            @Override
//            public void onScanFinished(BleDevice scanResult) {
//
//            }
//
//            @Override
//            public void onStartConnect() {
//
//            }
//
//            @Override
//            public void onConnectFail(BleDevice bleDevice,BleException exception) {
//
//            }
//
//            @Override
//            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
//                List<BluetoothGattService> serviceList = gatt.getServices();
//                for (BluetoothGattService service : serviceList) {
//                    uuid_service = service.getUuid();
//
//                    List<BluetoothGattCharacteristic> characteristicList= service.getCharacteristics();
//                    for(BluetoothGattCharacteristic characteristic : characteristicList) {
//                        uuid_chara = characteristic.getUuid();
//                    }
//                }
//                BleManager.getInstance().read(
//                        bleDevice,
//                        uuid_service,
//                        uuid_characteristic_read,
//                        new BleReadCallback() {
//                            @Override
//                            public void onReadSuccess(byte[] data) {
//                                // 读特征值数据成功
//                            }
//
//                            @Override
//                            public void onReadFailure(BleException exception) {
//                                // 读特征值数据失败
//                            }
//
//            }
//
//            @Override
//            public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
//
//            }
//        });
//        BleManager.getInstance().notify(
//                bleDevice,
//                uuid_service,
//                uuid_characteristic_notify,
//                new BleNotifyCallback() {
//                    @Override
//                    public void onNotifySuccess() {
//                        // 打开通知操作成功
//                    }
//
//                    @Override
//                    public void onNotifyFailure(BleException exception) {
//                        // 打开通知操作失败
//                    }
//
//                    @Override
//                    public void onCharacteristicChanged(byte[] data) {
//                        // 打开通知后，设备发过来的数据将在这里出现
//                    }
//                });
//        BleManager.getInstance().stopNotify(uuid_service, uuid_characteristic_notify);
//        BleManager.getInstance().indicate(
//                bleDevice,
//                uuid_service,
//                uuid_characteristic_indicate,
//                new BleIndicateCallback() {
//                    @Override
//                    public void onIndicateSuccess() {
//                        // 打开通知操作成功
//                    }
//
//                    @Override
//                    public void onIndicateFailure(BleException exception) {
//                        // 打开通知操作失败
//                    }
//
//                    @Override
//                    public void onCharacteristicChanged(byte[] data) {
//                        // 打开通知后，设备发过来的数据将在这里出现
//                    }
//                });
//        BleManager.getInstance().stopIndicate(uuid_service, uuid_characteristic_indicate);
//        BleManager.getInstance().read(
//                bleDevice,
//                uuid_service,
//                uuid_characteristic_read,
//                new BleReadCallback() {
//                    @Override
//                    public void onReadSuccess(byte[] data) {
//                        // 读特征值数据成功
//                    }
//
//                    @Override
//                    public void onReadFailure(BleException exception) {
//                        // 读特征值数据失败
//                    }
//                });
//        BleManager.getInstance().setMtu(bleDevice, mtu, new BleMtuChangedCallback() {
//            @Override
//            public void onSetMTUFailure(BleException exception) {
//                // 设置MTU失败
//            }
//
//            @Override
//            public void onMtuChanged(int mtu) {
//                // 设置MTU成功，并获得当前设备传输支持的MTU值
//            }
//        });
//        BleManager.getInstance().readRssi(
//                bleDevice,
//                new BleRssiCallback() {
//
//                    @Override
//                    public void onRssiFailure(BleException exception) {
//                        // 读取设备的信号强度失败
//                    }
//
//                    @Override
//                    public void onRssiSuccess(int rssi) {
//                        // 读取设备的信号强度成功
//                    }
//                });
    }
}