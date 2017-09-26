package com.eyalengel.blurdemo.Fragments;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eyalengel.blurdemo.Activities.MainActivity;
import com.eyalengel.blurdemo.Adapters.ConversationAdapter;
import com.eyalengel.blurdemo.Listeners.OnFragmentInteractionListener;
import com.eyalengel.blurdemo.Model.MessageBubbleInfo;
import com.eyalengel.blurdemo.R;
import com.eyalengel.blurdemo.Utils.FontHelper;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.widget.IconButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.eyalengel.blurdemo.Model.AppConstants.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationFragment extends Fragment {

    private View myView;
    private Context context;

    private TextView usernameTV;
    private TextView onlineDot;
    private TextView backButtonIcon;
    private RelativeLayout customActionBarLayout;
    private CircularImageView imageView;
    private EditText typingArea;
    private TextView sendButton;
    private TextView messageLeftArrow;
    private ArrayList<MessageBubbleInfo> conversationMessages;
    private ConversationAdapter myAdapter;
    private RecyclerView conversationRecyclerView;
    private boolean isOtherUserOnline;
    private IconButton backIcon;
    private OnFragmentInteractionListener mListener;

    private long lastMsgTime;

    public ConversationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_conversation, container, false);
        context = getActivity().getApplicationContext();

        setHasOptionsMenu(true);
        setMyActionBar();
        getUIComponents();
        setActionBarComponents();
        setUIComponentsTypeface();
        this.lastMsgTime = getLastMsgTime();
        setListeners();

        return myView;
    }

    private void setListeners() {
//        backButtonIcon.setOnClickListener(this);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSendMessageClick();
            }
        });
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBackButtonClick();
            }
        });
        typingArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!typingArea.getText().toString().equals(EMPTY_STRING))
                    handleSendMessageClick();
            }
        });
    }

    private void setUIComponentsTypeface() {
        Typeface typeface = FontHelper.getTypeface(context, FontHelper.FONTAWESOME);
        sendButton.setTypeface(typeface);
        messageLeftArrow.setTypeface(typeface);
//        backButtonIcon.setTypeface(typeface);
        backIcon.setBackground(new IconDrawable(context, FontAwesomeIcons.fa_chevron_circle_left)
                .colorRes(R.color.white));
    }

    private void setRecyclerView() {

        myAdapter = new ConversationAdapter(conversationMessages, context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);

        conversationRecyclerView.setHasFixedSize(false);
        conversationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        conversationRecyclerView.setLayoutManager(mLayoutManager);
        conversationRecyclerView.setAdapter(myAdapter);

        insertMessagesDataToList();


    }

    private void insertMessagesDataToList() {
        MessageBubbleInfo bubble = new MessageBubbleInfo("Catching the game tonight?", "today @ 09:45 am", MSG_TYPE_SELF);
        conversationMessages.add(bubble);
        bubble = new MessageBubbleInfo("Fo Sho! Where you want to meet?", "today @ 11:14 am", MSG_TYPE_OTHER);
        conversationMessages.add(bubble);
        bubble = new MessageBubbleInfo("Here's the spot", "", MSG_TYPE_SELF);
        conversationMessages.add(bubble);
        bubble = new MessageBubbleInfo("Kewl see you there", "", MSG_TYPE_LOCKED);
        conversationMessages.add(bubble);

        myAdapter.notifyDataSetChanged();

    }

    private void setOnlineDot(boolean userOnline) {
        if (userOnline) {
            onlineDot.setBackgroundResource(R.drawable.online_background);
        } else
            onlineDot.setBackgroundResource(R.drawable.offline_background);
    }

    private void setMyActionBar() {
        ((MainActivity)getActivity()).showActionBar();
        ((MainActivity)getActivity()).setActionbarCustomView(R.layout.custom_conversation_action_bar);
        ((MainActivity)getActivity()).setActionBarBackgroundColor(MESSAGES_TAB_INDEX);
        ((MainActivity)getActivity()).showActionbarBackButton(false);
    }

    private void setActionBarComponents() {
        isOtherUserOnline = true;

        setOnlineDot(isOtherUserOnline);
        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic));
        usernameTV.setText(getFirstName());
