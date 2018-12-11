package com.capgemini.imagespoc;

import java.util.List;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


public class Image implements Serializable{
	
	private static final long serialVersionUID = -7788619177798333712L;

	private byte[] imageFile;
	private int id;

	//String title;

	public byte[] getImageFile() {
		return imageFile;
	}

	public void setImageFile(byte[] imageFile) {
		this.imageFile = imageFile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}*/


}
	