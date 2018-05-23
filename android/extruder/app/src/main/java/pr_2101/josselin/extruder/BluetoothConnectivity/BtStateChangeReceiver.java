package pr_2101.josselin.extruder.BluetoothConnectivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by josselin on 31/03/18.
 */

public class BtStateChangeReceiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        int extraState;
        String message = "";

        Intent bluetoothStateIntent = new Intent("BLUETOOTH_STATE_CHANGED");

        Log.e("Receiver", action);

        switch (action) {
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                extraState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                switch (extraState) {
                    case BluetoothAdapter.STATE_TURNING_ON:
                        message = "Bluetooth enabled";
                        break;

                    case BluetoothAdapter.STATE_TURNING_OFF:
                        message = "Bluetooth disabled";
                        break;

                    case BluetoothAdapter.STATE_CONNECTING:
                        message = "Connecting";
                        break;

                    case BluetoothAdapter.STATE_DISCONNECTING:
                        message = "Disconnecting";
                        break;

                    case BluetoothAdapter.STATE_CONNECTED:
                        message = "Connected";
                        break;

                    case BluetoothAdapter.STATE_DISCONNECTED:
                        message = "Disconnected";
                        break;

                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        message = "??1";
                        break;

                    case BluetoothAdapter.SCAN_MODE_NONE:
                        message = "none";
                        break;

                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        message = "??2";
                        break;
                }
                break;

            case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                message = "Discovery finished";
                break;

            case BluetoothDevice.ACTION_ACL_CONNECTED:
                Log.wtf("BluetoothStateChangeReceiver", "Device connected");
                message = "Device connected";
                break;
            case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                Log.wtf("BluetoothStateChangeReceiver", "Device disconnected");
                message = "Device disconnected";
                break;
        }

        if (!message.isEmpty()) {
            bluetoothStateIntent.putExtra("bluetoothState", message);
            context.sendBroadcast(bluetoothStateIntent);
        }
    }
}
