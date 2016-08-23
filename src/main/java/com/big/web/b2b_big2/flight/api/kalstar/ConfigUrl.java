package com.big.web.b2b_big2.flight.api.kalstar;



public class ConfigUrl {
	private String protocol;
	private String host;
	private String apiKey;
	private String airlineCode;

	public ConfigUrl() {
		// TODO Auto-generated constructor stub
	}

	public ConfigUrl(String protocol, String host, String apiKey, String airlineCode) {
		super();
		this.protocol = protocol;
		this.host = host;
		this.apiKey = apiKey;
		this.airlineCode = airlineCode;
	}
	/*
	//configure
	protocol = Utils.nvl(setupDao.getValue(KEY_PROTOCOL), "http");
	host = Utils.nvl(setupDao.getValue(KEY_HOST), "ws.demo.awan.sqiva.com");
	apiKey =  Utils.nvl(setupDao.getValue(KEY_API), "5EB9FE68-8915-11E0-BEA0-C9892766ECF2");
	airlineCode = Utils.nvl(setupDao.getValue(KEY_CODE), "KD");
	*/
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getAirlineCode() {
		return airlineCode;
	}
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	
	public String getRootUrl(){
//		protocol = Utils.nvl(setupDao.getValue(KEY_PROTOCOL), "http");
//		host = Utils.nvl(setupDao.getValue(KEY_HOST), "ws.demo.awan.sqiva.com");
//		apiKey =  Utils.nvl(setupDao.getValue(KEY_API), "5EB9FE68-8915-11E0-BEA0-C9892766ECF2");
//		airlineCode = Utils.nvl(setupDao.getValue(KEY_CODE), "KD");
		
//	    final String API_KEY = "5EB9FE68-8915-11E0-BEA0-C9892766ECF2";
//		final String rootUrl = "http://ws.demo.awan.sqiva.com/?rqid=" + API_KEY + "&airline_code=W2";
		return new StringBuffer(protocol).append("://")
				.append(host)
				.append("/?rqid=").append(apiKey)
				.append("&airline_code=").append(airlineCode)
				.toString();
	}
	
	public static void main(String[] args) {
		ConfigUrl c = new ConfigUrl();
		c.setAirlineCode("KD");
		c.setApiKey("123-ABC");
		c.setHost("ws.demo.awan.sqiva.com");
		c.setProtocol("http");
		System.err.println(c.getRootUrl());
	}
	
	@Override
	public String toString() {
		return "ConfigUrl [protocol=" + protocol + ", host=" + host + ", apiKey=" + apiKey + ", airlineCode="
				+ airlineCode + "]";
	}
	
	

}
