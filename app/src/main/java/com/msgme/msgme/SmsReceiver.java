package com.msgme.msgme;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.msgme.msgme.data.LogoDataProvider;

public class SmsReceiver extends BroadcastReceiver {

    public Context context;

    public SmsMessage[] msgs = null;
    private StringBuilder wholeMsg = new StringBuilder(170);

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            abortBroadcast();

            this.context = context;

            getAndSaveMessages(context, intent);

            createNotification();

            String from = getContactNameFromNumber(msgs[0].getOriginatingAddress());
            sendBroadCast(from, wholeMsg.toString());

        } catch (Exception e) {
            //Some error
        }
    }

    private void sendBroadCast(String sender, String body) {
        Intent in = new Intent("com.msgme.msgme.message_recieved").
                putExtra("phoneNumber", sender).
                putExtra("message", body);

        context.sendBroadcast(in);
    }


    private void getAndSaveMessages(Context context, Intent intent) {
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                wholeMsg.append(msgs[i].getMessageBody().toString());
            }

            //write the SMSs to the DB so they will be saved even after AbortBrodcast
            ContentValues values = new ContentValues();
            values.put("address", msgs[0].getOriginatingAddress());//sender name
            values.put("body", wholeMsg.toString());
            context.getContentResolver().insert(Uri.parse("content://sms/inbox"), values);

        }
    }


    private void showToast(Context context, Intent intent) {
        String str = null;

        for (int i = 0; i < msgs.length; i++) {
            str += "SMS from " + msgs[i].getOriginatingAddress();
            str += " :";
            str += msgs[i].getMessageBody().toString();
            str += "\n";
        }
        //---display the new SMS message---
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }


    public void createNotification() {
        String phone = msgs[0].getOriginatingAddress();
        String from = getContactNameFromNumber(phone);
        String message = msgs[0].getMessageBody().toString();

        String uniqeInd = null;
        int uniqePhone;
        if (phone.length() > 9)
            uniqeInd = phone.substring(phone.length() - 9, phone.length());
        else
            uniqeInd = phone;

        {
            int i = 0;
            for (; i < uniqeInd.length(); i++) {
                if (Character.isDigit(uniqeInd.charAt(i)))
                    break;
            }
            if (i == uniqeInd.length())
                uniqeInd = null;
            else
                uniqeInd = uniqeInd.substring(i, uniqeInd.length());
        }

        if (uniqeInd != null)
            uniqePhone = Integer.parseInt(uniqeInd);
        else
            uniqePhone = phone.hashCode();

        NotificationManager notifier = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Get the icon for the notification
        int icon = LogoDataProvider.appLogo[0];
        Notification notification = new Notification(icon, from + ":" + message, System.currentTimeMillis());

        //Setup the Intent to open this Activity when clicked
        Intent toLaunch = new Intent(context, MainActivity.class);
        toLaunch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        toLaunch.putExtra("fromSmsReciever", true);
        toLaunch.putExtra("smsRecieverPhone", phone);

        PendingIntent contentIntent = PendingIntent.getActivity(context, uniqePhone, toLaunch,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        //Set the Notification Info
        notification.setLatestEventInfo(context, from, message, contentIntent);

        //Setting Notification Flags
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        //No sound alert
        //notification.defaults |= Notification.FLAG_ONLY_ALERT_ONCE;
        //Sound Alert
        notification.defaults |= (Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification
                .DEFAULT_VIBRATE);

        //Send the notification
        notifier.notify(uniqePhone, notification);
    }

    public String getContactNameFromNumber(String number) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

        Cursor c = context.getContentResolver().query(contactUri, new String[]{PhoneLookup.DISPLAY_NAME, PhoneLookup._ID}, null, null, null);

        String name = null;

        if (c.moveToFirst()) {
            name = c.getString(c.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        return name == null ? number : name;
    }

}


