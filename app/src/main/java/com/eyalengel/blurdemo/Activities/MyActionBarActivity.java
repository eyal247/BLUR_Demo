package com.eyalengel.blurdemo.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.eyalengel.blurdemo.R;
import static com.eyalengel.blurdemo.Model.AppConstants.*;

public class MyActionBarActivity extends AppCompatActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        setCustomActionBar();
    }

    public void setCustomActionBar() {
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.custom_action_bar);
    }

    public void setActionBarTitle(String title, Activity activity) {
        TextView tv = (TextView) activity.findViewById(R.id.custom_action_bar_title);
        if (tv != null) {
            tv.setText(title);
        }
        actionBar.setTitle(title);
    }

    public void restoreOriginalActionBar() {
        actionBar.setDisplayShowCustomEnabled(false);
    }

    public void showActionbarBackButton(boolean show) {
        actionBar.setDisplayHomeAsUpEnabled(show);
    }

    public void setActionBarBackgroundColor(int tabIndex) {
        switch (tabIndex) {
            case FEED_TAB_INDEX:
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(FEED_COLOR)));
                break;
            case SCOREBOARD_TAB_INDEX:
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(SCOREBOARD_COLOR)));
                break;
            case MESSAGES_TAB_INDEX:
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(MESSAGES_COLOR)));
                break;
            case GAMES_TAB_INDEX:
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(GAMES_COLOR)));
                break;
            case GROUPS_TAB_INDEX:
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(GROUPS_COLOR)));
                break;

        }
    }

    public void setActionbarCustomView(int viewID) {
        actionBar.setCustomView(viewID);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void changeStatuBarColor(int ColorId) {
        Window window = this.getWindow();

//      clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//      add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

//      change the status bar color
        window.setStatusBarColor(ContextCompat.getColor(this, ColorId));
    }


    public void hideActionBar() {
        actionBar.hide();
    }

    public void showActionBar() {
        actionBar.show();
    }
}
