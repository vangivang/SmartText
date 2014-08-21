package com.msgme.msgme.vo;

import java.util.HashMap;

public class ContactsMap extends HashMap<String, String>
{
	public static String SEPERATOR = ",";
	
	public String toString() 
	{ 
		return "" + this.get("Name")  + " <" + this.get("Phone") + ">" + SEPERATOR; 
	}
}
