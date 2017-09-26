package com.eyalengel.blurdemo.Model;

import android.graphics.Bitmap;

/**
 * Created by eyal on 9/22/17.
 */

public class MessagesRow {

    private Bitmap userImage;
    private String userFirstName;
    private String userLastName;
    private String msgPreview;
    private String msgTimeAgo;
    private int type; //private chat or group chat
    private int numOfUnreadMsgs;

    public MessagesRow(Bitmap userImage, String userFirstName, String userLastName, String msgPreview, String msgTimeAgo, int numOfUnreadMsgs){
        this.userImage = userImage;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.msgPreview = msgPreview;
        this.msgTimeAgo = msgTimeAgo;
        this.numOfUnreadMsgs = numOfUnreadMsgs;
    }

    public Bitmap getUserImage(){
        return this.userImage;
    }

    public String getUserFirstName(){
        return this.userFirstName;
    }

    public String getUserLastName(){
        return this.userLastName;
    }

    public String getMsgPreview(){
        return this.msgPreview;
    }

    public String getMsgTimeAgo(){
        return this.msgTimeAgo;
    }

    public int getNumOfUnreadMsgs() {return this.numOfUnreadMsgs; }
}
