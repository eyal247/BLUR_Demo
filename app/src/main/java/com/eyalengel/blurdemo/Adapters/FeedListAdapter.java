package com.eyalengel.blurdemo.Adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eyalengel.blurdemo.Model.FeedRow;
import com.eyalengel.blurdemo.R;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;

import static com.eyalengel.blurdemo.Model.AppConstants.SPACE_CHAR;

/**
 * Created by eyal on 9/19/17.
 */

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.MyViewHolder> {

    private ArrayList<FeedRow> feedRows = new ArrayList<>();

    public FeedListAdapter(ArrayList<FeedRow> feedRows){
        this.feedRows = feedRows;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list_row, parent, false);

        return new FeedListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FeedRow feedRow = feedRows.get(position);
        holder.contentTV.setText(feedRow.getUserFirstName() + SPACE_CHAR + feedRow.getQuote());
        holder.timeAgo.setText(feedRow.getTimeAgo());
        holder.image.setImageBitmap(feedRow.getImage());
    }

    @Override
    public int getItemCount() {
        return feedRows.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contentTV, timeAgo;
        public CircularImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            contentTV = (TextView)itemView.findViewById(R.id.list_row_name_and_quote_tv);
            timeAgo = (TextView)itemView.findViewById(R.id.feed_list_row_time_ago_tv);
            image = (CircularImageView)itemView.findViewById(R.id.list_row_image);
        }
    }
}
