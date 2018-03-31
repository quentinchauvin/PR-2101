package pr_2101.josselin.extruder.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.TextView;

/**
 * Created by josselin on 31/03/18.
 */

public class FeedBackFragment extends Fragment {
    private TextView valueLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public TextView getValueLayout(){ return valueLayout; }
    public void setValueLayout(final TextView view){ valueLayout = view; }

    public void setValue(final double value){
        getValueLayout().setText(value+"");
    }

}
