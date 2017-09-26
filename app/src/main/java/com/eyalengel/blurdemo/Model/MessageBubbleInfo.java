package com.eyalengel.blurdemo.Model;

/**
 * Created by eyal on 9/24/17.
 */

public class MessageBubbleInfo {

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

//    public long getMsgTimeInMilliseconds() {
//        return msgTimeInMilliseconds;
//    }
//
//    public void setMsgTimeInMilliseconds(long msgTimeInMilliseconds) {
//        this.msgTimeInMilliseconds = msgTimeInMilliseconds;
//    }
}
