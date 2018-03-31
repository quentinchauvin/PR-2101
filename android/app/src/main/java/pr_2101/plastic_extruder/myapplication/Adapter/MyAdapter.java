package pr_2101.plastic_extruder.myapplication.Adapter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pr_2101.plastic_extruder.myapplication.ConnectThread;
import pr_2101.plastic_extruder.myapplication.R;

/**
 * Created by josselin on 14/02/18.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<BluetoothDevice> mBtDevices;
    private String TAG = "Adapter";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mBtName, mBtMac;

        ViewHolder(View v) {
            super(v);
            mBtName = v.findViewById(R.id.device_name);
            mBtMac = v.findViewById(R.id.device_mac);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<BluetoothDevice> _dataSet) {
        mBtDevices = _dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_bt_device, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.mBtName.setText(mBtDevices.get(position).getName());

        holder.mBtMac.setText(mBtDevices.get(position).getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "position : " + position);
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                ConnectThread connectThread = new ConnectThread(mBtDevices.get(position));
                connectThread.run();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mBtDevices.size();
    }
}


