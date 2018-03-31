package pr_2101.josselin.extruder;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pr_2101.josselin.extruder.Fragments.GasFragment;
import pr_2101.josselin.extruder.Fragments.TempFragment;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);



        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.test, new TempFragment());
        fragmentTransaction.commit();

        FragmentManager fragmentManager1 = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();

        fragmentTransaction1.add(R.id.test1, new GasFragment());
        fragmentTransaction1.commit();
    }
}
