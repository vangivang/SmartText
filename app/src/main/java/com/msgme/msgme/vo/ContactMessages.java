package com.msgme.msgme.vo;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.graphics.Bitmap;

public class ContactMessages implements Serializable {

    //thread id
    private String thread_id;

    //contact id
    private long contact_id;

    //contact name
    private String name;

    //contact phone
    private String phone;

    //contact thumbnail
    private Bitmap thumbnail;

    //thread messages
    private List<Message> messages;


    public ContactMessages(String thread_id, long contact_id, String name, String phone, Bitmap thumbnail,
                           List<Message> messages) {
        super();
        this.thread_id = thread_id;
        this.contact_id = contact_id;
        this.name = name;
        this.phone = phone;
        if (messages != null)
            this.messages = messages;
        else {
            if (this.messages != null)
                this.messages.clear();
            else
                this.messages = new ArrayList<Message>();
        }
        this.thumbnail = thumbnail;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public long getContact_id() {
        return contact_id;
    }

    public void setContact_id(long contact_id) {
        this.contact_id = contact_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ContactMessages clone() {
        ContactMessages newContactMessage = new ContactMessages(thread_id, contact_id, name, phone, thumbnail, null);
        return newContactMessage;
    }

    public void AddNewMsgs(Cursor c) {

        int dIndex = c.getColumnIndex("date");
        int bIndex = c.getColumnIndex("body");
        int tIndex = c.getColumnIndex("type");

        messages.remove(0);
        while (c.moveToNext()) {
            messages.add(0, new Message(new Date(c.getLong(dIndex)),
                    c.getString(bIndex),
                    c.getString(tIndex).equals("1")));
        }
    }

}
