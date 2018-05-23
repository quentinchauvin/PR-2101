package pr_2101.josselin.extruder.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import pr_2101.josselin.extruder.BluetoothConnectivity.ConnectThread;
import pr_2101.josselin.extruder.Usr;

/**
 * Created by josselin on 23/04/18.
 */

public class BluetoothActivity extends AppCompatActivity {

    private static ConnectThread mConnectThread = new ConnectThread();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConnectThread.setContext(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public ConnectThread getConnectThread(){
        return this.mConnectThread;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
