package com.msgme.msgme.adapters;

import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import com.msgme.msgme.R;
import com.msgme.msgme.Settings;
import com.msgme.msgme.TextImage;
import com.msgme.msgme.utils.DateUtils;
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

public class MessagesAdapter implements ListAdapter, View.OnClickListener 
{
	Context 				context;
	
	List<Message> 			messages;
	
	// View adapterView;
	public MessagesAdapter(Context context, List<Message> messages) {
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

		return 0;
	}

	@Override
	public int getItemViewType(int arg0) {

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
				//root = inflater.inflate(R.layout.person_message_item_content, null);
				root = inflater.inflate(R.layout.person_message_item_content, parent, false);
			}
			else
				root = convertView;
			
		
			Message data = messages.get(position);
			
			TextView textTv=(TextView)root.findViewById(R.id.tvbody);
			TextImage tiMessage = new TextImage(textTv, context);
			
			if ( (Settings.showIcons) || (Settings.showLogos) )
				textTv.setText(tiMessage.toVisualTextFast(data.getText()));
			else
				textTv.setText(TextImage.toText(data.getText(), false));
			
			TextView dateTv=(TextView)root.findViewById(R.id.tvDate);
			dateTv.setText(DateUtils.getMessageDate( data.getDate() ));
			
			
			//Set style for in/sent styles
			if (data.getIsSentByMe())
			{
				LinearLayout genralLayout=(LinearLayout)root.findViewById(R.id.llGeneral);
				genralLayout.setGravity(Gravity.RIGHT);
				
				LinearLayout backLayout=(LinearLayout)root.findViewById(R.id.llBack);
				backLayout.setBackgroundResource(context.getResources().getIdentifier("com.msgme.msgme:drawable/round_rect_green_shape",null,null));
				
				root.findViewById(R.id.llArrowOrange).setVisibility(View.GONE);
				root.findViewById(R.id.llArrowGreen).setVisibility(View.VISIBLE);
			}
			else
			{
				
				LinearLayout genralLayout=(LinearLayout)root.findViewById(R.id.llGeneral);
				genralLayout.setGravity(Gravity.LEFT);
								
				LinearLayout backLayout=(LinearLayout)root.findViewById(R.id.llBack);
				backLayout.setBackgroundResource(context.getResources().getIdentifier("com.msgme.msgme:drawable/round_rect_red_shape",null,null));
				
				root.findViewById(R.id.llArrowOrange).setVisibility(View.VISIBLE);
				root.findViewById(R.id.llArrowGreen).setVisibility(View.GONE);
			}
			
			
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

		return false;
	}

	@Override
	public boolean isEmpty() {
		return messages.size() == 0;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver arg0) {

		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver arg0) {

		
	}

	@Override
	public void onClick(View arg0) {

		
	}

	@Override
	public boolean areAllItemsEnabled() {

		return true;
	}

	@Override
	public boolean isEnabled(int arg0) {

		return true;
	}

}
