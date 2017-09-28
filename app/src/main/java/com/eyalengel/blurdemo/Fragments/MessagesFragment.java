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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.eyalengel.blurdemo.Activities.MainActivity;
import com.eyalengel.blurdemo.Adapters.MessagesListAdapter;
import com.eyalengel.blurdemo.Listeners.OnFragmentInteractionListener;
import com.eyalengel.blurdemo.Model.MessagesRow;
import com.eyalengel.blurdemo.R;
import com.eyalengel.blurdemo.Utils.DividerItemDecoration;
import com.eyalengel.blurdemo.Utils.FontHelper;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;

import static com.eyalengel.blurdemo.Model.AppConstants.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    private Context context;
    private View mainView;
    private EditText searchBar;
    private RecyclerView messagesRecyclerView;
    private ArrayList<MessagesRow> messagesListData;
    private ArrayList<MessagesRow> tempMessagesList;
    private ArrayList<MessagesRow> filteredMessages;
    private MessagesListAdapter myAdapter;
    private OnFragmentInteractionListener mListener;

    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_messages, container, false);
        setFragmentActionBar();
        context = getActivity().getApplicationContext();
        setHasOptionsMenu(true);
        getUIComponents();
        setListeners();

        return mainView;
    }

    private void setListeners() {
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBar.setFocusable(true);
                searchBar.setCursorVisible(true);
            }
        });

        messagesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                searchBar.setCursorVisible(false);
                searchBar.setFocusable(true);
            }

        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Toast.makeText(getActivity(), "Text Changed", Toast.LENGTH_SHORT).show();
                filteredMessages.clear();
                if(s.length() == 0)
                    myAdapter.notifyDataSetChanged();
                else {
                    for (MessagesRow msgRow : messagesListData) {
                        if (msgRow.getMsgPreview().toLowerCase().contains(s)) {
                            filteredMessages.add(msgRow);
                        }
                    }
                    myAdapter.filterSearchedMessages(filteredMessages);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    messagesListData.clear();
                    messagesListData.addAll(tempMessagesList);
                }
                return false;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.messages_menu, menu);
        menu.findItem(R.id.action_create_new_message).setIcon(
                new IconDrawable(context, FontAwesomeIcons.fa_plus_square_o)
                        .colorRes(R.color.white)
                        .sizeDp(MENU_ITEM_ICON_SIZE_DP));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_create_new_message) {
            //Todo createNewMessage();
            Toast.makeText(context, "Create New Message", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnFragmentInteractionListener) context;
//        parentActivity.showActionBar();
    }

    private void getUIComponents() {
        searchBar = (EditText) mainView.findViewById(R.id.messages_search_edittext);
        searchBar.setHint(R.string.search_hint);
        searchBar.setCursorVisible(false);
        Typeface typeface = FontHelper.getTypeface(context, FontHelper.FONTAWESOME);
        searchBar.setTypeface(typeface);
        messagesRecyclerView = (RecyclerView) mainView.findViewById(R.id.messages_list);
    }

    @Override
    public void onResume() {
        super.onResume();
        messagesListData = new ArrayList<>();
        filteredMessages = new ArrayList<>();
        tempMessagesList = new ArrayList<>();
        setRecyclerView();

        if (searchBar != null) {
            searchBar.setCursorVisible(false);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void setFragmentActionBar() {
        Bundle bundle = new Bundle();
        bundle.putString("action", "change_action_bar");
        bundle.putString("fragment_title", MESSAGES_STR);
        bundle.putInt("fragment_tab_index", MESSAGES_TAB_INDEX);

        mListener.onFragmentInteraction(bundle);
    }

    private void setRecyclerView() {

        myAdapter = new MessagesListAdapter(messagesListData, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());

        messagesRecyclerView.setHasFixedSize(false);
        messagesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        messagesRecyclerView.setLayoutManager(mLayoutManager);
        messagesRecyclerView.addItemDecoration(new DividerItemDecoration(context));
        messagesRecyclerView.setAdapter(myAdapter);
        setRecyclerViewTouchListener();

        insertMessagesDataToList();
    }

    private void setRecyclerViewTouchListener() {
        messagesRecyclerView.addOnItemTouchListener(new MessagesListAdapter.RecyclerTouchListener(context, messagesRecyclerView, new MessagesListAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
//               when message row is clicked, launch full chat fragment
                Bundle bundle = new Bundle();
                bundle.putString("action", "switch_to_conversation_fragment");
                bundle.putString("other_user_first_name", messagesListData.get(position).getUserFirstName());

                mListener.onFragmentInteraction(bundle);

            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));
    }

    private void insertMessagesDataToList() {
        String msgPreview = "Lorem ipsum dolor sit amet pulcher veni fugit altra untumulis sectilim dentori alles klar der kommisar";
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_profile_pic);

        MessagesRow row = new MessagesRow(bm, "Kirsten", "Nelson", "David king of Israel", "12m", 1);
        messagesListData.add(row);

        row = new MessagesRow(bm, "John", "Smith", msgPreview, "1h", 4);
        messagesListData.add(row);

        row = new MessagesRow(bm, "Jane", "Smith", msgPreview, "4h", 0);
        messagesListData.add(row);

        row = new MessagesRow(bm, "Jane", "Johnson", msgPreview, "MON", 0);
        messagesListData.add(row);

        row = new MessagesRow(bm, "Steve", "Smith", msgPreview, "SAT", 0);
        messagesListData.add(row);

        row = new MessagesRow(bm, "Kirsten, John & Jane", "", msgPreview, "1/2/15", 0);
        messagesListData.add(row);

        row = new MessagesRow(bm, "Kelly", "James", msgPreview, "1/2/15", 0);
        messagesListData.add(row);

        row = new MessagesRow(bm, "Oren", "Schindler", "Hi David how are you?", "1/2/15", 0);
        messagesListData.add(row);

        row = new MessagesRow(bm, "Bill", "Clinton", "Hi David hoe are you?", "1/4/15", 1);
        messagesListData.add(row);

        row = new MessagesRow(bm, "Harry", "Potter", msgPreview, "1/8/15", 0);
        messagesListData.add(row);

        tempMessagesList.addAll(messagesListData);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
