package com.msgme.msgme.vo;

import java.io.Serializable;

public class IconData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int 					index;

	public String 				name;
	
	public int 					sortGroup;
	
	public float					hiegth;
	public float					wiegth;
	
	public IconData (int index, String name, int group) {
		super();
		this.index = index;
		this.name = name;
		this.sortGroup = group;
		this.hiegth = 3;
		this.wiegth = 3;
	}

	public IconData (int index, String name, int group, float hiegth, float wiegth) {
		super();
		this.index = index;
		this.name = name;
		this.sortGroup = group;
		this.hiegth = hiegth;
		this.wiegth = wiegth;
	}
}
