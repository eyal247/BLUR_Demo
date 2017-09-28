package com.eyalengel.blurdemo.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.eyalengel.blurdemo.Model.MessagesRow;
import com.eyalengel.blurdemo.R;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;

import static com.eyalengel.blurdemo.Model.AppConstants.NO_UNREAD_MESSAGES;
import static com.eyalengel.blurdemo.Model.AppConstants.SPACE_CHAR;

/**
 * Created by eyal on 9/22/17.
 */

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.MyViewHolder> {

    private ArrayList<MessagesRow> messagesListRows = new ArrayList<>();
    private ArrayList<MessagesRow> tempListHolder = new ArrayList<>();
    private int lastPosition = -1;
    private Context context;

    public MessagesListAdapter(ArrayList<MessagesRow> messagesListRows, Context context) {
        this.messagesListRows = messagesListRows;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_list_row, parent, false);

        return new MessagesListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MessagesRow row = messagesListRows.get(position);
        holder.userImageView.setImageBitmap(row.getUserImage());
        holder.userNameTV.setText(row.getUserFirstName()+ SPACE_CHAR + row.getUserLastName());
        holder.msgPreviewTV.setText(row.getMsgPreview());
        holder.timeAgoTV.setText(row.getMsgTimeAgo());
        if(row.getNumOfUnreadMsgs() <= NO_UNREAD_MESSAGES)
            holder.numOfUnreadMsgsTV.setVisibility(View.GONE);
        else {
            holder.numOfUnreadMsgsTV.setText(row.getNumOfUnreadMsgs() + "");
            holder.numOfUnreadMsgsTV.setVisibility(View.VISIBLE);
        }

        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return messagesListRows.size();
    }

    public void filterSearchedMessages(ArrayList<MessagesRow> filteredMessages)
    {
        tempListHolder.addAll(messagesListRows);
        messagesListRows.clear();
        messagesListRows.addAll(filteredMessages);

        notifyDataSetChanged();

    }

    private void returnDataToOriginalList() {
        messagesListRows.clear();
        messagesListRows.addAll(tempListHolder);
        tempListHolder.clear();
    }

    public void handleEmptySearchBar(){
        returnDataToOriginalList();
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircularImageView userImageView;
        public TextView userNameTV, msgPreviewTV, timeAgoTV, numOfUnreadMsgsTV;

        public MyViewHolder(View itemView) {
            super(itemView);
            userImageView = (CircularImageView) itemView.findViewById(R.id.message_imageview);
            userNameTV = (TextView) itemView.findViewById(R.id.messages_username_tv);
            msgPreviewTV = (TextView) itemView.findViewById(R.id.messages_preview_message);
            timeAgoTV = (TextView) itemView.findViewById(R.id.message_time_ago);
            numOfUnreadMsgsTV = (TextView) itemView.findViewById(R.id.unread_single_message);
        }
    }

    /* ------------------------- */
    /* CLick and Touch Listeners */
    /* ------------------------- */

    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MessagesListAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView,
                                     final MessagesListAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }



}
