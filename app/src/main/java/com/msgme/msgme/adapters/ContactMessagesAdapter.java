package com.msgme.msgme.adapters;

import java.io.InputStream;
import java.util.List;

import com.msgme.msgme.R;
import com.msgme.msgme.TextImage;
import com.msgme.msgme.vo.ContactMessages;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class ContactMessagesAdapter implements ListAdapter, View.OnClickListener 
{
	Context 				context;

	List<ContactMessages>	contactMessages;

	// View adapterView;
	public ContactMessagesAdapter(Context context, List<ContactMessages> contactMessages) {
		super();
		this.context = context;
		this.contactMessages = contactMessages;
	}

	@Override
	public int getCount() {
		
		return ((this.contactMessages==null)? 0:contactMessages.size());
	}

	@Override
	public Object getItem(int position) {
		return contactMessages.get(position);
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

		try {
			if(convertView==null)
			{
				LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				root = inflater.inflate(R.layout.contact_message_item_content, null);
			}
			else
				root=convertView;

			ContactMessages data = contactMessages.get(position);

			TextView nameTv=(TextView)root.findViewById(R.id.tvUserName);

			if (data.getName() == null)
				nameTv.setText(data.getPhone());
			else
				nameTv.setText(data.getName());

			TextView wishTv=(TextView)root.findViewById(R.id.tvWish);
			wishTv.setText(TextImage.toText(data.getMessages().get(0).getText(), false) );

			ImageView ivThumb = (ImageView)root.findViewById(R.id.ivThumb);

			Drawable thumbDrawable = loadContactPhoto(context.getContentResolver(), data.getContact_id());
			
			if (thumbDrawable == null)
				ivThumb.setImageResource(context.getResources().getIdentifier("com.msgme.msgme:drawable/user_profile",null,null));
			else
				ivThumb.setImageDrawable(thumbDrawable);

			ImageView ivBubble = (ImageView)root.findViewById(R.id.ivBubble);

			if ( (position % 2) == 0 ) 
			{
				root.setBackgroundColor(context.getResources().getColor(R.color.grid_alternate_1));
				ivBubble.setImageResource(context.getResources().getIdentifier("com.msgme.msgme:drawable/profile_pic_alt1",null,null));
			}
			else
			{
				root.setBackgroundColor(context.getResources().getColor(R.color.grid_alternate_2));
				ivBubble.setImageResource(context.getResources().getIdentifier("com.msgme.msgme:drawable/profile_pic_alt2",null,null));
			}


			return root;
		} catch (Exception e) {
			//Some error
			return root;
		}
	}


	public static Drawable loadContactPhoto(ContentResolver cr, long id) 
	{
		if (id == -1)
			return null;
		
		Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);

		try
		{
			InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);

			if (input == null)
				return null;

			Drawable d = Drawable.createFromStream(input, "src name");
			return d;

		}catch (Exception e) {
			System.out.println("Exc="+e);
			return null;
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
		return contactMessages.size() == 0;
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