//        backButtonIcon.setText(getResources().getString(R.string.fa_arrow_circle_left));
    }

    private String getFirstName() {
        String firstName = getArguments().getString("other_user_first_name");

        return firstName;
    }


    private void getUIComponents() {
        customActionBarLayout = (RelativeLayout) getConversationActionBarView();


        backIcon = (IconButton) customActionBarLayout.getChildAt(0);
        onlineDot = (TextView) customActionBarLayout.getChildAt(1);
        imageView = (CircularImageView) customActionBarLayout.getChildAt(2);
        usernameTV = (TextView) customActionBarLayout.getChildAt(3);

        conversationRecyclerView = (RecyclerView) myView.findViewById(R.id.conversation_recyclerview);
        sendButton = (TextView) myView.findViewById(R.id.send_message_button_tv);
        messageLeftArrow = (TextView) myView.findViewById(R.id.conversation_arrow_left_tv);
        typingArea = (EditText) myView.findViewById(R.id.message_edittext);
//        backIcon = (IconButton)findViewById(R.id.conversation_back_button_icon);
    }

    public View getConversationActionBarView() {
        Window window = ((MainActivity)getActivity()).getWindow();
        View v = window.getDecorView();
        int resId = getResources().getIdentifier("conversation_actionbar_container", "id", ((MainActivity)getActivity()).getPackageName());
        return v.findViewById(resId);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.conversation_menu, menu);
        menu.findItem(R.id.action_conversation_info)
                .setIcon(new IconDrawable(context, FontAwesomeIcons.fa_info_circle)
                .colorRes(R.color.white)
                .sizeDp(MENU_ITEM_ICON_SIZE_DP));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_conversation_info) {
            //Todo createNewMessage();
            Toast.makeText(context, "Get Info Clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == android.R.id.home) {
            //Toast.makeText(this, "Go Back", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            //onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }

    @Override
    public void onResume() {
        super.onResume();
        conversationMessages = new ArrayList<>();
        setRecyclerView();
    }


    private void handleSendMessageClick() {
        String msg = typingArea.getText().toString().trim();
        typingArea.setText(EMPTY_STRING);
        addMessageToConversation(msg);


    }

    private void addMessageToConversation(String msgText) {
        Calendar msgTime = Calendar.getInstance();

        MessageBubbleInfo message = new MessageBubbleInfo(msgText, getFormattedDate(context, msgTime.getTimeInMillis()), MSG_TYPE_SELF);
        conversationMessages.add(message);
        myAdapter.notifyDataSetChanged();
        conversationRecyclerView.smoothScrollToPosition(myAdapter.getItemCount() - 1);
    }

    private void handleBackButtonClick() {
        getFragmentManager().popBackStack();
//        getActivity().finish();
//        return;
    }

    private String getTimeStamp() {

        String timeStamp;

        String delegate = "hh:mm aaa";
        return (String) DateFormat.format(delegate, Calendar.getInstance().getTime());

    }

    public String getFormattedDate(Context context, long msgTimeInMilis) {
        Calendar msgTime = Calendar.getInstance();
        Calendar lastMsgTime = Calendar.getInstance();

        msgTime.setTimeInMillis(msgTimeInMilis);
        lastMsgTime.setTimeInMillis(this.lastMsgTime);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "hh:mm aa";
        final String dateTimeFormatString = "EEEE, MMMM d, hh:mm aa";
        final long HOURS = 60 * 60 * 60;

        if (now.get(Calendar.DATE) == msgTime.get(Calendar.DATE)) {
            if (msgTime.get(Calendar.HOUR_OF_DAY) - lastMsgTime.get(Calendar.HOUR_OF_DAY) > 1) {
                this.lastMsgTime = msgTimeInMilis;
                return "Today @ " + DateFormat.format(timeFormatString, msgTime);
            } else
                return EMPTY_STRING;
        } else if (now.get(Calendar.DATE) - msgTime.get(Calendar.DATE) == 1) {
            return "Yesterday @ " + DateFormat.format(timeFormatString, msgTime);
        } else if (now.get(Calendar.YEAR) == msgTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, msgTime).toString();
        } else {
            return DateFormat.format("MMMM dd yyyy, h:mm aa", msgTime).toString();
        }
    }

    private long getLastMsgTime() {
        String myDate = "2017/09/25 11:20 am";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm aa");
        Date date = null;
        try {
            date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = date.getTime();

        return millis;
    }
}