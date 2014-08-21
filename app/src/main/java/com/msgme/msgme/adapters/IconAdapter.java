package com.msgme.msgme.adapters;

import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import com.msgme.msgme.R;
import com.msgme.msgme.Settings;
import com.msgme.msgme.TextImage;
import com.msgme.msgme.utils.DateUtils;
import com.msgme.msgme.vo.IconData;
import com.msgme.msgme.vo.Message;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class IconAdapter implements ListAdapter, View.OnClickListener 
{
	private Context 				context;

	private List<IconData> 			icons;

	// View adapterView;
	public IconAdapter(Context context, List<IconData> icons) {
		super();
		this.context = context;
		this.icons = icons;
	}

	@Override
	public int getCount() {
		return icons.size();
	}

	@Override
	public Object getItem(int position) {
		return icons.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getItemViewType(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View root = null;

		try 
		{
			if(convertView == null)
			{
				LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				if (Settings.isEnglish)
				{
					root = inflater.inflate(R.layout.icon_set_layout, null);
				}
				else
				{
					root = inflater.inflate(R.layout.icon_set_heb_layout, null);
				}
			}
			else
				root = convertView;

			IconData iconData = icons.get(position);

			//Name
			TextView nameTv=(TextView)root.findViewById(R.id.tvIconName);
			nameTv.setText(Character.toUpperCase(iconData.name.charAt(0)) + iconData.name.substring(1) );

			//Icon
			ImageView ivIconImage=(ImageView)root.findViewById(R.id.ivIconImage);
			ivIconImage.setImageResource(icons.get(position).index);
			
			return root;

		} catch (Exception e) 
		{
			return root;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		return icons.size() == 0;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled(int arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
