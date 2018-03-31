package pr_2101.josselin.extruder.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pr_2101.josselin.extruder.R;

/**
 * Created by josselin on 31/03/18.
 */

public class TempFragment extends FeedBackFragment{

    private int mParam1;

    public TempFragment() {
        // Required empty public constructor
    }

    public static TempFragment newInstance(int param1) {
        TempFragment fragment = new TempFragment();
        Bundle args = new Bundle();
        args.putInt("jean", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt("jean");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_temp, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.wtf("onSaveInstance", "activated");
    }
}

