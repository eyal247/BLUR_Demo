package com.eyalengel.blurdemo.Model;

import android.graphics.Bitmap;

/**
 * Created by eyal on 9/19/17.
 */

public class FeedRow {
    private Bitmap image;
    private String userFirstName;
    private String quote;
    private String timeAgo;

    public FeedRow(Bitmap image, String userFirstName, String quote, String timeAgo){
        this.image = image;
        this.userFirstName = userFirstName;
        this.quote = quote;
        this.timeAgo = timeAgo;
    }

    public String getUserFirstName() {
        return this.userFirstName;
    }

    public String getQuote() {
        return this.quote;
    }

    public String getTimeAgo() {
        return this.timeAgo;
    }

    public Bitmap getImage() {
        return image;
    }
}
