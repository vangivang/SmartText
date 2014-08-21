package com.msgme.msgme.vo;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

public class LogoData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//must
	public int image;
	public String waseUrl;

	public float hiegth;
	public float wiegth;

	public LogoData(int imageRes, String waseAddress)
	{
		super();
		this.image = imageRes;
		this.waseUrl = waseAddress;
		this.hiegth = 3;
		this.wiegth = 3;
	}
	
	public LogoData(int imageRes, String waseAddress, float hiegth, float wiegth)
	{
		super();
		this.image = imageRes;
		this.waseUrl = waseAddress;
		this.hiegth = hiegth;
		this.wiegth = wiegth;
	}
}
