package com.eyalengel.blurdemo.Adapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eyalengel.blurdemo.Fragments.ScoreboardFragment;
import com.eyalengel.blurdemo.Model.AppConstants;
import com.eyalengel.blurdemo.Model.ScoreboardRow;
import com.eyalengel.blurdemo.R;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;

import static com.eyalengel.blurdemo.Model.AppConstants.*;

/**
 * Created by eyal on 9/21/17.
 */

public class ScoreboardListAdapter extends RecyclerView.Adapter<ScoreboardListAdapter.MyViewHolder> {

    private ArrayList<ScoreboardRow> scoreboard = new ArrayList<>();

    public ScoreboardListAdapter(ArrayList<ScoreboardRow> scoreboard) {
        this.scoreboard = scoreboard;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scoreboard_row, parent, false);
        return new ScoreboardListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ScoreboardRow sbr = scoreboard.get(position);

        holder.imageView.setImageBitmap(sbr.getUserImage());
        holder.rankNumber.setText(sbr.getUserRankNumber()+EMPTY_STRING);
        holder.username.setText(sbr.getUserFirstName() + SPACE_CHAR + sbr.getUserLastName());
        holder.numOfPoints.setText(sbr.getNumOfPoints() + " pts");
        if(sbr.getIsUserRow()) {
            holder.numOfPoints.setText(sbr.getNumOfPoints() + " pts");
            holder.numOfPoints.setTextColor(Color.parseColor(SCOREBOARD_COLOR));
            holder.numOfPoints.setTypeface(null, Typeface.BOLD);
        } else {
            // styling number of points in Bold and the word "pts" in regular font
            String sourceString = "<b>" + sbr.getNumOfPoints() + "</b> pts";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.numOfPoints.setText(Html.fromHtml(sourceString, 0));
            } else {
                holder.numOfPoints.setText(Html.fromHtml(sourceString));
            }
        }
   }

//    to get the first itemView in list (then check if itemView = 1 in onCreateViewHolder();
//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0)
//            return 1;
//        else
//            return 2;
//    }

    @Override
    public int getItemCount() {
        return scoreboard.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView rankNumber, username, numOfPoints;
        public CircularImageView imageView;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            rankNumber = (TextView)itemView.findViewById(R.id.scoreboard_user_rank);
            username = (TextView)itemView.findViewById(R.id.scoreboard_username);
            numOfPoints = (TextView)itemView.findViewById(R.id.user_score);
            imageView = (CircularImageView)itemView.findViewById(R.id.scoreboard_imageview);

        }

    }
}
