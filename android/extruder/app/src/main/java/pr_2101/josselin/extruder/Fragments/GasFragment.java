package pr_2101.josselin.extruder.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pr_2101.josselin.extruder.R;

/**
 * Created by josselin on 31/03/18.
 */

public class GasFragment extends FeedBackFragment{

    private int mParam1;

    public GasFragment() {
        // Required empty public constructor
    }

    public static GasFragment newInstance(int param1) {
        GasFragment fragment = new GasFragment();
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
        View view = inflater.inflate(R.layout.fragment_gas, container, false);
        ((TextView) view.findViewById(R.id.value)).setText("8.2");
        setValueLayout(((TextView) view.findViewById(R.id.value)));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue(2.1);
            }
        });

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

