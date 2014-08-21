package com.msgme.msgme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.msgme.msgme.adapters.ContactMessagesAdapter;
import com.msgme.msgme.storage.SharedPreferencesManager;
import com.msgme.msgme.vo.ContactMessages;
import com.msgme.msgme.vo.Message;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	//Holds the conversations
	public ContactMessagesAdapter 	adapter 		= null;

	public ListView 				lvMessagesList = null;

	public List<ContactMessages> 	contactMessages = null;

	//----------------------------------------------------------------------
	//SMS - reciever
	//----------------------------------------------------------------------
	private BroadcastReceiver 			sendBroadcastReceiver;
	private BroadcastReceiver 			deliveryBroadcastReceiver;

	private BroadcastReceiver 			mIntentReceiver;

	private String 						SENT = "SMS_SENT";
	private String 						DELIVERED = "SMS_DELIVERED";


	private static final int WHAT = 1;
	private static final int TIME_TO_WAIT = 5000;

	public  Boolean						isRefreshIsNeeded			    = true;
	private String 						lastConversationMessage			= null;
	
	private Context 					context 					= this;

	private static int 					mutex			= 0;
	Handler regularHandler = new Handler(new Handler.Callback() {


		@Override
		public boolean handleMessage(android.os.Message msg) {

			Log.i("HANDLER_AWAKE","HANDLER_AWAKE");
			//check if refresh is needed to the main activity
			if (isRefreshIsNeeded)
			{
				//Refresh
				try {
					if (lvMessagesList == null)
						lvMessagesList = (ListView)findViewById(R.id.messagesList);
					
					lvMessagesList.invalidateViews();
					
					new UpdateList().execute();

					isRefreshIsNeeded = false;
					
					Log.i("HANDLER_AWAKE","REFRESHING!!!");

				} catch (Exception e) {

				}
			}
			else
				Log.i("HANDLER_AWAKE","NOT_REFRESHING!!!");

			regularHandler.sendEmptyMessageDelayed(msg.what, TIME_TO_WAIT);

			return true;
		}
	});

	private class UpdateList extends AsyncTask<String, Integer, String> {

		@Override
		protected String  doInBackground(String... params) {
					try {
						if (mutex == 0)
							contactMessages = getConversationsSamsungS3();
						++mutex;
						
					} catch (Exception e) {
						//do nothing
					}
			return null;
		}

		
		protected void onPostExecute(String result) {
			--mutex;
			if (mutex == 0)
			{
				super.onPostExecute(result);
				showConversations();
			}
		}
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {

			registerRecievers();

			onNewSmsClick();

			onOptionsClick();

			readSettings();

			if (adapter == null)
			{
				contactMessages = getConversationsSamsungS3();
			}
			
			Intent intent = getIntent();
			openConversation(intent);


		} catch (Exception e) {

		}
	}



	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		if ( intent.getBooleanExtra("fromSmsReciever", true) )
		{
			openConversation(intent);
		}
		
	}

	protected void openConversation(Intent intent)
	{
		String phone = intent.getStringExtra("smsRecieverPhone");

		if ( (phone == null) ||		//not from notifier
				( !intent.getBooleanExtra("fromSmsReciever", false)) )	//if new intent the default is false, if from receiver search for old conversation
			showConversations();
		else
		{
			ContactMessages sender = null;
			int i;
			for (i=0; i < contactMessages.size(); i++)
			{
				sender = contactMessages.get(i);
				if ((sender == null) || (sender.getPhone() == null)){
					sender = null;
					continue;
				}
				if (sender.getPhone().equals(phone))
					break;
			}
			
			if (i == contactMessages.size() || sender == null)
			{
				showConversations();
				return;
			}
			
			Intent msgIntent = new Intent(MainActivity.this,PersonMessagesActivity.class);
			msgIntent.putExtra("contactMessages", sender);
			msgIntent.putExtra("isNewMessage", false);
	
			startActivityForResult(msgIntent,1);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		regularHandler.sendEmptyMessageDelayed(WHAT, TIME_TO_WAIT);

		registerRecievers();

		mIntentReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) 
			{
				isRefreshIsNeeded = true;
			}
		};

		IntentFilter intentFilter = new IntentFilter("com.msgme.msgme.message_recieved");
		this.registerReceiver(mIntentReceiver, intentFilter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		regularHandler.removeMessages(WHAT);

		unregisterReceiver(sendBroadcastReceiver);
		unregisterReceiver(deliveryBroadcastReceiver);
	}


	private void registerRecievers()
	{
		sendBroadcastReceiver = new BroadcastReceiver()
		{

			public void onReceive(Context arg0, Intent arg1)
			{
				switch (getResultCode())
				{
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS Sent", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "Generic failure", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "No service", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(), "Null PDU", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(), "Radio off", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};

		deliveryBroadcastReceiver = new BroadcastReceiver()
		{
			public void onReceive(Context arg0, Intent arg1)
			{
				switch (getResultCode())
				{
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS Delivered", Toast.LENGTH_SHORT).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "SMS not delivered", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};
		registerReceiver(deliveryBroadcastReceiver, new IntentFilter(DELIVERED));
		registerReceiver(sendBroadcastReceiver , new IntentFilter(SENT));   

	}

	private void readSettings()
	{
		//Read from shared preferences
		SharedPreferences settings = getSharedPreferences(SharedPreferencesManager.SETTINGS, 0);

		if (settings != null)
		{
			Settings.showIcons = settings.getBoolean(SharedPreferencesManager.SHOW_ICONS,true);
			Settings.showLogos = settings.getBoolean(SharedPreferencesManager.SHOW_LOGOS,true);

			Settings.isEnglish = settings.getBoolean(SharedPreferencesManager.IS_ENGLISH, true);
		}
		else
		{
			Settings.showIcons = true;
			Settings.showLogos = true;

			Settings.isEnglish = true;
		}
	}

	/**
	 * @param contactMessages
	 */
	private void showConversations()
	{
		try {
			lvMessagesList = (ListView)findViewById(R.id.messagesList);
			adapter = new ContactMessagesAdapter(this, contactMessages);
			lvMessagesList.setAdapter(adapter);
			
			lvMessagesList.setOnItemClickListener(new ListView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					Intent intent = new Intent(MainActivity.this,PersonMessagesActivity.class);
					intent.putExtra("contactMessages", (ContactMessages)parent.getItemAtPosition(position));
					intent.putExtra("isNewMessage", false);

					startActivityForResult(intent,1);
		 
				}
			});
		} catch (Exception e) 
		{
			//Some error
		}
	}

	@Override  
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
			try {
				contactMessages = getConversationsSamsungS3();
				showConversations();
				
				lvMessagesList.invalidateViews();
			} catch (Exception e) {
			}
	}

	/**
	 * on new sms click logic
	 */
	private void onNewSmsClick() throws Exception 
	{
		try 
		{
			ImageView ivNewSms = (ImageView)findViewById(R.id.ivNewSms);
			ivNewSms.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this,PersonMessagesActivity.class);
					intent.putExtra("isNewMessage", true);

					startActivityForResult(intent,1);
				}
			});
		} catch (Exception e) 
		{
			//Some error
		}

	}

	private void onOptionsClick() 
	{
		try 
		{
			ImageView ivOptions = (ImageView)findViewById(R.id.ivOptions);
			ivOptions.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
					startActivity(intent);
				}
			});
		} catch (Exception e) 
		{
			//Some error
		}
	}



	/**
	 * @return list of mock conversations
	 */
	private List<ContactMessages> getMockConversation() {
		List<Message> messages1 = new ArrayList<Message>() {{
			add(new Message(new Date(), "What's up?  Did you called me?",true));
		}};

		List<Message> messages2 = new ArrayList<Message>() {{
			add(new Message(new Date(), "Great graphics.... very good progress",false));
		}};								

		List<ContactMessages> contactMessages= new ArrayList<ContactMessages>();
		contactMessages.add(new ContactMessages("1",1L, "Tomer Shamir", "0542432213", null, messages1));
		contactMessages.add(new ContactMessages("2",2L, "Yaron Azulay", "0523394453", null, messages2));

		return contactMessages;
	}



	//due to Galaxy S3 differences 
	private List<ContactMessages> getConversationsSamsungS3() throws Exception  
	{
		try 
		{
			Uri mSmsinboxQueryUri = Uri.parse("content://mms-sms/conversations?simple=true");

			Cursor cursor1 = getContentResolver().query(mSmsinboxQueryUri, new String[] {"*"}, null, null, "date desc");

			contactMessages =  new ArrayList<ContactMessages>(); 

			if (cursor1.getCount() > 0) 
			{
				String count = Integer.toString(cursor1.getCount());

				ContactMessages contactMessage = null;

				Boolean isFirst = true;

				while (cursor1.moveToNext())
				{
					//Details
					String phone = getContactNameByThreadId(cursor1.getString(cursor1.getColumnIndex("_id")));
					String date = cursor1.getString(cursor1.getColumnIndex("date"));
					String msg = cursor1.getString(cursor1.getColumnIndex("snippet"));
					String threadId = cursor1.getString(cursor1.getColumnIndex("_id"));

					List<Message> messages = new ArrayList<Message>();
					messages.add(0, new Message(new Date(Long.parseLong(date)),msg,false));

					if (isFirst)
					{
						lastConversationMessage = msg;
						isFirst = false;
					}

					contactMessage = new ContactMessages(threadId, -1L, null , phone, null, messages);

					//Get id, name and thumbnail
					getContactDataFromNumber(phone, getApplicationContext(), contactMessage);

					contactMessages.add(contactMessage);
				}

				cursor1.close();
			}
			return contactMessages;
		} catch (Exception e) 
		{
			//Some error
			return null;
		}

	}

	private String getContactNameByThreadId(String threadId)
	{
		ArrayList<String> phones = new ArrayList<String>();

		Uri THREAD_ID_CONTENT_URI = Uri.parse("content://sms/");

		Cursor cursor = getContentResolver().query(THREAD_ID_CONTENT_URI, new String[]{"address"},"thread_id=?) group by (address", new String[]{threadId},null);

		while (cursor.moveToNext()) 
		{
			phones.add(cursor.getString(cursor.getColumnIndex("address")));
		} 

		cursor.close();

		if (phones.size() > 0)
			return(phones.get(0));
		else
			return "Unknown Contact";
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		//Add the setting option in the button
		menu.add(1,1,0,"Settings");
		menu.add(2,2,0,"Share");
		menu.add(3,3,0,"Invite Friends");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle item selection
		switch (item.getItemId()) {
		case 1:
			Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
			startActivity(intent);
			return true;
		case 2:
				
			Boolean isFacebookInstalled = false;
			
			try {
				Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(Intent.EXTRA_TEXT, "http://www.smartext.me");
				PackageManager pm = context.getPackageManager();
				List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
				
				for (final ResolveInfo app : activityList) {
				    if ((app.activityInfo.name).contains("facebook")) {
				    	isFacebookInstalled = true;
				        final ActivityInfo activity = app.activityInfo;
				        final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
				        shareIntent.setComponent(name);
				        context.startActivity(shareIntent);
				        break;
				   }
				}
			} catch (Exception e) {
				Toast.makeText(context, "can't complete action", Toast.LENGTH_SHORT).show();
			}
			
			
			if (!isFacebookInstalled)
			{
				try
				{
					Intent installIntent = new Intent(Intent.ACTION_VIEW);
					String url = "https://play.google.com/store/apps/details?id=com.facebook.katana";
					installIntent.setData(Uri.parse(url));
					context.startActivity(installIntent);
				}
				catch (Exception ex  )
				{
					Toast.makeText(context, "can't open facebook web site", Toast.LENGTH_SHORT).show();
				}
			}
			return true;
		case 3:

			Intent inviteIntent = new Intent(MainActivity.this,PersonMessagesActivity.class);
			inviteIntent.putExtra("isNewMessage", true);
			inviteIntent.putExtra("isInviteFriend", true);
			
			startActivityForResult(inviteIntent,1);
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void getContactDataFromNumber(String number, Context ctx, ContactMessages contactMessages) 
	{
		Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

		Cursor c = ctx.getContentResolver().query(contactUri, new String[]{PhoneLookup.DISPLAY_NAME,PhoneLookup._ID}, null, null,null);

		if (c.moveToFirst()) {
			contactMessages.setName(c.getString(c.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)));
			contactMessages.setContact_id(c.getLong(c.getColumnIndex(ContactsContract.PhoneLookup._ID)));
		}

		c.close();
	}
}
