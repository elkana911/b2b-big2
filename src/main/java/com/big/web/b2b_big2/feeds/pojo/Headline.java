package com.big.web.b2b_big2.feeds.pojo;

public class Headline {
	private String newsid;
	private String title;
	private String remarks;
	private String imageUrl;
	public String getNewsid() {
		return newsid;
	}
	public void setNewsid(String newsid) {
		this.newsid = newsid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	@Override
	public String toString() {
		return "Headline [newsid=" + newsid + ", title=" + title + ", remarks=" + remarks + ", imageUrl=" + imageUrl
				+ "]";
	}

	
}
