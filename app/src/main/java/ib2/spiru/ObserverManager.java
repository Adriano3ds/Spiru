package ib2.spiru;

import com.clj.fastble.data.BleDevice;
import java.util.ArrayList;
import java.util.List;

public class ObserverManager implements Observable {
    private List<Observer> observers = new ArrayList();

    public static ObserverManager getInstance() {
        return ObserverManagerHolder.sObserverManager;
    }

    private static class ObserverManagerHolder {
        /* access modifiers changed from: private */
        public static final ObserverManager sObserverManager = new ObserverManager();

        private ObserverManagerHolder() {
        }
    }

    public void addObserver(Observer obj) {
        this.observers.add(obj);
    }

    public void deleteObserver(Observer obj) {
        if (this.observers.indexOf(obj) >= 0) {
            this.observers.remove(obj);
        }
    }

    public void notifyObserver(BleDevice bleDevice) {
        for (int i = 0; i < this.observers.size(); i++) {
            this.observers.get(i).disConnected(bleDevice);
        }
    }
}