package pr_2101.josselin.extruder;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import lecho.lib.hellocharts.view.Chart;
import pr_2101.josselin.extruder.Fragments.GasFragment;
import pr_2101.josselin.extruder.Fragments.TempFragment;
import pr_2101.josselin.extruder.Utils.GraphUtils;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.temp, new TempFragment());
        fragmentTransaction.add(R.id.gas, new GasFragment());
        fragmentTransaction.commit();

        //////////////////////////////////////////////////////////////////////
        //Chart
        //////////////////////////////////////////////////////////////////////

        Chart chart = findViewById(R.id.chart);

        int[] meters = {30, 10, 15, 20, 35, 25, 13, 24, 23, 34, 12, 27, 23, 12, 55, 23, 21, 43,30, 10, 15, 20, 35, 25, 13, 24, 23, 34, 12, 27, 23, 12, 55, 23, 21, 43};
        String[] days = new String[] {"01/01", "03/02", "06/02", "09/03", "12/03", "15/04", "18/04",
                "21/04", "01/05", "03/05", "22/05", "03/06","01/07", "03/08", "06/08", "09/08", "12/08", "15/08","01/01", "03/02", "06/02", "09/03", "12/03", "15/04", "18/04",
                "21/04", "01/05", "03/05", "22/05", "03/06","01/07", "03/08", "06/08", "09/08", "12/08", "15/08"};

        GraphUtils.configChart(chart, GraphUtils.CONFIG_GAS, meters, days);
    }
}
