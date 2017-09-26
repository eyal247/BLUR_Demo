package com.eyalengel.blurdemo.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eyalengel.blurdemo.Model.MessageBubbleInfo;
import com.eyalengel.blurdemo.Model.ScoreboardRow;
import com.eyalengel.blurdemo.R;
import com.eyalengel.blurdemo.Utils.FontHelper;

import java.util.ArrayList;
import java.util.Date;

import static com.eyalengel.blurdemo.Model.AppConstants.*;

/**
 * Created by eyal on 9/24/17.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.MyViewHolder> {

    private ArrayList<MessageBubbleInfo> conversationMessagesList = new ArrayList<>();
    private int messageType;
    private Context context;

    public ConversationAdapter(ArrayList<MessageBubbleInfo> conversationMessagesList, Context context) {
        this.conversationMessagesList = conversationMessagesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch(viewType) {
            case MSG_TYPE_SELF:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_item_self, parent, false);
                break;
            case MSG_TYPE_OTHER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_item_other, parent, false);
                break;
            case MSG_TYPE_LOCKED:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_item_self_locked, parent, false);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_item_self, parent, false);
                break;
        }

        return new ConversationAdapter.MyViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Typeface typeface = FontHelper.getTypeface(context, FontHelper.FONTAWESOME);
        String lockIconStr = context.getResources().getString(R.string.fa_lock);
        MessageBubbleInfo msg = conversationMessagesList.get(position);

        if(msg.getTimestamp().equals(EMPTY_STRING))
            holder.timeStampTV.setVisibility(View.GONE);
        else
            holder.timeStampTV.setText(msg.getTimestamp());

        if(holder.getItemViewType() == MSG_TYPE_LOCKED) {
            holder.messageContentTV.setText(msg.getMessageContent() + TRIPLE_SPACE + lockIconStr);
            holder.messageContentTV.setTypeface(typeface);
        } else
            holder.messageContentTV.setText(msg.getMessageContent());
    }

    @Override
    public int getItemViewType(int position) {
        MessageBubbleInfo message = conversationMessagesList.get(position);
        if (message.getMessageType() == MSG_TYPE_SELF) {
            return MSG_TYPE_SELF;
        } else if (message.getMessageType() == MSG_TYPE_OTHER)
            return MSG_TYPE_OTHER;
        else if (message.getMessageType() == MSG_TYPE_LOCKED)
            return MSG_TYPE_LOCKED;
        else
            return MSG_TYPE_UNLOCKED;
    }

    @Override
    public int getItemCount() {
        return conversationMessagesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView timeStampTV, messageContentTV, hiddenMessageTV;
        public Date lastMessageTimeStamp;


        public MyViewHolder(View itemView, int msgType){
            super(itemView);

            switch(msgType){
                case MSG_TYPE_SELF:
                    timeStampTV = (TextView)itemView.findViewById(R.id.timestamp_self);
                    messageContentTV = (TextView)itemView.findViewById(R.id.message_self);
                    break;
                case MSG_TYPE_OTHER:
                    timeStampTV = (TextView)itemView.findViewById(R.id.timestamp_other);
                    messageContentTV = (TextView)itemView.findViewById(R.id.message_other);
                    break;
                case MSG_TYPE_LOCKED:
                    timeStampTV = (TextView)itemView.findViewById(R.id.timestamp_self_locked);
                    messageContentTV = (TextView)itemView.findViewById(R.id.message_self_locked);
                    break;
                case MSG_TYPE_UNLOCKED:
                    //do something
                    break;
            }
        }
    }
}
