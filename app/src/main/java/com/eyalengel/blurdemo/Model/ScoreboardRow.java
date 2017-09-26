package com.eyalengel.blurdemo.Model;

import android.graphics.Bitmap;

/**
 * Created by eyal on 9/21/17.
 */

public class ScoreboardRow {

    private Bitmap userImage;
    private int rankNumber;
    private String userFirstName;
    private String userLastName;
    private int numOfPoints;
    private boolean isUserRow;

    public ScoreboardRow(Bitmap userImage, int rankNumber, String userFirstName, String userLastName, int numOfPoints, boolean isUserRow){
        this.userImage = userImage;
        this.rankNumber = rankNumber;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.numOfPoints = numOfPoints;
        this.isUserRow = isUserRow;
    }

    public Bitmap getUserImage(){
        return this.userImage;
    }

    public int getUserRankNumber(){
        return this.rankNumber;
    }

    public String getUserFirstName(){
        return this.userFirstName;
    }

    public String getUserLastName(){
        return this.userLastName;
    }

    public int getNumOfPoints(){
        return this.numOfPoints;
    }

    public boolean getIsUserRow() {return this.isUserRow; }
}
