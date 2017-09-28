package com.eyalengel.blurdemo.Fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eyalengel.blurdemo.Adapters.FeedListAdapter;
import com.eyalengel.blurdemo.Listeners.OnFragmentInteractionListener;
import com.eyalengel.blurdemo.Model.FeedRow;
import com.eyalengel.blurdemo.R;
import com.eyalengel.blurdemo.Utils.FontHelper;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;

import static com.eyalengel.blurdemo.Model.AppConstants.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {

    private Context context;
    private View mainView;
    private TextView settingsIcon;
    private CircularImageView profilePic;
    private TextView nameTV;
    private TextView rightQuoteIcon;
    private TextView leftQuoteIcon;
    private EditText quoteEditText;
    private TextView todayPtsTV;
    private TextView weekPtsTV;
    private TextView overallPtsTV;
    private TextView rankTV;
    private TextView dateTV;
    private RecyclerView recyclerView;
    private ArrayList<FeedRow> feedRowsData;
    private FeedListAdapter myAdapter;

    private OnFragmentInteractionListener mListener;

    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_feed, container, false);
        setFragmentActionBar();
        context = getActivity().getApplicationContext();

        getUIComponents();
        setAwesomeFontIcons();
        setUIComponents();
        setListeners();

        return mainView;
    }

//    private void setActionBar() {
//        parentActivity.setActionBar(FEED_STR, FEED_TAB, false);
//    }
//
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnFragmentInteractionListener)context;
//        parentActivity.hideActionBar();
    }

    private void setRecyclerView() {
        myAdapter = new FeedListAdapter(feedRowsData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);

        insertDataIntoList();
    }

    private void insertDataIntoList() {
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_profile_pic);

        FeedRow feedRow = new FeedRow(bm, "Kirsten", "\"Ready for the weekend!!\"", "2m");
        feedRowsData.add(feedRow);

        feedRow = new FeedRow(bm, "Kirsten", "\"Rain rain go away\"", "5m");
        feedRowsData.add(feedRow);

        feedRow = new FeedRow(bm, "Ryan", "\"This weather is crazy\"", "15m");
        feedRowsData.add(feedRow);

        feedRow = new FeedRow(bm, "Zooey", "\"I’ve got a show to be on\"", "26m");
        feedRowsData.add(feedRow);

        feedRow = new FeedRow(bm, "Courtney", "\"Who\'s up for a big time GoT premiere game night!\"", "1h");
        feedRowsData.add(feedRow);

        feedRow = new FeedRow(bm, "Courtney", "\"Feeling good\"", "1h");
        feedRowsData.add(feedRow);

        feedRow = new FeedRow(bm, "Donald", "\"Make America great again!\"", "1h");
        feedRowsData.add(feedRow);

        feedRow = new FeedRow(bm, "John", "\"Best day ever!!!\"", "1h");
        feedRowsData.add(feedRow);

        feedRow = new FeedRow(bm, "Stacey", "\"Go watch wonder woman\"", "2h");
        feedRowsData.add(feedRow);

        feedRow = new FeedRow(bm, "Natasha", "\"Gaming is life\"", "2h");
        feedRowsData.add(feedRow);

        feedRow = new FeedRow(bm, "Michael", "\"Blur changed my life. Thank you!\"", "2h");
        feedRowsData.add(feedRow);

        feedRow = new FeedRow(bm, "Roger", "\"Feeling lucky\"", "4h");
        feedRowsData.add(feedRow);

        myAdapter.notifyDataSetChanged();
    }

    private void setListeners() {
        quoteEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                quoteEditText.setFocusable(true);
                //TODO make text disappear when it's too long
                return true;
            }
        });

        quoteEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    quoteEditText.setEnabled(true);
                    quoteEditText.setSelection(quoteEditText.getText().length());
                } else {
                    quoteEditText.setFocusable(false);
                    quoteEditText.setEnabled(true);
                }
            }
        });

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Settings Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAwesomeFontIcons() {
        Typeface tf = FontHelper.getTypeface(context, FontHelper.FONTAWESOME);
        settingsIcon.setTypeface(tf);
        leftQuoteIcon.setTypeface(tf);
        rightQuoteIcon.setTypeface(tf);
    }

    private void setUIComponents() {
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_profile_pic);
        profilePic.setImageBitmap(bm);
    }

    private void getUIComponents() {
        settingsIcon = (TextView) mainView.findViewById(R.id.feed_icon_setting_textview);
        profilePic = (CircularImageView) mainView.findViewById(R.id.feed_circle_imageview);
        nameTV = (TextView) mainView.findViewById(R.id.feed_name_textview);
        rightQuoteIcon = (TextView) mainView.findViewById(R.id.right_quote_awesome_font);
        leftQuoteIcon = (TextView) mainView.findViewById(R.id.left_quote_awesome_font);
        quoteEditText = (EditText) mainView.findViewById(R.id.quote_edittext);
        todayPtsTV = (TextView) mainView.findViewById(R.id.num_of_pts_today);
        weekPtsTV = (TextView) mainView.findViewById(R.id.num_of_pts_this_week);
        overallPtsTV = (TextView) mainView.findViewById(R.id.num_of_total_pts);
        rankTV = (TextView) mainView.findViewById(R.id.rank_number);
        dateTV = (TextView) mainView.findViewById(R.id.feed_top_date);
        recyclerView = (RecyclerView) mainView.findViewById(R.id.feed_recyclerview);
    }

    @Override
    public void onResume() {
        super.onResume();
        feedRowsData = new ArrayList<>();
        setRecyclerView();
    }

    private void setFragmentActionBar() {
        Bundle bundle = new Bundle();
        bundle.putString("action", "change_action_bar");
        bundle.putString("fragment_title", FEED_STR);
        bundle.putInt("fragment_tab_index", FEED_TAB_INDEX);

        mListener.onFragmentInteraction(bundle);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
