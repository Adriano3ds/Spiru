package ib2.spiru;

import com.clj.fastble.data.BleDevice;

public interface Observable {
    void addObserver(Observer observer);

    void deleteObserver(Observer observer);

    void notifyObserver(BleDevice bleDevice);
}