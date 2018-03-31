package pr_2101.josselin.extruder.Utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

/**
 * Created by josselin on 31/03/18.
 */

public class BluetoothUtils {

    public final static int REQUEST_CODE_ENABLE_BT = 1;

    public static void requestEnableBT(Activity _activity) {
        Intent enableBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        _activity.startActivityForResult(enableBlueTooth, REQUEST_CODE_ENABLE_BT);
    }
}