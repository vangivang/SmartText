package com.msgme.msgme.vo;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
	
	private Boolean				isSentByMe;
	
	private Date 				date;

    private String 				text;
    
    public Boolean getIsSentByMe() {
		return isSentByMe;
	}

	public void setIsSentByMe(Boolean isSentByMe) {
		this.isSentByMe = isSentByMe;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Message(Date date, String text, Boolean isSentByMe) {
		super();
		this.isSentByMe = isSentByMe;
		this.date = date;
		this.text = text;
	}
}
