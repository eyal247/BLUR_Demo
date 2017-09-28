package com.eyalengel.blurdemo.Fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eyalengel.blurdemo.Adapters.ScoreboardListAdapter;
import com.eyalengel.blurdemo.Listeners.OnFragmentInteractionListener;
import com.eyalengel.blurdemo.Model.ScoreboardRow;
import com.eyalengel.blurdemo.R;
import com.eyalengel.blurdemo.Utils.DividerItemDecoration;

import java.util.ArrayList;

import static com.eyalengel.blurdemo.Model.AppConstants.*;


public class ScoreboardFragment extends Fragment {

    private Context context;
    private View mainView;
    private RelativeLayout scoreboardLayout;
    private RelativeLayout todaySummaryLayout;
    private RelativeLayout prizesAndProductsLayout;
    private TextView todaySummaryButton;
    private TextView scoreboardButton;
    private TextView prizesProductsButton;
    private TextView ptsTextView;
    private RecyclerView scoreBoardRecyclerView;
    private ArrayList<ScoreboardRow> scoreboardRowsData;
    private ScoreboardListAdapter myAdapter;
    private int selectedButton;

    private OnFragmentInteractionListener mListener;

    public ScoreboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_scoreboard, container, false);
        context = getActivity().getApplicationContext();
        selectedButton = SCOREBOARD_BUTTON;
        setFragmentActionBar();
        getUIComponents();
        setButtonsListeners();
        return mainView;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnFragmentInteractionListener)context;
    }

    private void setButtonsListeners() {
        todaySummaryButton.setOnClickListener(onClickListener);
        scoreboardButton.setOnClickListener(onClickListener);
        prizesProductsButton.setOnClickListener(onClickListener);

    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.scoreboard_today_pts_button:
                    handleTodayPtsButtonClick();
                    break;
                case R.id.scoreboard_button:
                    handleScoreboardButtonClick();
                    break;
                case R.id.scoreboard_prizes_products_button:
                    handlePrizesButtonClick();
                    break;
            }

        }
    };

    private void handlePrizesButtonClick() {
        if(selectedButton == SCOREBOARD_BUTTON){
            prizesProductsButton.setBackgroundResource(R.drawable.scoreboard_selected_button);
            prizesProductsButton.setTextColor(Color.parseColor(WHITE));
            scoreboardButton.setBackgroundResource(R.drawable.scoreboard_not_selected_button);
            scoreboardButton.setTextColor(Color.parseColor(BUTTONS_GRAY));
            scoreboardLayout.setVisibility(View.GONE);
            prizesAndProductsLayout.setVisibility(View.VISIBLE);
        } else if(selectedButton == TODAY_SUMMARY_BUTTON){
            prizesProductsButton.setBackgroundResource(R.drawable.scoreboard_selected_button);
            prizesProductsButton.setTextColor(Color.parseColor(WHITE));
            todaySummaryButton.setBackgroundResource(R.drawable.scoreboard_not_selected_button);
            todaySummaryButton.setTextColor(Color.parseColor(BUTTONS_GRAY));
            todaySummaryLayout.setVisibility(View.GONE);
            prizesAndProductsLayout.setVisibility(View.VISIBLE);
        }

        selectedButton = PRIZES_PRODUCTS_BUTTON;
    }

    private void handleScoreboardButtonClick() {
        if(selectedButton == TODAY_SUMMARY_BUTTON){
            scoreboardButton.setBackgroundResource(R.drawable.scoreboard_selected_button);
            scoreboardButton.setTextColor(Color.parseColor(WHITE));
            todaySummaryButton.setBackgroundResource(R.drawable.scoreboard_not_selected_button);
            todaySummaryButton.setTextColor(Color.parseColor(BUTTONS_GRAY));
            todaySummaryLayout.setVisibility(View.GONE);
            scoreboardLayout.setVisibility(View.VISIBLE);
        } else if (selectedButton == PRIZES_PRODUCTS_BUTTON){
            scoreboardButton.setBackgroundResource(R.drawable.scoreboard_selected_button);
            scoreboardButton.setTextColor(Color.parseColor(WHITE));
            prizesProductsButton.setBackgroundResource(R.drawable.scoreboard_not_selected_button);
            prizesProductsButton.setTextColor(Color.parseColor(BUTTONS_GRAY));
            prizesAndProductsLayout.setVisibility(View.GONE);
            scoreboardLayout.setVisibility(View.VISIBLE);
        }
        selectedButton = SCOREBOARD_BUTTON;
    }

    private void handleTodayPtsButtonClick() {
        if(selectedButton == SCOREBOARD_BUTTON) {
            todaySummaryButton.setBackgroundResource(R.drawable.scoreboard_selected_button);
            todaySummaryButton.setTextColor(Color.parseColor(WHITE));
            scoreboardButton.setBackgroundResource(R.drawable.scoreboard_not_selected_button);
            scoreboardButton.setTextColor(Color.parseColor(BUTTONS_GRAY));
            scoreboardLayout.setVisibility(View.GONE);
            todaySummaryLayout.setVisibility(View.VISIBLE);
        } else if (selectedButton == PRIZES_PRODUCTS_BUTTON) {
            todaySummaryButton.setBackgroundResource(R.drawable.scoreboard_selected_button);
            todaySummaryButton.setTextColor(Color.parseColor(WHITE));
            prizesProductsButton.setBackgroundResource(R.drawable.scoreboard_not_selected_button);
            prizesProductsButton.setTextColor(Color.parseColor(BUTTONS_GRAY));
            prizesAndProductsLayout.setVisibility(View.GONE);
            todaySummaryLayout.setVisibility(View.VISIBLE);
        }

        selectedButton = TODAY_SUMMARY_BUTTON;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onResume() {
        super.onResume();
//        setFragmentActionBar();
        scoreboardRowsData = new ArrayList<>();
        setRecyclerView();
    }

    private void setFragmentActionBar() {
        Bundle bundle = new Bundle();
        bundle.putString("action", "change_action_bar");
        bundle.putString("fragment_title", SCOREBOARD_STR);
        bundle.putInt("fragment_tab_index", SCOREBOARD_TAB_INDEX);

        mListener.onFragmentInteraction(bundle);
    }

    private void setRecyclerView() {

        myAdapter = new ScoreboardListAdapter(scoreboardRowsData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());

        scoreBoardRecyclerView.setHasFixedSize(false);
        scoreBoardRecyclerView.setItemAnimator(new DefaultItemAnimator());
        scoreBoardRecyclerView.setLayoutManager(mLayoutManager);
        scoreBoardRecyclerView.addItemDecoration(new DividerItemDecoration(context));
        scoreBoardRecyclerView.setAdapter(myAdapter);

        insertScoreboardDataToList();
    }

    private void insertScoreboardDataToList() {
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_profile_pic);

        ScoreboardRow row = new ScoreboardRow(bm, 1, "Oren", "Schindler", 100, true);
        scoreboardRowsData.add(row);

        row = new ScoreboardRow(bm, 2, "Kirsten", "Nelson", 80, false);
        scoreboardRowsData.add(row);

        row = new ScoreboardRow(bm, 3, "John", "Smith", 77, false);
        scoreboardRowsData.add(row);

        row = new ScoreboardRow(bm, 4, "Jane", "Johnson", 74, false);
        scoreboardRowsData.add(row);

        row = new ScoreboardRow(bm, 5, "Steve", "Smith", 68, false);
        scoreboardRowsData.add(row);

        row = new ScoreboardRow(bm, 6, "Kelly", "James", 65, false);
        scoreboardRowsData.add(row);

        row = new ScoreboardRow(bm, 7, "James", "Kelly", 60, false);
        scoreboardRowsData.add(row);

        row = new ScoreboardRow(bm, 8, "Lorem" ,"Ipsum", 58, false);
        scoreboardRowsData.add(row);

        row = new ScoreboardRow(bm, 9, "Lorem", "Ipsum", 50, false);
        scoreboardRowsData.add(row);

        myAdapter.notifyDataSetChanged();
    }


    private void getUIComponents() {
        scoreBoardRecyclerView = (RecyclerView) mainView.findViewById(R.id.scoreboard_recyclerview);
        todaySummaryButton = (TextView) mainView.findViewById(R.id.scoreboard_today_pts_button);
        scoreboardButton = (TextView) mainView.findViewById(R.id.scoreboard_button);
        prizesProductsButton = (TextView) mainView.findViewById(R.id.scoreboard_prizes_products_button);
        ptsTextView = (TextView) mainView.findViewById(R.id.user_score);
        scoreboardLayout = (RelativeLayout)mainView.findViewById(R.id.scoreboard_layout);
        todaySummaryLayout = (RelativeLayout)mainView.findViewById(R.id.today_summary_layout);
        prizesAndProductsLayout = (RelativeLayout)mainView.findViewById(R.id.prizes_products_layout);
    }

}
