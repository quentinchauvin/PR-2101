package pr_2101.josselin.extruder;

/**
 * Created by josselin on 31/03/18.
 */

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

/**
 * Created by josselin on 15/02/18.
 */

public class BtScanThread implements Runnable {

    private BluetoothAdapter mBluetoothAdapter;

    public BtScanThread(BluetoothAdapter _bluetoothAdapter) {
        Log.e("BtScanThread", "1");
        mBluetoothAdapter = _bluetoothAdapter;
    }

    @Override
    public void run() {

        Log.e("BtScanThread", "2");

        Log.e("BtScanThread", mBluetoothAdapter.isDiscovering() + "");
        while (mBluetoothAdapter.isDiscovering()) {
            if (!mBluetoothAdapter.isDiscovering()) {
                Log.e("BtScanThread", "finish");
            }
            else {
                Log.e("BtScanThread", "not finish");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void finishDiscovering() {
    }
}
