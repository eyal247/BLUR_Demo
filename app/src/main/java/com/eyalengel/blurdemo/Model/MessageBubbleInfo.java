package com.eyalengel.blurdemo.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eyal on 9/24/17.
 */

public class MessageBubbleInfo implements Parcelable{

    private String sender;
    private String messageContent;
    private String timestamp;
//    private long msgTimeInMilliseconds;
    private int messageType;

    public MessageBubbleInfo(String messageContent, String timestamp, int messageType){
        this.messageContent = messageContent;
        this.timestamp = timestamp;
        this.messageType = messageType;
//        this.msgTimeInMilliseconds = msgTimeInMilliseconds;
    }

    public MessageBubbleInfo(Parcel in){

        this.sender = in.readString();
        this.messageContent = in.readString();
        this.timestamp = in.readString();
        this.messageType = in.readInt();
    }


    public static final Creator<MessageBubbleInfo> CREATOR = new Creator<MessageBubbleInfo>() {
        @Override
        public MessageBubbleInfo createFromParcel(Parcel in) {
            return new MessageBubbleInfo(in);
        }

        @Override
        public MessageBubbleInfo[] newArray(int size) {
            return new MessageBubbleInfo[size];
        }
    };

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sender);
        dest.writeString(messageContent);
        dest.writeString(timestamp);
        dest.writeInt(messageType);
    }

//    public long getMsgTimeInMilliseconds() {
//        return msgTimeInMilliseconds;
//    }
//
//    public void setMsgTimeInMilliseconds(long msgTimeInMilliseconds) {
//        this.msgTimeInMilliseconds = msgTimeInMilliseconds;
//    }
}
