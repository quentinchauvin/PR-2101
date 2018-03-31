package pr_2101.josselin.extruder.Receivers;

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

        Intent bluetoothStateIntent = new Intent("BLUETOOTH_STATE_CHANGED");

        Log.e("Receiver", action);

        switch (action) {
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                extraState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                if (extraState == BluetoothAdapter.STATE_OFF) {
                    Log.wtf("BluetoothStateChangeReceiver", "Bluetooth disabled");
                    bluetoothStateIntent.putExtra("bluetoothState", "Bluetooth disabled");
                } else if (extraState == BluetoothAdapter.STATE_ON) {
                    Log.wtf("BluetoothStateChangeReceiver", "Bluetooth enabled");
                    bluetoothStateIntent.putExtra("bluetoothState", "Bluetooth enabled");
                }
                break;

            case BluetoothDevice.ACTION_ACL_CONNECTED:
                Log.wtf("BluetoothStateChangeReceiver", "Device connected");
                bluetoothStateIntent.putExtra("bluetoothState", "Device connected");
                break;
            case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                Log.wtf("BluetoothStateChangeReceiver", "Device disconnected");
                bluetoothStateIntent.putExtra("bluetoothState", "Device disconnected");
                break;
        }
        context.sendBroadcast(bluetoothStateIntent);
    }
}
