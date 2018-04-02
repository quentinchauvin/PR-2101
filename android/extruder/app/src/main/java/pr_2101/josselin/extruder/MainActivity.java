package pr_2101.josselin.extruder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import pr_2101.josselin.extruder.Adapters.MyAdapter;
import pr_2101.josselin.extruder.Utils.BluetoothUtils;

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
                    if (mBtDevices.size()==1) showProgress(false);

                    Log.wtf("0", device.getName());
                    Log.wtf("1", device.getAddress());
                    Log.wtf("3", device.getType()+"");

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
                Log.i("RequestEnableBT", "Success");

                mBluetoothAdapter.startDiscovery();
                showProgress(true);

            }
            else {
                Intent intent = new Intent(this, FeedbackActivity.class);
                startActivity(intent);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //mMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_scan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_skip:
                startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        final ProgressBar progressBar = findViewById(R.id.progress);

        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        progressBar.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}