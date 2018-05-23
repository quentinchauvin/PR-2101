package pr_2101.josselin.extruder.Adapters;

/**
 * Created by josselin on 23/05/18.
 */

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pr_2101.josselin.extruder.R;
import pr_2101.josselin.extruder.Usr;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private ArrayList<Usr> mUsers;
    private String TAG = "UserAdapter";

    public UserAdapter(ArrayList<Usr> _dataSet) {
        mUsers = _dataSet;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_user, parent, false);

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.mName.setText(mUsers.get(position).getName());
        holder.mScore.setText(mUsers.get(position).getScore()+"");
        holder.mRank.setText(mUsers.get(position).getRank()+"");
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mName, mRank, mScore;

        ViewHolder(View v) {
            super(v);
            mName = v.findViewById(R.id.user_name);
            mRank = v.findViewById(R.id.user_rank);
            mScore = v.findViewById(R.id.user_score);
        }
    }
}
