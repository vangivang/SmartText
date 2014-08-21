package com.msgme.msgme;

import java.net.URLEncoder;

import com.msgme.msgme.storage.SharedPreferencesManager;
import com.msgme.msgme.storage.WordReferences;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity {
	
	private Context 					context 					= this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		onBackClick();

		onRequestIconEvents();

		checkToggleStatus();
		
		onLogoClick();
		
		onLinksClick();
	}

	@Override
	protected void onResume() {
		super.onResume();

		//Clear the word entered
		EditText etRequestIcon = (EditText)findViewById(R.id.etRequestIcon);
		if (etRequestIcon != null)
			etRequestIcon.setText("");
	}

	private void checkToggleStatus()
	{
		ToggleButton tglShowIconsButton = (ToggleButton) findViewById(R.id.tbShowIcons);
		ToggleButton tglShowLogosButton = (ToggleButton) findViewById(R.id.tbShowLogos);

		tglShowIconsButton.setChecked(Settings.showIcons);
		tglShowLogosButton.setChecked(Settings.showLogos);
	}

	public void onShowIconsClicked(View view) {
		Settings.showIcons = ((ToggleButton) view).isChecked();
	}

	public void onShowLogosClicked(View view) {
		Settings.showLogos = ((ToggleButton) view).isChecked();
	}

	@Override
	public void onBackPressed() {

		saveSettings();

		super.onBackPressed();
	}

	/**
	 * 
	 */
	private void onBackClick() {

		ImageView ivBack = (ImageView)findViewById(R.id.ivBack);
		ivBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) 
			{
				saveSettings();

				finish();
			}
		});
	}

	private void saveSettings()
	{
		//Save the settings in shared preferences
		try {
			SharedPreferences settings = getSharedPreferences(SharedPreferencesManager.SETTINGS, 0);
			SharedPreferences.Editor editor = settings.edit();

			editor.putBoolean(SharedPreferencesManager.SHOW_LOGOS, Settings.showLogos);
			editor.putBoolean(SharedPreferencesManager.SHOW_ICONS,Settings.showIcons);

			editor.commit();
		} catch (Exception e) {
			//Some Error
		}
	}

	private void onRequestIconEvents() {
		ImageView btnRequestIcon = (ImageView)findViewById(R.id.btnRequestIcon);

		btnRequestIcon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) 
			{
				EditText etRequestIcon = (EditText)findViewById(R.id.etRequestIcon);
				sendWordMailRequest(etRequestIcon.getText().toString());
			}
		});

		EditText etRequestIcon = (EditText)findViewById(R.id.etRequestIcon);

		etRequestIcon.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				ImageView btnRequestIcon = (ImageView)findViewById(R.id.btnRequestIcon);

				if (s.length() == 0)
					btnRequestIcon.setEnabled(false);
				else
					btnRequestIcon.setEnabled(true);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void afterTextChanged(Editable s) {}
		});

	}
	
	
	private void onLogoClick()
	{
		ImageView ivLogo = (ImageView)findViewById(R.id.ivLogo);

		ivLogo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) 
			{				
				try
				{
					Intent intent = new Intent(Intent.ACTION_VIEW);
					String url = "http://www.smartext.me/";
					intent.setData(Uri.parse(url));
					context.startActivity(intent);
				}
				catch (Exception ex  )
				{
					Toast.makeText(context, "can't open samrtext web site", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void onLinksClick()
	{
		ImageView ivFacebook = (ImageView)findViewById(R.id.ivFacebook);

		ivFacebook.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) 
			{				
				try
				{
					Intent intent = new Intent(Intent.ACTION_VIEW);
					String url = "https://www.facebook.com/pages/Smartext/245648282237557";
					intent.setData(Uri.parse(url));
					context.startActivity(intent);
				}
				catch (Exception ex  )
				{
					Toast.makeText(context, "can't open samrtext web site", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		ImageView ivTwitter = (ImageView)findViewById(R.id.ivTwitter);

		ivTwitter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) 
			{				
				try
				{
					Intent intent = new Intent(Intent.ACTION_VIEW);
					String url = "https://twitter.com/SmartextSMS";
					intent.setData(Uri.parse(url));
					context.startActivity(intent);
				}
				catch (Exception ex  )
				{
					Toast.makeText(context, "can't open samrtext web site", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		ImageView ivGplus = (ImageView)findViewById(R.id.ivGplus);

		ivGplus.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) 
			{				
				try
				{
					Intent intent = new Intent(Intent.ACTION_VIEW);
					String url = "https://plus.google.com/115020635697813532381/";
					intent.setData(Uri.parse(url));
					context.startActivity(intent);
				}
				catch (Exception ex  )
				{
					Toast.makeText(context, "can't open samrtext web site", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	

	private void storeKeyWord(String keyword, String youtube, String google, String waze)
	{
		try {
			SharedPreferences settings = getSharedPreferences(SharedPreferencesManager.PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();

			if (youtube != null)
				editor.putString(keyword + SharedPreferencesManager.YOUTUBE_POSTFIX, youtube);

			if (google != null)
				editor.putString(keyword + SharedPreferencesManager.GOOGLE_POSTFIX, google);

			if (waze != null)
				editor.putString(keyword + SharedPreferencesManager.WAZE_POSTFIX, waze);

			editor.commit();
		} catch (Exception e) {
			//Some Error
		}

	}

	private WordReferences readKeyWords(String keyword)
	{
		try 
		{
			SharedPreferences settings = getSharedPreferences(SharedPreferencesManager.PREFS_NAME, 0);

			WordReferences wordReferences = new WordReferences(keyword, null, null, null);
			wordReferences.setYoutube(settings.getString(SharedPreferencesManager.YOUTUBE_POSTFIX, ""));
			wordReferences.setGoogle(settings.getString(SharedPreferencesManager.GOOGLE_POSTFIX, ""));
			wordReferences.setWaze(settings.getString(keyword + SharedPreferencesManager.WAZE_POSTFIX, ""));

			return wordReferences;

		} catch (Exception e) 
		{
			return null;
		}

	}

	private void sendWordMailRequest(String newIcon)
	{

		Intent sendMailIntent = new Intent(android.content.Intent.ACTION_SEND);
		sendMailIntent.setType("message/rfc822");
		sendMailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"info@smartext.me"});
		sendMailIntent.putExtra(Intent.EXTRA_SUBJECT, "Please add \"" + newIcon + "\" icon");
		sendMailIntent.putExtra(Intent.EXTRA_TEXT   , "Thanks!");
		try {
			startActivity(Intent.createChooser(sendMailIntent, "Send mail..."));

		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(SettingsActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}

}
