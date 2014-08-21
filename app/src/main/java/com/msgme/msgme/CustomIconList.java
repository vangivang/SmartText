package com.msgme.msgme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.msgme.msgme.adapters.IconAdapter;
import com.msgme.msgme.data.IconsDataProvider;
import com.msgme.msgme.storage.SharedPreferencesManager;
import com.msgme.msgme.vo.IconData;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CustomIconList extends Activity implements OnItemClickListener{

	private List<IconData> _iconList;
	private ListView _lvIconList = null;
	private IconAdapter _adapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_icon);
		
		_lvIconList = (ListView)findViewById(R.id.lvIconSelect);
		
		_iconList = new ArrayList<IconData>();
		
		loadList();

		ToggleButton tbIsEnglish = (ToggleButton)findViewById(R.id.tbSwitch);
		tbIsEnglish.setChecked(Settings.isEnglish);

		_lvIconList.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		saveSettings();
		
        SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();

        prefsEditor.putString("key", _iconList.get(arg2).name);
        prefsEditor.commit();
        setResult(RESULT_OK);
        finish();
	}
	
	private void loadList()
	{
		_iconList.clear();
		
		Object[] iconsKeys;
		if (Settings.isEnglish)
		{
			iconsKeys = IconsDataProvider.htIcons.keySet().toArray();
			for (int i=0; i<iconsKeys.length; i++)
			{
				IconData tmp = IconsDataProvider.htIcons.get(iconsKeys[i]);
				if (tmp.sortGroup != -100)
					_iconList.add(tmp);
			}
		}
		else
		{
			iconsKeys = IconsDataProvider.htIconsHeb.keySet().toArray();
			for (int i=0; i<iconsKeys.length; i++)
			{
				IconData tmp = IconsDataProvider.htIconsHeb.get(iconsKeys[i]);
				if (tmp.sortGroup != -100)
					_iconList.add(tmp);
			}
		}

		Collections.sort(_iconList, new Comparator<IconData>() {
			@Override
			public int compare(IconData lhs, IconData rhs) {
				return lhs.name.compareToIgnoreCase(rhs.name);
			}
		});
		
		_adapter = new IconAdapter(this, _iconList);
		_lvIconList.setAdapter(_adapter);
		
	}
	
	public void onSwitchLanguage(View view) {
		Settings.isEnglish = ((ToggleButton) view).isChecked();
		
		loadList();
	}
	
	private void saveSettings()
	{
		try {
			SharedPreferences settings = getSharedPreferences(SharedPreferencesManager.SETTINGS, 0);
			SharedPreferences.Editor editor = settings.edit();

			editor.putBoolean(SharedPreferencesManager.IS_ENGLISH, Settings.isEnglish);

			editor.commit();
		} catch (Exception e) {
			//Some Error
		}
	}
	
	@Override
	public void onBackPressed() {

		saveSettings();

		super.onBackPressed();
	}
}
