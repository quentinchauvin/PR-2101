package pr_2101.plastic_extruder.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

import pr_2101.plastic_extruder.myapplication.Adapter.MyAdapter;
import pr_2101.plastic_extruder.myapplication.Utils.BluetoothUtils;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter;
    ArrayList<BluetoothDevice> mBtDevices;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MyAdapter mAdapter;

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (device.getName() != null && !mBtDevices.contains(device)) {
                    mBtDevices.add(device);

                    Log.wtf("0", device.getName());
                    Log.wtf("1", device.getAddress());

                    mRecyclerView.setAdapter(new MyAdapter(mBtDevices));
                    mRecyclerView.invalidate();
                }
            }
        }
    };

    private final BroadcastReceiver btReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("bluetoothState")) {
                Snackbar.make(findViewById(R.id.coordinator_layout),
                        intent.getStringExtra("bluetoothState"), Snackbar.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtDevices = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(mBtDevices);
        mRecyclerView.setAdapter(mAdapter);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

        registerReceiver(btReceiver, new IntentFilter("BLUETOOTH_STATE_CHANGED"));

        BluetoothUtils.requestEnableBT(MainActivity.this);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.startDiscovery();

        findViewById(R.id.my_text_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Click", "Start discovery");
                    mBluetoothAdapter.startDiscovery();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(mReceiver);
        unregisterReceiver(btReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (BluetoothUtils.REQUEST_CODE_ENABLE_BT == requestCode) {
            if (resultCode == RESULT_OK) {
                Log.e("RequestEnableBT", "Success");

                mBluetoothAdapter.startDiscovery();
                //BtScanThread btScanThread = new BtScanThread(mBluetoothAdapter);
                //btScanThread.run();

            } else {
                Intent enableBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBlueTooth, BluetoothUtils.REQUEST_CODE_ENABLE_BT);
            }
        }
    }
}
