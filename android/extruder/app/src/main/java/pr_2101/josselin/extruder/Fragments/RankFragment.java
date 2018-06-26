package pr_2101.josselin.extruder.Fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pr_2101.josselin.extruder.Adapters.UserAdapter;
import pr_2101.josselin.extruder.R;
import pr_2101.josselin.extruder.Usr;

/**
 * Created by josselin on 11/06/18.
 */

public class RankFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private UserAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_rank, container, false);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.users);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Usr.getAllUsers(getContext());

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void displayUsers(ArrayList<Usr> _users){
        mAdapter = new UserAdapter(_users);
        mRecyclerView.setAdapter(mAdapter);
    }

}
