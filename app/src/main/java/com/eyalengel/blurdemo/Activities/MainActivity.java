package com.eyalengel.blurdemo.Activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import com.eyalengel.blurdemo.Fragments.ConversationFragment;
import com.eyalengel.blurdemo.Fragments.MessagesFragment;
import com.eyalengel.blurdemo.Fragments.FeedFragment;
import com.eyalengel.blurdemo.Fragments.GamesFragment;
import com.eyalengel.blurdemo.Fragments.GroupsFragment;
import com.eyalengel.blurdemo.Fragments.ScoreboardFragment;
import com.eyalengel.blurdemo.Listeners.OnFragmentInteractionListener;
import com.eyalengel.blurdemo.R;
import com.eyalengel.blurdemo.Utils.FontHelper;

import java.util.ArrayList;

import static com.eyalengel.blurdemo.Model.AppConstants.*;

public class MainActivity extends MyActionBarActivity implements OnFragmentInteractionListener {

    private TabLayout tabLayout;
    private FragmentManager fragmentManager;
    private ArrayList<String> tabIconStrings;
    private ArrayList<String> tabTitleStrings;
    private ArrayList<String> tabColorStrings;
    private FeedFragment feedFragment;
    private ScoreboardFragment scoreboardFragment;
    private MessagesFragment messagesFragment;
    private GamesFragment gamesFragment;
    private GroupsFragment groupsFragment;
    private Fragment currFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            //Restore the fragment's instance
            currFragment = getSupportFragmentManager().getFragment(savedInstanceState, "ConversationFragment");
        }
        setContentView(R.layout.activity_main);
        getUIComponents();
        initFragmentManager();
        initTabIconStrings();
        initTabTitleStrings();
        initTabColorStrings();
        createFragments();
        setupTabLayout();
        changeTabsFont(FEED_TAB_INDEX);
        setCurrentFragment(FEED_TAB_INDEX);
        setTabListener();
    }

    private void changeTabsFont(int chosenTabIndex) {
        int[] badgeNumbers = new int[]{23, 6, 5, 4, 3}; //just random badge numbers for testing
        Typeface typeface = FontHelper.getTypeface(getApplicationContext(), FontHelper.FONTAWESOME);

        for (int j = 0; j < NUM_OF_TABS; j++) {
            //get a single Tab from TabLayout
            TabLayout.Tab tab = tabLayout.getTabAt(j);
            //get the Tab custom view
            View v = tab.getCustomView();
            //set Tab custom view according to its position in the TabLayout
            setTabIcon(v, j, chosenTabIndex, typeface);
            setTabIconNotificationBadge(v, j, badgeNumbers[j]);
        }
    }

    private void setTabIcon(View v, int j, int chosenTabIndex, Typeface typeface) {

        if (chosenTabIndex == j)
            setSelectedTabIcon(v, j, typeface);
        else
            setNotSelectedTabIcon(v, j, typeface);

    }

    private void setTabIconNotificationBadge(View v, int tabIndex, int badgeNumber) {
        GradientDrawable gdBadge;
        TextView badgeTV = (TextView) v.findViewById(R.id.tab_icon_badge);

        badgeTV.setTextColor(Color.parseColor(tabColorStrings.get(tabIndex)));
        badgeTV.setText(badgeNumber + EMPTY_STRING);
        gdBadge = (GradientDrawable) badgeTV.getBackground();
        gdBadge.setColor(Color.parseColor(WHITE));
        gdBadge.setStroke(BADGE_STROKE_WIDTH, Color.parseColor(tabColorStrings.get(tabIndex)));
    }

    private void setSelectedTabIcon(View v, int tabIndex, Typeface typeface) {
        GradientDrawable gdIcon;

        TextView iconTV = (TextView) v.findViewById(R.id.icon_awesome_font_string);

        iconTV.setText(tabIconStrings.get(tabIndex));
        iconTV.setTypeface(typeface);
        iconTV.setTextColor(Color.parseColor(tabColorStrings.get(tabIndex)));
        gdIcon = (GradientDrawable) iconTV.getBackground();
        gdIcon.setColor(Color.parseColor(WHITE));
        gdIcon.setStroke(BADGE_STROKE_WIDTH, Color.parseColor(tabColorStrings.get(tabIndex)));
    }

    private void setNotSelectedTabIcon(View v, int tabIndex, Typeface typeface) {
        GradientDrawable gdIcon;

        TextView iconTV = (TextView) v.findViewById(R.id.icon_awesome_font_string);

        iconTV.setText(tabIconStrings.get(tabIndex));
        iconTV.setTypeface(typeface);
        iconTV.setTextColor(Color.parseColor(WHITE));
        gdIcon = (GradientDrawable) iconTV.getBackground();
        gdIcon.setColor(Color.parseColor(tabColorStrings.get(tabIndex)));
        gdIcon.setStroke(BADGE_STROKE_WIDTH, Color.parseColor(tabColorStrings.get(tabIndex)));
    }


    private void setupTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_icon_with_badge));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_icon_with_badge));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_icon_with_badge));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_icon_with_badge));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_icon_with_badge));
    }

    private void setTabListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentFragment(tab.getPosition());
                changeTabsFont(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setCurrentFragment(int tabPosition) {
        switch (tabPosition) {
            case FEED_TAB_INDEX:
                replaceFragment(feedFragment);
                break;
            case SCOREBOARD_TAB_INDEX:
                replaceFragment(scoreboardFragment);
                break;
            case MESSAGES_TAB_INDEX:
                replaceFragment(messagesFragment);
                break;
            case GAMES_TAB_INDEX:
                replaceFragment(gamesFragment);
                break;
            case GROUPS_TAB_INDEX:
                replaceFragment(groupsFragment);
                break;
        }
    }

    private void replaceFragment(Fragment fragment) {
        currFragment = fragment;
        fragmentManager.beginTransaction().replace(R.id.main_content_frame, fragment).addToBackStack(null).commit();
    }

    private void createFragments() {
        feedFragment = new FeedFragment();
        scoreboardFragment = new ScoreboardFragment();
        messagesFragment = new MessagesFragment();
        gamesFragment = new GamesFragment();
        groupsFragment = new GroupsFragment();
    }

    private void initTabColorStrings() {
        String[] tabColors = getResources().getStringArray(R.array.tab_color_strings);
        tabColorStrings = new ArrayList<>();

        for (int i = 0; i < NUM_OF_TABS; i++)
            tabColorStrings.add(tabColors[i]);
    }

    private void initTabTitleStrings() {
        String[] tabTitles = getResources().getStringArray(R.array.fragment_titles);
        tabTitleStrings = new ArrayList<>();

        for (int i = 0; i < NUM_OF_TABS; i++)
            tabTitleStrings.add(tabTitles[i]);
    }

    private void initTabIconStrings() {
        String[] iconStrings = getResources().getStringArray(R.array.icon_strings);
        tabIconStrings = new ArrayList<>();

        for (int i = 0; i < NUM_OF_TABS; i++)
            tabIconStrings.add(iconStrings[i]);
    }

    private void initFragmentManager() {
        fragmentManager = getSupportFragmentManager();
    }

    private void getUIComponents() {
        tabLayout = (TabLayout) findViewById(R.id.bottom_tab_layout);
    }

    @Override
    public void onFragmentInteraction(Bundle args) {

        switch (args.getString("action")) {
            case "change_action_bar":
                setActionBarInFragment(args);
                break;
            case "switch_to_conversation_fragment":
                switchToConversationFragment(args);
        }
    }

    private void setActionBarInFragment(Bundle args) {
        String fragmentTitle = args.getString("fragment_title");

        setCustomActionBar();
        changeStatuBarColor(R.color.status_bar_color);

        switch (fragmentTitle) {
            case FEED_STR: //remove this case if want to show actionbar in Feed fragment
                hideActionBar();
                break;
            case CONVERSATIONS_STR:
                showActionBar();
                setActionbarCustomView(R.layout.custom_conversation_action_bar);
                setActionBarBackgroundColor(MESSAGES_TAB_INDEX);
                showActionbarBackButton(false);
                break;
            default:
                showActionBar();
                setActionBarTitle(fragmentTitle, this);
                setActionBarBackgroundColor(args.getInt("fragment_tab_index"));
                break;
        }
    }

    private void switchToConversationFragment(Bundle bundle) {
        ConversationFragment frag = new ConversationFragment();
        frag.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
                .replace(R.id.main_content_frame, frag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "ConversationFragment", currFragment);
    }

}
