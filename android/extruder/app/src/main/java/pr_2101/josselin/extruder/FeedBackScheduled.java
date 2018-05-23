package pr_2101.josselin.extruder;

import android.database.Cursor;

import java.util.Calendar;

/**
 * Created by josselin on 26/04/18.
 */

public class FeedBackScheduled {
    private final String mDate;

    private final String mTemp;
    private final String mDist;
    private final String mGas;

    public FeedBackScheduled(double _temp, double _dist, double _gas) {
        mDate = getCurrentDate();
        mDist = _dist + "";
        mTemp = _temp + "";
        mGas = _gas + "";
    }

    public FeedBackScheduled(Cursor _cursor) {
        mDate = _cursor.getString(1);
        mTemp = _cursor.getString(2);
        mDist = _cursor.getString(3);
        mGas = _cursor.getString(4);
    }

    public String getDate() {
        return mDate;
    }

    public String getDist() {
        return mDist;
    }

    public String getTemp() {
        return mTemp;
    }

    public String getGas() {
        return mGas;
    }

    private static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        int h = (c.get(Calendar.AM_PM) == Calendar.AM) ? c.get(Calendar.HOUR) : c.get(Calendar.HOUR) + 12;
        int d = c.get(Calendar.DAY_OF_MONTH);
        int mo = c.get(Calendar.MONTH) + 1;
        int m = c.get(Calendar.MINUTE);
        int y = c.get(Calendar.YEAR);

        //return (y + "-" + mo + "-" + d + "-" + h + "-" + m);
        String hs, ms;

        if (h < 10) hs = "0" + h;
        else hs = "" + h;

        if (m < 10) ms = "0" + m;
        else ms = "" + m;

        return (hs + ":" + ms);
    }


    @Override
    public String toString() {
        return "FeedBackScheduled{" +
                "date='" + mDate + '\'' +
                ", temp='" + mTemp + '\'' +
                ", dist='" + mDist + '\'' +
                ", gas='" + mGas + '\'' +
                '}';
    }
}
