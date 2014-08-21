package com.msgme.msgme.storage;

import java.util.Map;

public class WordReferences {
	
	private String 				keyword 	= null;
	
	private String				youtube		= null;
	private String				google		= null;
	private String				waze		= null;
	
	public WordReferences(String keyword, String youtube, String google,
			String waze) {
		super();
		this.keyword = keyword;
		this.youtube = youtube;
		this.google = google;
		this.waze = waze;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getYoutube() {
		return youtube;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}

	public String getGoogle() {
		return google;
	}

	public void setGoogle(String google) {
		this.google = google;
	}

	public String getWaze() {
		return waze;
	}

	public void setWaze(String waze) {
		this.waze = waze;
	}
}
