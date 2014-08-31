package com.msgme.msgme;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.msgme.msgme.BaseClasses.BaseActivity;
import com.msgme.msgme.adapters.MessagesAdapter;
import com.msgme.msgme.customViews.CustomPopupButton;
import com.msgme.msgme.database.AppContentProvider;
import com.msgme.msgme.vo.ContactMessages;
import com.msgme.msgme.vo.ContactsMap;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class PersonMessagesActivity extends BaseActivity {

    public static final int PICK_CONTACT = 0;
    public static final int GET_ICON_FROM_LIST = 1;
    public static final int LIST_VIEW_ANIMATION_DURATION = 500;
    public static final int LIST_VIEW_TRANSLATION_AMOUNT = 150;

    // TODO: Perhaps get this from server aswell?
    public ContactMessages contactMessages = null;

    //-------------
    //Auto complete
    //-------------
    MultiAutoCompleteTextView txtPhoneNo;

    private ArrayList<ContactsMap> mPeopleList;
    private SimpleAdapter mAdapter;
    private TextImage tiMessageBody = null;
    private Context context = this;
    private Boolean isMessageSent = false;
    private BroadcastReceiver mIntentReceiver;
    public ListView lvMessagesList = null;
    public MessagesAdapter adapter = null;
    private static final int WHAT = 1;
    private static final int TIME_TO_WAIT = 5000;

    public Boolean isRefreshIsNeeded = false;
    private static int mutex = 0;

    private CustomPopupButton mPopUpButton;
    private CustomPopupButton mPopUpButtonRight;

    Handler regularHandler = new Handler(new Handler.Callback() {


        @Override
        public boolean handleMessage(android.os.Message msg) {

            Log.i("MESSAGES_AWAKE", "MESSAGES_AWAKE");
            if (isRefreshIsNeeded) {
                //Refresh
                if (lvMessagesList == null)
                    lvMessagesList = (ListView) findViewById(R.id.messagesList);

                lvMessagesList.invalidateViews();

                new UpdateList().execute();

                isRefreshIsNeeded = false;

                Log.i("HANDLER_AWAKE", "REFRESHING!!!");

            } else
                Log.i("HANDLER_AWAKE", "NOT_REFRESHING!!!");

            regularHandler.sendEmptyMessageDelayed(msg.what, TIME_TO_WAIT);

            return true;
        }
    });


    private class UpdateList extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            if (mutex == 0)
                getMessagesByThreadId(true);
            ++mutex;

            return null;
        }


        protected void onPostExecute(String result) {
            --mutex;
            if (mutex == 0) {
                super.onPostExecute(result);
                ContentValues values = new ContentValues();
                values.put("read", true);
                context.getContentResolver().update(Uri.parse("content://sms/inbox"), values,
                        "thread_id=" + contactMessages.getThread_id(), null);

                showMessages();
            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        regularHandler.sendEmptyMessageDelayed(WHAT, TIME_TO_WAIT);

        mIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isRefreshIsNeeded = true;
                Log.i("REFRESH_IS_NEEDED", isRefreshIsNeeded.toString());
            }
        };

        IntentFilter intentFilter = new IntentFilter("com.msgme.msgme.message_recieved");
        this.registerReceiver(mIntentReceiver, intentFilter);
    }

    @Override
    protected void onPause() {

        super.onPause();
        regularHandler.removeMessages(WHAT);
        this.unregisterReceiver(this.mIntentReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_messages);

        onBackClick();

        onDeleteConversationClick();

        setUpSmsSendButtonClickListener();

        onIconsClick();

        fillContactsAutoComplete();

        mPopUpButton = (CustomPopupButton) findViewById(R.id.customPopupButton);
        mPopUpButtonRight = (CustomPopupButton) findViewById(R.id.customPopupButton_right);

        mPopUpButton.setOnPopupButtonDurationPassedListener(getPopupDurationPassedListener());
        mPopUpButtonRight.setOnPopupButtonDurationPassedListener(getPopupDurationPassedListener());

        EditText etMessageBody = (EditText) findViewById(R.id.etMessageBody);
        txtPhoneNo = (MultiAutoCompleteTextView) findViewById(R.id.txtPhoneNumber);

        registerForContextMenu(etMessageBody);

        tiMessageBody = new TextImage(etMessageBody, context);

        Intent intent = getIntent();
        if (!intent.getBooleanExtra("isNewMessage", false)) {
            txtPhoneNo.setVisibility(View.GONE);

            showMessagesState();

            //set sms as read
            ContentValues values = new ContentValues();
            values.put("read", true);
            context.getContentResolver().update(Uri.parse("content://sms/inbox"), values,
                    "thread_id=" + contactMessages.getThread_id(), null);

        }
        //New Message
        else {
            //remove delete conversation
            findViewById(R.id.ivDeleteConversations).setVisibility(View.GONE);

            txtPhoneNo.bringToFront();

            TextView tvPersonalMessagesTitle = (TextView) findViewById(R.id.txtPersonalMessages);
            tvPersonalMessagesTitle.setText("New Message");

            //Is invite friend? from settings
            boolean bIsInvite = false;
            Serializable isInvite = intent.getSerializableExtra("isInviteFriend");
            if (isInvite != null)
                bIsInvite = (Boolean) isInvite;
            if (bIsInvite) {
                SpannedString msg = new SpannedString("Hey! I am using SmarText now and my SMS is smarter! Join " +
                        "me: https://play.google.com/store/apps/details?id=com.msgme.msgme");
                tiMessageBody.setText(msg, msg.length());
            }

            txtPhoneNo.requestFocus();
            txtPhoneNo.setFocusableInTouchMode(true);

        }

    }

    private CustomPopupButton.OnPopupButtonDurationPassedListener getPopupDurationPassedListener() {
        return new CustomPopupButton.OnPopupButtonDurationPassedListener() {

            @Override
            public void onPopupButtonDurationPassedEvent() {
                animateListViewDown();
            }
        };
    }

    private void animateListViewDown() {

        animate(lvMessagesList).setDuration(LIST_VIEW_ANIMATION_DURATION).translationYBy
                (getListViewRowHeight()).setInterpolator(new
                AccelerateDecelerateInterpolator());
    }

    private void animateListViewUp() {

        animate(lvMessagesList).setDuration(LIST_VIEW_ANIMATION_DURATION).translationYBy
                (-getListViewRowHeight()).setInterpolator(new
                AccelerateDecelerateInterpolator());
    }

    private int getListViewRowHeight() {
        MessagesAdapter adapter = (MessagesAdapter) lvMessagesList.getAdapter();
        View view = adapter.getView(adapter.getCount() - 1, null, lvMessagesList);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        return view.getMeasuredHeight() + 26;
    }

    private int convertPixelsToDP(int pixels) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;

        return 0;
    }


    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
    }

    private void fillContactsAutoComplete() {
        txtPhoneNo = (MultiAutoCompleteTextView) findViewById(R.id.txtPhoneNumber);
        txtPhoneNo.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        mPeopleList = new ArrayList<ContactsMap>();

        txtPhoneNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchKey = s.toString();

                //Support multiple contacts
                searchKey = searchKey.substring(searchKey.lastIndexOf(ContactsMap.SEPERATOR) + 1, searchKey.length());

                if (!searchKey.equals("") && searchKey.length() >= 2) {
                    mPeopleList = populateContacts(searchKey);

                    mAdapter = new SimpleAdapter(context, mPeopleList, R.layout.custcontview, new String[]{"Name",
                            "Phone", "Type"}, new int[]{R.id.ccontName, R.id.ccontNo, R.id.ccontType});

                    txtPhoneNo.setAdapter(mAdapter);
                    txtPhoneNo.showDropDown();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private ArrayList<ContactsMap> populateContacts(String searchString) {
        Log.i("AUTO", searchString);
        mPeopleList.clear();

        String[] mSelectionArgs = {""};
        mSelectionArgs[0] = "1";

        String[] projection = new String[]{
                Contacts._ID, // the contact id column
                Contacts.DISPLAY_NAME}; // column if this contact is deleted

        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        Cursor people = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, projection, ContactsContract.Contacts.HAS_PHONE_NUMBER + " = ?" +
                        " and " +
                        Contacts.DISPLAY_NAME + " like " + "'" + searchString + "%'",
                mSelectionArgs, sortOrder);

        while (people.moveToNext()) {
            String contactName = people.getString(people.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = people.getString(people.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);

            while (phones.moveToNext()) {
                //store numbers and display a dialog letting the user select which.
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone
                        .NUMBER));
                String numberType = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone
                        .TYPE));

                ContactsMap NamePhoneType = new ContactsMap();

                NamePhoneType.put("Name", contactName);
                NamePhoneType.put("Phone", phoneNumber);
                if (numberType.equals("0"))
                    NamePhoneType.put("Type", "Work");
                else if (numberType.equals("1"))
                    NamePhoneType.put("Type", "Home");
                else if (numberType.equals("2"))
                    NamePhoneType.put("Type", "Mobile");
                else
                    NamePhoneType.put("Type", "Other");
                //Then add this map to the list.
                mPeopleList.add(NamePhoneType);
            }
            phones.close();
            //}
        }
        people.close();
        return mPeopleList;
    }

    /**
     *
     */
    private void showMessagesState() {
        //Get the ContactMessages That was clicked

        contactMessages = (ContactMessages) getIntent().getSerializableExtra("contactMessages");
        if (contactMessages.getThread_id() == null) {
            String from = (String) getIntent().getSerializableExtra("phone");
            Cursor cursor1 = getContentResolver().query(Uri.parse("content://sms/inbox"),
                    new String[]{"thread_id"}, "_id=?", new String[]{from}, null);
            cursor1.moveToFirst();
            contactMessages.setThread_id(cursor1.getString(cursor1.getColumnIndex("thread_id")));
        }

        TextView tvPersonalMessagesTitle = (TextView) findViewById(R.id.txtPersonalMessages);
        tvPersonalMessagesTitle.setText(contactMessages.getName() == null ? contactMessages.getPhone() :
                contactMessages.getName());

        getMessagesByThreadId(false);

        showMessages();
    }

    private void showMessages() {
        lvMessagesList = (ListView) findViewById(R.id.messagesList);
        lvMessagesList.setStackFromBottom(true);
        adapter = new MessagesAdapter(this, contactMessages.getMessages());
        lvMessagesList.setAdapter(adapter);
    }

    /**
     * Updates the list of message to parse back into view
     *
     * @param isHereAfterMessagesListItemClicked If this is true, we do not need to examine for trigger words because we
     *                                           only just arrived here from Main conversation activity. If false,
     *                                           it means we just received a new message
     *                                           hence, a new word to check as a trigger word
     */
    private void getMessagesByThreadId(boolean isHereAfterMessagesListItemClicked) {
        Uri mSmsinboxQueryUri = Uri.parse("content://sms");

        String[] mSelectionArgs = new String[1];
        mSelectionArgs[0] = contactMessages.getThread_id();

        Cursor cursor = getContentResolver().query(mSmsinboxQueryUri, new String[]{"_id", "thread_id", "date",
                "body", "type"}, "thread_id = ?", mSelectionArgs, "date DESC limit 45");

        if (cursor.getCount() > 0) {
            contactMessages.AddNewMsgs(cursor);
        }

        if (isHereAfterMessagesListItemClicked && !isMessageSent) {
            String bodyOfNewMessage = null;

            // Get the latest message entered to database
            Cursor lastMessageCursor = getContentResolver().query(mSmsinboxQueryUri, new String[]{"body"},
                    "thread_id = ?", mSelectionArgs, "_id DESC limit 1");
            if (lastMessageCursor.moveToFirst()) {

                // We have a last message, lets check if its a trigger word
                bodyOfNewMessage = lastMessageCursor.getString(lastMessageCursor.getColumnIndex("body"));
                examineSMSTextForTriggerWords(bodyOfNewMessage, CustomPopupButton.PopupButtonSide.RIGHT);
            }
            lastMessageCursor.close();
        }


        cursor.close();
    }

    /**
     * Will examine whether smsMessageBody contains a trigger word. If it does, it will light up the popup button
     *
     * @param smsMessageBody The text to examine for a trigger word
     */
    private void examineSMSTextForTriggerWords(String smsMessageBody, CustomPopupButton.PopupButtonSide side) {
        // Get the outgoing text and trim from both ends of the string white spaces.
        // + inside the regex denotes consecutive white spaces as well (not only a
        // single one)

        if (!TextUtils.isEmpty(smsMessageBody)) {
            String[] tokens = getTokenWordsFromIncomingMessageBody(smsMessageBody);

            // For each word token in array, check in data base if there is a
            // corresponding trigger word
            String triggerWordUrl = getFoundTriggerWordImageUrl(tokens);

            // If we have a url, we can execute a pop up animation
            if (triggerWordUrl != null) {
                activatePopUp(side);
            } else {
                Log.d("LOGGY", "We have no url");
            }
        }
    }

    private void activatePopUp(final CustomPopupButton.PopupButtonSide side) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                        closeKeyboard(mPopUpButton);
                animateListViewUp();

                // TODO: Perhaps get transition duration from server as well?
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        RelativeLayout.LayoutParams params = null;

                        switch (side) {
                            case LEFT:
                                mPopUpButton.onTriggerWordFound(500);
                                // SHOW LEFT BUTTON
//                                params = (RelativeLayout.LayoutParams) mPopUpButton.getLayoutParams();
//                                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                                break;
                            case RIGHT:
                                mPopUpButtonRight.onTriggerWordFound(500);
                                // SHOW RIGHT BUTTON
//                                params = (RelativeLayout.LayoutParams) mPopUpButton.getLayoutParams();
//                                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                break;
                            default:
                                break;

                        }

