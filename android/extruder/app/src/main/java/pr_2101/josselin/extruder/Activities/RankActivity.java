package pr_2101.josselin.extruder.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import pr_2101.josselin.extruder.Adapters.BtDeviceAdapter;
import pr_2101.josselin.extruder.Adapters.UserAdapter;
import pr_2101.josselin.extruder.OnRetrieveUsersListener;
import pr_2101.josselin.extruder.R;
import pr_2101.josselin.extruder.Usr;

public class RankActivity extends AppCompatActivity implements OnRetrieveUsersListener{
    private RecyclerView mRecyclerView;
    private UserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        mRecyclerView = (RecyclerView) findViewById(R.id.users);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Usr.getAllUsers(this);

    }

    @Override
    public void onRetrieveUsers(ArrayList<Usr> users) {

        mAdapter = new UserAdapter( users);
        mRecyclerView.setAdapter(mAdapter);
    }
}
