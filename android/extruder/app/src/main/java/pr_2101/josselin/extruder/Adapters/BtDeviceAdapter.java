package pr_2101.josselin.extruder.Adapters;

/**
 * Created by josselin on 31/03/18.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pr_2101.josselin.extruder.BluetoothConnectivity.ConnectThread;
import pr_2101.josselin.extruder.R;


public class BtDeviceAdapter extends RecyclerView.Adapter<BtDeviceAdapter.ViewHolder> {
    private ArrayList<BluetoothDevice> mBtDevices;
    private ConnectThread connectThread;
    private String TAG = "BtDeviceAdapter";

    public BtDeviceAdapter(ConnectThread _connectThread, ArrayList<BluetoothDevice> _dataSet) {
        connectThread= _connectThread;
        mBtDevices = _dataSet;
    }

    @Override
    public BtDeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_bt_device, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.mBtName.setText(mBtDevices.get(position).getName());

        holder.mBtMac.setText(mBtDevices.get(position).getAddress());

        connectThread.initSocket(mBtDevices.get(position));

        if (mBtDevices.get(position).getBondState() == 12){
            holder.mBound.setText(R.string.bounded);
        }
        if (mBtDevices.get(position).getType() == 2){
            holder.mBound.setText("BLE");
        }

        holder.mBtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "position : " + position);
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                /*ConnectThread connectThread = new ConnectThread(mBtDevices.get(position));
                connectThread.run();*/
                try {
                    mBtDevices.get(position).createBond();
                    connectThread.run();
                }
                catch (Exception e){
                    Log.e("Unable to create bound", e.getMessage());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mBtDevices.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mBtName, mBtMac, mBound;

        ViewHolder(View v) {
            super(v);
            mBtName = v.findViewById(R.id.device_name);
            mBtMac = v.findViewById(R.id.device_mac);
            mBound = v.findViewById(R.id.is_bounded);
        }
    }
}
