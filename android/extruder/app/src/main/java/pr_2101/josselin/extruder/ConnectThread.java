package pr_2101.josselin.extruder;

/**
 * Created by josselin on 31/03/18.
 */

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class ConnectThread extends Thread {
    private BluetoothSocket mSocket = null;
    private char[] mColor;
    private final static UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String TAG = "Connect thread";

    public ConnectThread(BluetoothDevice _device) {
        Log.wtf("ConnectThread", "Start");

        BluetoothSocket tmp = null;

        try {
            tmp = _device.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            Log.e(TAG, "Could not create RFCOMM socket:" + e.toString());
        }
        mSocket = tmp;
    }

    @Override
    public void run() {
        connect();
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

    public void setColor(char[] _color) {
        this.mColor = _color;
        Log.wtf("setColor", "Color : " + mColor[0] + ", " + mColor[1] + ", " + mColor[2]
                + ", " + mColor[3] + ", " + mColor[4] + ", " + mColor[5]);
        manageConnectedSocket(mSocket);
    }

    private void manageConnectedSocket(BluetoothSocket bluetoothSocket) {
        try {
            OutputStream outputStream = bluetoothSocket.getOutputStream();

            if (mColor != null) {
                outputStream.write('/');
                Thread.sleep(20);
                for (int color : mColor) {
                    outputStream.write(color);
                    Thread.sleep(20);
                }
                outputStream.write(';');
                Log.i("BT manager'", "Sent");

                Thread.sleep(20);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    void cancel() {
        try {
            mSocket.close();
        } catch (IOException ignored) {
        }
    }

    public boolean isConnected() {
        if (mSocket != null) {
            return mSocket.isConnected();
        } else return false;
    }
}
