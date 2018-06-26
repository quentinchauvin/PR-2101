package pr_2101.josselin.extruder.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pr_2101.josselin.extruder.R;

/**
 * Created by josselin on 31/03/18.
 */

public class FeedBackFragment extends Fragment {
    private TextView valueLayout;
    private TextView valueLayout2;
    private OnFragmentInteractionListener mListener;

    public final static short TEMP = 1;
    public final static short DIST = 2;
    public final static short GAS = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FeedBackFragment.OnFragmentInteractionListener) {
            mListener = (FeedBackFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public TextView getValueLayout(){ return valueLayout; }
    public void setValueLayout(final TextView view){ valueLayout = view; }
    public void setValueLayout(final TextView view1, final TextView view2) {
        valueLayout = view1;
        valueLayout2 =view2;
    }
    public void setValue(final double value){
        getValueLayout().setText(value+"");
    }
    public void setValue(final double value1, final double value2){
        valueLayout.setText(value1+"");
        valueLayout2.setText(value2+"");
    }

    public OnFragmentInteractionListener getFragmentInteractionListener(){
        return mListener;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(short _frag);
    }

}
