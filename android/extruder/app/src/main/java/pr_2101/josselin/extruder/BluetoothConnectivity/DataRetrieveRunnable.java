package pr_2101.josselin.extruder.BluetoothConnectivity;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import pr_2101.josselin.extruder.DatabaseHelper;
import pr_2101.josselin.extruder.FeedBackScheduled;

/**
 * Created by josselin on 23/04/18.
 */

public class DataRetrieveRunnable implements Runnable {
    private BluetoothSocket mSocket;
    private OnDataRetrievedListener mOnDataRetrievedListener;
    private Context context;
    private static final String TAG = "MyRunnable";

    DataRetrieveRunnable(final BluetoothSocket _socket, final Context _context) {
        this.mSocket = _socket;
        this.context = _context;
    }

    void setOnDataRetrievedListener(final OnDataRetrievedListener _listener) {
        this.mOnDataRetrievedListener = _listener;
    }

    @Override
    public void run() {
        if (mSocket != null) {
            while (true) {
                DatabaseHelper dbHelp = new DatabaseHelper(context);
                try {
                    final OutputStream outputStream = mSocket.getOutputStream();
                    final InputStream inputStream = mSocket.getInputStream();
                    ArrayList<String> c = new ArrayList<>();

                    try {
                        outputStream.write('a');
                        char a = ((char) inputStream.read());
                        if (a == '/') {
                            while (a != 'g') {
                                a = ((char) inputStream.read());
                                c.add("" + a);
                            }
                            StringBuilder res = new StringBuilder();
                            for (String s : c) {
                                res.append(s);
                            }
                            Log.i(TAG, res.toString());

                            double[] d = retrieveDataFromString(res.toString());

                            if (dbHelp.insertData(new FeedBackScheduled(d[0], d[1], d[2]))) {
                                Log.i(TAG, "yes");
                            } else Log.i(TAG, "no");

                            if (this.mOnDataRetrievedListener != null) {
                                this.mOnDataRetrievedListener.onDataRetrieved(d[0], d[1], d[2]);
                            }
                        }
                    } catch (IOException | NullPointerException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface OnDataRetrievedListener {

        void onDataRetrieved(final double temp, final double dist, final double gas);

    }

    private double[] retrieveDataFromString(String _soup) {

        String[] frag1 = _soup.split("t");
        double tmp = Double.parseDouble(frag1[0]);

        String[] frag2 = frag1[1].split("d");
        double dist = Double.parseDouble(frag2[0]);
        double gas = Double.parseDouble(frag2[1].split("g")[0]);

        return new double[]{tmp, dist, gas};
    }
}
