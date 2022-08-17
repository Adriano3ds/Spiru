package ib2.spiru;

import com.clj.fastble.data.BleDevice;

public interface Observer {
    void disConnected(BleDevice bleDevice);
}