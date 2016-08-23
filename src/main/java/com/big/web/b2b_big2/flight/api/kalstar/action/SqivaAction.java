package com.big.web.b2b_big2.flight.api.kalstar.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.ws.rs.core.UriBuilder;

import com.big.web.b2b_big2.flight.api.kalstar.ConfigUrl;
import com.big.web.b2b_big2.util.StopWatch;

public abstract class SqivaAction implements ISqivaAction {
	
	private ConfigUrl configUrl;
	
	public UriBuilder buildPreUrl(Action action) throws Exception{
			
		return UriBuilder.fromUri(configUrl.getRootUrl())
				.queryParam("app", URLEncoder.encode(action.getAppType(), "UTF-8"))
				.queryParam("action", URLEncoder.encode(action.name().toLowerCase(), "UTF-8"));
	}

	public ConfigUrl getConfigUrl() {
		return configUrl;
	}

	public void setConfigUrl(ConfigUrl configUrl) {
		this.configUrl = configUrl;
	}
	
	/**
	 * Berhubung cara koneksinya khusus, method ini dibedain dengan standarnya.
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public String getFromServer(String url) throws MalformedURLException, IOException{
        HttpURLConnection con = null ;
        InputStream is = null;
        
        StopWatch sw = StopWatch.AutoStart();
        try {
            con = (HttpURLConnection) ( new URL(url)).openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "WS-AWAN");
            con.setDoInput(true);
            con.setDoOutput(true);
            
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();
            
           return buffer.toString(); 
    		
        }
        finally {
            System.out.println(con.getResponseMessage() + "(" + con.getResponseCode() + ") " + sw.getElapsedTimeInString());

            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }
	
	}

	/*
	public static UriBuilder buildPreUrl(ConfigUrl configUrl, Action action) throws Exception{
		return UriBuilder.fromUri( rootUrl)
				.queryParam("app", URLEncoder.encode(action.getAppType(), "UTF-8"))
				.queryParam("action", URLEncoder.encode(action.name().toLowerCase(), "UTF-8"));
	}
	*/


}
