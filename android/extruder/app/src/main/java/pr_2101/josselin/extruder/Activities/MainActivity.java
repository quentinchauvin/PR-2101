package pr_2101.josselin.extruder.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import pr_2101.josselin.extruder.Adapters.BtDeviceAdapter;
import pr_2101.josselin.extruder.BluetoothConnectivity.BtStateChangeReceiver;
import pr_2101.josselin.extruder.OnRetrieveUsersListener;
import pr_2101.josselin.extruder.R;
import pr_2101.josselin.extruder.Usr;
import pr_2101.josselin.extruder.Utils.BluetoothUtils;

import static android.bluetooth.BluetoothAdapter.STATE_CONNECTED;
import static android.bluetooth.BluetoothAdapter.STATE_DISCONNECTED;

public class MainActivity extends BluetoothActivity implements OnRetrieveUsersListener {

    String TAG = "MainActivity";
    BluetoothAdapter mBluetoothAdapter;
    ArrayList<BluetoothDevice> mBtDevices;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    BtDeviceAdapter mAdapter;
    SwipeRefreshLayout mSwipe;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private final BluetoothGattCallback mGattCallback =
            new BluetoothGattCallback() {
                @Override
                public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                                    int newState) {
                    String intentAction;
                    if (newState == BluetoothProfile.STATE_CONNECTED) {
                        Log.i(TAG, "Connected to GATT server.");
                        Log.i(TAG, "Attempting to start service discovery:");

                    } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                        Log.i(TAG, "Disconnected from GATT server.");
                    }
                }
            };

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    if (device.getName() != null && !mBtDevices.contains(device)) {
                        mBtDevices.add(device);
                        if (mBtDevices.size() == 1) showProgress(false);

                        Log.i("Name", device.getName());
                        Log.i("Adress", device.getType()+"");
                        Log.i("Bound state", device.getBondState() + "");

                        /*if (device.getType() == 2){
                            device.connectGatt(getApplicationContext(), true, mGattCallback);
                        }*/

                        mRecyclerView.setAdapter(new BtDeviceAdapter(getConnectThread(), mBtDevices));
                        mRecyclerView.invalidate();
                    }
                    break;

                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    Log.wtf("scan", "finished");
                    if (mSwipe != null && mSwipe.isRefreshing()) {
                        mSwipe.setRefreshing(false);
                    }
                    break;
            }
        }
    };

    private final BtStateChangeReceiver btReceiver = new BtStateChangeReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("bluetoothState") != null) {
                if (!intent.getStringExtra("bluetoothState").isEmpty()) {
                    Snackbar.make(findViewById(R.id.coordinator_layout),
                            intent.getStringExtra("bluetoothState"), Snackbar.LENGTH_LONG).show();
                }


            }
        }
    };

    private final BroadcastReceiver test = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.wtf("test", "C'est commence brooooow");
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

        mAdapter = new BtDeviceAdapter(getConnectThread(), mBtDevices);
        mRecyclerView.setAdapter(mAdapter);

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mScanReceiver, filter);

        IntentFilter f = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(test, f);
        registerReceiver(btReceiver, new IntentFilter("BLUETOOTH_STATE_CHANGED"));

        BluetoothUtils.requestEnableBT(MainActivity.this);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.startDiscovery();


        mSwipe = findViewById(R.id.swipe);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.wtf("it's", "ok");
                if (!mBluetoothAdapter.isDiscovering()) {
                    Log.i("SwipeResfresh", "begin");
                    mBluetoothAdapter.startDiscovery();
                    mBtDevices.clear();
                    mRecyclerView.invalidate();
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                } else {
                    Log.i("SwipeResfresh", "scan isn't finished");
                    mSwipe.setRefreshing(false);
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.wtf("LOGIN", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.wtf("LOGIN", "onAuthStateChanged:signed_out");
                }
            }
        };

        Usr.createUser("BtpqnS38YbTVyqDLEswfALG02m13", "Michel");
        Usr.createUser("DCX2szEo0EOHPPiiphf8if1YYup2", "Charles");
        Usr.createUser("YEGDd2vL9gealXYwvZtFYbOW76S2", "Guy");

        Usr.getAllUsers(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mScanReceiver);
        unregisterReceiver(btReceiver);
        unregisterReceiver(test);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (BluetoothUtils.REQUEST_CODE_ENABLE_BT == requestCode) {
            if (resultCode == RESULT_OK) {
                Log.i("RequestEnableBT", "Success");

                mBluetoothAdapter.startDiscovery();
                showProgress(true);

            } else {
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

    @Override
    public void onRetrieveUsers(ArrayList<Usr> users) {
        for (Usr usr : users){
            Log.wtf(TAG, usr.getName() + ":" + usr.getScore());
        }
    }
}