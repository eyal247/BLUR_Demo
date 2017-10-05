package com.eyalengel.blurdemo.Utils;

import android.content.Context;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.eyalengel.blurdemo.Model.AppConstants.EMPTY_STRING;
import static com.eyalengel.blurdemo.Model.AppConstants.MY_DATE_FORMAT;
import static com.eyalengel.blurdemo.Model.AppConstants.MY_TIME_FORMAT;

/**
 * Created by eyal on 9/25/17.
 */

public class DateTimeUtils {

    public static String getFormattedDate(Context context, long currMsgTimeInMillis, long lastMsgTimeInMillis) {
        String formatedDate = null;

        Calendar currMsgCal = Calendar.getInstance();
        Calendar lastMsgCal = Calendar.getInstance();

        currMsgCal.setTimeInMillis(currMsgTimeInMillis);
        lastMsgCal.setTimeInMillis(lastMsgTimeInMillis);

//        Date currMsg = currMsgTime.getTime();
//        Date lastMsg = lastMsgTime.getTime();

        Calendar now = Calendar.getInstance();

        // same date
        if (currMsgCal.get(Calendar.DATE) == lastMsgCal.get(Calendar.DATE)) {
            if (currMsgCal.get(Calendar.HOUR_OF_DAY) - lastMsgCal.get(Calendar.HOUR_OF_DAY) == 1) { //one more hour
                if(currMsgCal.get(Calendar.MINUTE) - lastMsgCal.get(Calendar.MINUTE) > 0)  // really more than an hour later
                    formatedDate = "today @ " + DateFormat.format(MY_TIME_FORMAT, currMsgCal);
                else //less than an hour later (still within the same absolute hour)
                    formatedDate = EMPTY_STRING;
            }
            // same date, more than one hour later
            else if (currMsgCal.get(Calendar.HOUR_OF_DAY) - lastMsgCal.get(Calendar.HOUR_OF_DAY) > 1){
                formatedDate = "today @ " + DateFormat.format(MY_TIME_FORMAT, currMsgCal);
            }
            else // same hour
                formatedDate = EMPTY_STRING;
        }

        else if (now.get(Calendar.DATE) - currMsgCal.get(Calendar.DATE) == 1) {
            formatedDate = "Yesterday @ " + DateFormat.format(MY_TIME_FORMAT, currMsgCal);
        }

        else if (now.get(Calendar.YEAR) == currMsgCal.get(Calendar.YEAR)) {
            formatedDate = "today @ " + DateFormat.format(MY_TIME_FORMAT, currMsgCal).toString();
        }

        else {
            formatedDate = DateFormat.format(MY_DATE_FORMAT, currMsgCal).toString();
        }

        return formatedDate;
    }

    public static String getTimeStampString() {

        return (String) DateFormat.format(MY_TIME_FORMAT, Calendar.getInstance().getTime());
    }
}
