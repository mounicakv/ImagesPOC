/**
 * 
 */
package com.capgemini.imagespoc.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Image {
	int id;
	String url;
	String large_url;
	int source_id;
	String copyright;
	String site;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLarge_url() {
		return large_url;
	}
	public void setLarge_url(String large_url) {
		this.large_url = large_url;
	}
	public int getSource_id() {
		return source_id;
	}
	public void setSource_id(int source_id) {
		this.source_id = source_id;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}

}