//                        mPopUpButton.setLayoutParams(params);
//                        mPopUpButton.onTriggerWordFound(500);
                    }
                }, LIST_VIEW_ANIMATION_DURATION + 150);
            }
        });

        mPopUpButton.setOnClickListenerToRootView(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: open pop up view animatedly
                closeKeyboard(mPopUpButton);
                Toast.makeText(PersonMessagesActivity.this, "Pop up clicked... animate view",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getFoundTriggerWordImageUrl(String[] tokens) {

        String triggerWordUrl = null;
        String[] columns = {AppContentProvider.COLUMN_TRIGGER_WORD,
                AppContentProvider.COLUMN_TRIGGER_WORD_IMAGE_URL};

        for (String string : tokens) {
            Cursor triggerWordsCursor = getContentResolver().query
                    (AppContentProvider.CONTENT_URI_ENGLISH,
                            columns, "UPPER(" + AppContentProvider
                                    .COLUMN_TRIGGER_WORD + ") =?",
                            new String[]{string.toUpperCase()}, null);

            // If we have results, save the url of that word token and exit loop
            // We leave the loop because currently, one word is enough
            if (triggerWordsCursor.moveToFirst()) {
                triggerWordUrl = triggerWordsCursor.getString(1);
                triggerWordsCursor.close();
                break;
            }
        }
        return triggerWordUrl;
    }

    private String[] getTokenWordsFromIncomingMessageBody(String smsMessageBody) {
        String whiteSpaceTrimmedText = smsMessageBody.replaceAll
                ("^\\s+|\\s+$", "");

        // Split the string using these delimiters. Again + denotes consecutive
        // chars as well not only a single one
        String delimeterss = "[ .,?!]+";
        return whiteSpaceTrimmedText.split(delimeterss);
    }

    private void setUpSmsSendButtonClickListener() {

        ImageView btnSend = (ImageView) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contact;
                MultiAutoCompleteTextView txtPhoneNumber = (MultiAutoCompleteTextView)
                        findViewById(R.id.txtPhoneNumber);

                //Send from person messages view
                if (txtPhoneNumber.getVisibility() == View.GONE) {
                    contact = contactMessages.getPhone();
                } else {
                    contact = txtPhoneNumber.getText().toString();
                }

                if (!TextUtils.isEmpty(contact)) {

                    examineSMSTextForTriggerWords(tiMessageBody.getText(true), CustomPopupButton.PopupButtonSide.LEFT);

                    //Extract the numbers to send
                    List<String> numbersToSend = extractNumbers(contact);

                    String messageTosend = tiMessageBody.getText(true);
                    for (int i = 0; i < numbersToSend.size(); i++) {
                        sendSMS(numbersToSend.get(i), messageTosend);
                    }
                    tiMessageBody.init();

                    if (getIntent().getBooleanExtra("isNewMessage", false)) {
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        isMessageSent = true;
                        new UpdateList().execute();
                    }
                } else {
                    Toast.makeText(PersonMessagesActivity.this, "Please enter a number or choose a contact",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void closeKeyboard(View closingView) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(closingView.getWindowToken(), 0);
    }

    private void addToSentContenProvider(String phoneNumber, String message) {
        // store the sent sms in the sent folder
        ContentValues values = new ContentValues();
        values.put("address", phoneNumber);
        values.put("body", message);
        context.getContentResolver().insert(Uri.parse("content://sms/sent"), values);
    }


    private void onIconsClick() {
        ImageView btnIcons = (ImageView) findViewById(R.id.btnIcons);
        btnIcons.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(PersonMessagesActivity.this, CustomIconList.class);
                startActivityForResult(in, GET_ICON_FROM_LIST);
            }
        });
    }

    private void onDeleteConversationClick() {
        ImageView ivDeleteConversations = (ImageView) findViewById(R.id.ivDeleteConversations);
        ivDeleteConversations.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showVerifyDeleteConversation();
            }
        });
    }

    private void showVerifyDeleteConversation() {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("This conversation will be deleted").setTitle("Delete?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                deleteConversationByThreadId();

                setResult(RESULT_OK);

                finish();

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteConversationByThreadId() {
        getContentResolver().delete(Uri.parse("content://sms/conversations/" + contactMessages.getThread_id()),
                null, null);
        Toast.makeText(getBaseContext(), "Conversation deleted...", Toast.LENGTH_SHORT).show();
    }

    private void showAlertCantSendMessage() {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Your message has no valid recipients").setTitle("Can't send message");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private List<String> extractNumbers(String input) throws InvalidParameterException {
        List<String> numbers = new ArrayList<String>();

        String[] contacts = input.split(ContactsMap.SEPERATOR);

        for (String contact : contacts) {
            int startIndex = contact.lastIndexOf("<");

            if (startIndex == -1 && contact.trim().length() > 0) {
                if (contact.replace("-", "").replace("+", "").matches("[0-9]+"))
                    numbers.add(contact.trim().replace("-", ""));
                else
                    throw new InvalidParameterException();
            } else {
                int endIndex = contact.lastIndexOf(">");

                if (endIndex == -1 && contact.trim().length() == 0)
                    throw new InvalidParameterException();

                else
                    numbers.add(contact.substring(startIndex + 1, endIndex).trim().replace("-", ""));
            }
        }

        return numbers;

    }


    /**
     *
     */
    private void onBackClick() {
        //BACK
        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //refresh
                if (isMessageSent)
                    setResult(RESULT_OK);
                else
                    setResult(RESULT_CANCELED);

                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

        //refresh
        if (isMessageSent)
            setResult(RESULT_OK);
        else
            setResult(RESULT_CANCELED);

        super.onBackPressed();
        finish();
    }

    @Override
    public void onStop() {
        //refresh
        if (isMessageSent)
            setResult(RESULT_OK);
        else
            setResult(RESULT_CANCELED);

        super.onStop();
    }

    //Open Contacts
    public void readcontacts() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        startActivityForResult(intent, PICK_CONTACT);
    }

    /*
     * Get contacts result
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_CONTACT: {
                if (resultCode == RESULT_OK) {
                    //CONTACT Selector
                    MultiAutoCompleteTextView txtPhoneNo = (MultiAutoCompleteTextView) findViewById(R.id
                            .txtPhoneNumber);

                    List<String> allNumbers = new ArrayList<String>();
                    Cursor cursor = null;
                    int phoneIdx = 0;
                    String phoneNumber = "";

                    Uri result = data.getData();
                    String id = result.getLastPathSegment();

                    cursor = getContentResolver().query(Phone.CONTENT_URI, null, Phone.CONTACT_ID + "=?",
                            new String[]{id}, null);
                    phoneIdx = cursor.getColumnIndex(Phone.DATA);

                    if (cursor.moveToFirst()) {
                        while (cursor.isAfterLast() == false) {
                            phoneNumber = cursor.getString(phoneIdx);
                            allNumbers.add(phoneNumber);
                            cursor.moveToNext();
                        }

                        txtPhoneNo.setText(phoneNumber);
                    } else {
                        txtPhoneNo.setText("No results!");
                    }

                    if (cursor != null) {
                        cursor.close();
                    }
                }
                break;
            }
            case GET_ICON_FROM_LIST: {
                if (resultCode == RESULT_OK) {
                    SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", this.MODE_WORLD_READABLE);
                    String selectedkkey = myPrefs.getString("key", "");

                    if (!selectedkkey.equals("")) {
                        SpannedString msg = new SpannedString(selectedkkey + " ");
                        tiMessageBody.appendText(msg);
                    }
                }
                break;
            }
        }
    }

    //sends an SMS message to another device
    private void sendSMS(final String phoneNumber, final String message) {

        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);

        addToSentContenProvider(phoneNumber, message);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 0, "Settings");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Intent intent = new Intent(PersonMessagesActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        menu.removeGroup(1);            //remove "add to dictionary" and etc.
        super.onCreateContextMenu(menu, v, menuInfo);
    }

}
