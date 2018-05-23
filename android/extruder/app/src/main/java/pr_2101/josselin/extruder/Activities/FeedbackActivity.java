package pr_2101.josselin.extruder.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import devlight.io.library.ntb.NavigationTabBar;
import lecho.lib.hellocharts.view.Chart;
import pr_2101.josselin.extruder.DatabaseHelper;
import pr_2101.josselin.extruder.FeedBackScheduled;
import pr_2101.josselin.extruder.Fragments.CoilFragment;
import pr_2101.josselin.extruder.Fragments.FeedBackFragment;
import pr_2101.josselin.extruder.Fragments.GasFragment;
import pr_2101.josselin.extruder.Fragments.TempFragment;
import pr_2101.josselin.extruder.R;
import pr_2101.josselin.extruder.Usr;
import pr_2101.josselin.extruder.Utils.GraphUtils;

public class FeedbackActivity extends BluetoothActivity implements FeedBackFragment.OnFragmentInteractionListener {
    private Random rand = new Random();
    private static final String TAG = "FeedBackActivity";
    private GasFragment gasFragment;
    private TempFragment tempFragment;
    private BottomSheetBehavior behavior;
    private DatabaseHelper mDbHelper;

    private float[] meters = {30, 10, 15, 20, 35, 25, 13, 24, 23, 34, 12, 27, 23, 12, 55, 23, 21, 43, 30, 10, 15, 20, 35, 25, 13, 24, 23, 34, 12, 27, 23, 12, 55, 23, 21, 43};
    private String[] days = new String[]{"01/01", "03/02", "06/02", "09/03", "12/03", "15/04", "18/04",
            "21/04", "01/05", "03/05", "22/05", "03/06", "01/07", "03/08", "06/08", "09/08", "12/08", "15/08", "01/01", "03/02", "06/02", "09/03", "12/03", "15/04", "18/04",
            "21/04", "01/05", "03/05", "22/05", "03/06", "01/07", "03/08", "06/08", "09/08", "12/08", "15/08"};

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message inputMessage) {
            double[] d = inputMessage.getData().getDoubleArray("data");
            /*for (double dd : d) {
                Log.e(TAG, dd + "");
            }*/
            assert d != null;
            //tempFragment.setValue(d[0]);
            //gasFragment.setValue(d[2]);

            refreshChartValues();

            Chart line = findViewById(R.id.column);
            GraphUtils.configChart(line, GraphUtils.CONFIG_GAS, meters, days, GraphUtils.hasLabel());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        mDbHelper = new DatabaseHelper(this);

        getConnectThread().setHandler(mHandler);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        tempFragment = new TempFragment();
        gasFragment = new GasFragment();
        CoilFragment coilFragment = new CoilFragment();

        fragmentTransaction.add(R.id.temp, tempFragment);
        fragmentTransaction.add(R.id.gas, gasFragment);
        fragmentTransaction.add(R.id.coil, coilFragment);
        fragmentTransaction.commit();

        //////////////////////////////////////////////////////////////////////
        //BottomSheet
        //////////////////////////////////////////////////////////////////////

        behavior = BottomSheetBehavior.from(findViewById(R.id.test11));
        behavior.setPeekHeight(0);
        behavior.setHideable(true);

        //////////////////////////////////////////////////////////////////////
        //Chart
        //////////////////////////////////////////////////////////////////////

        Chart chart = findViewById(R.id.chart);
        GraphUtils.configChart(chart, GraphUtils.CONFIG_COIL, meters, days, GraphUtils.hasLabel());

        //////////////////////////////////////////////////////////////////////
        //Tab
        //////////////////////////////////////////////////////////////////////

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_feedback),
                        Color.parseColor("#dd6495")
                ).title("Heart")
                        .badgeTitle("7")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_chat),
                        Color.BLUE
                ).title("Cup")
                        .badgeTitle("2")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_star_white),
                        Color.BLUE
                ).title("Diploma")
                        .badgeTitle("3")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_person_white),
                        Color.BLUE
                ).title("Flag")
                        .badgeTitle("1")
                        .build()
        );
        navigationTabBar.setModels(models);

        navigationTabBar.setViewPager(null, 0);
        navigationTabBar.setIsBadged(true);


        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                model.showBadge();

                getConnectThread().setColor();
            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {
            }
        });

    }

    @Override
    public void onFragmentInteraction(short _frag) {
        Chart line = findViewById(R.id.column);
        Chart column = findViewById(R.id.chart);

        switch (_frag) {
            case FeedBackFragment.TEMP:
                GraphUtils.configChart(line, GraphUtils.CONFIG_COIL, meters, days, GraphUtils.hasLabel());
                break;

            case FeedBackFragment.DIST:
                break;

            case FeedBackFragment.GAS:
                GraphUtils.configChart(line, GraphUtils.CONFIG_GAS, meters, days, GraphUtils.hasLabel());
                break;
        }

        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        else behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void refreshChartValues(){

        ArrayList<Float> dist = new ArrayList<>();
        ArrayList<String> hour = new ArrayList<>();

        ArrayList<FeedBackScheduled> data = mDbHelper.getFormattedData();

        for (FeedBackScheduled f : data) {
            dist.add(Float.parseFloat(f.getDist()));
            hour.add(f.getDate());
        }

        float[] dd = new float[dist.size()];
        String[] ss = new String[hour.size()];

        for (int i = 0 ; i < dist.size() ; i++){
            dd[i] = dist.get(i);
            Log.wtf(TAG, dd[i]+"");

            ss[i] = String.valueOf(hour.get(i));
            Log.wtf(TAG, ss[i]);
        }

        days = ss;
        meters =dd;
    }
}
