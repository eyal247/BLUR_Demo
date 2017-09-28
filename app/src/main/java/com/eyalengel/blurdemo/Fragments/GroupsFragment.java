package com.eyalengel.blurdemo.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eyalengel.blurdemo.Activities.MainActivity;
import com.eyalengel.blurdemo.Listeners.OnFragmentInteractionListener;
import com.eyalengel.blurdemo.R;

import static com.eyalengel.blurdemo.Model.AppConstants.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsFragment extends Fragment {

    Context context;
    private View mainView;
    private MainActivity parentActivity;

    private OnFragmentInteractionListener mListener;

    public GroupsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_groups, container, false);
        setFragmentActionBar();
        context = getActivity().getApplicationContext();

        return mainView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnFragmentInteractionListener)context;
//        parentActivity.showActionBar();
    }

    private void setFragmentActionBar() {
        Bundle bundle = new Bundle();
        bundle.putString("action", "change_action_bar");
        bundle.putString("fragment_title", GROUPS_STR);
        bundle.putInt("fragment_tab_index", GROUPS_TAB_INDEX);

        mListener.onFragmentInteraction(bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
