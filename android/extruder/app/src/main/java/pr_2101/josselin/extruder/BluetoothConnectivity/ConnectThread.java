package pr_2101.josselin.extruder.BluetoothConnectivity;

/**
 * Created by josselin on 31/03/18.
 */

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class ConnectThread extends Thread {
    private BluetoothSocket mSocket = null;
    private Handler mHandler;
    private Context context;
    private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String TAG = "Connect thread";

    @Override
    public void run() {
        connect();
    }

    public void setContext(Context _context) {
        context = _context;
    }

    public void setHandler(Handler _handler) {
        mHandler = _handler;
    }

    public void initSocket(BluetoothDevice _device) {
        BluetoothSocket tmp = null;

        try {
            tmp = _device.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            Log.e(TAG, "Could not create RFCOMM socket:" + e.toString());
        }
        mSocket = tmp;
    }

    private void connect() {
        Log.e(TAG, "Attempt to connect");

        try {
            mSocket.connect();
        } catch (IOException e) {
            Log.e(TAG, "Could not connect: " + e.toString());
            try {
                mSocket.close();
            } catch (IOException close) {
                Log.e(TAG, "Could not close connection:" + e.toString());
            }
        }
    }

    public void setColor() {
        manageConnectedSocket();
    }

    private void manageConnectedSocket() {
        DataRetrieveRunnable runnable = new DataRetrieveRunnable(mSocket, context);
        runnable.setOnDataRetrievedListener(new DataRetrieveRunnable.OnDataRetrievedListener() {
            @Override
            public void onDataRetrieved(double temp, double dist, double gas) {
                Log.i(TAG, "temp : " + temp + " dist : " + dist + " gas : " + gas);
                threadMessage(new double[]{temp, dist, gas});
            }
        });
        Thread t = new Thread(runnable);
        t.start();
    }

    void cancel() {
        try {
            mSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close connection:" + e.toString());
        }
    }

    private void threadMessage(double[] d) {
        Message msg = mHandler.obtainMessage();
        Bundle b = new Bundle();
        b.putDoubleArray("data", d);
        msg.setData(b);
        mHandler.sendMessage(msg);
    }
}
