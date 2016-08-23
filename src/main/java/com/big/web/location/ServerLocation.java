package com.big.web.location;

public class ServerLocation {
	private String city;
	private String country;
	private String hostName;
	private String ip;
	private String latitude;
	private String longitude;
	private String org;
	private String region;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	@Override
	public String toString() {
		return "ServerLocation [city=" + city + ", country=" + country
				+ ", hostName=" + hostName + ", ip=" + ip + ", latitude="
				+ latitude + ", longitude=" + longitude + ", org=" + org
				+ ", region=" + region + "]";
	}

	
}
