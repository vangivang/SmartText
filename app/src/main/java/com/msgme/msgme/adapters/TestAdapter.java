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
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

public class TestAdapter implements ListAdapter, View.OnClickListener 
{
Context 				context;
	
	List<Message> 			messages;
	
	// View adapterView;
	public TestAdapter(Context context, List<Message> messages) {
		super();
		this.context = context;
		this.messages = messages;
	}

	@Override
	public int getCount() {
		return messages.size();
	}

	@Override
	public Object getItem(int position) {
		return messages.get(position);
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
				root = inflater.inflate(R.layout.test_contnet, parent, false);
			}
			else
				root = convertView;
					
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
		return messages.size() == 0;
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
