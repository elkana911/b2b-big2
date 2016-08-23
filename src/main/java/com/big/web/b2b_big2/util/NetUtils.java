package com.big.web.b2b_big2.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.core.UriBuilder;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.Response;

public class NetUtils {
	

	/**
	 * 
	 * @param message Server returned HTTP response code: 500 for URL: http://ws.demo.awan.sqiva.com/?rqid=5EB9FE68-8915-11E0-BEA0-C9892766ECF2&amp;airline_code=W2&amp...
	 * @return Server returned HTTP response code: 500...
	 */
	public static String protectImportantMessageFromUser(String message) {
		String key1 = "http response code";
		int i = message.toLowerCase().indexOf(key1);
		
		if (message.toLowerCase().indexOf(key1) > 0){
			return message.substring(0, i + key1.length() + 6);
		}else
			return message;
	}

	@SuppressWarnings("rawtypes")
	public static String buildUrlParam(String base, Hashtable keyValue) throws IllegalArgumentException, UnsupportedEncodingException{
		
		UriBuilder uri = UriBuilder.fromUri(base);
		
		Enumeration e = keyValue.keys();
		    while (e.hasMoreElements()) {
		      String key = (String) e.nextElement();
		      // masalahnya + akan dibaca sebagai %2B nantinya http://stackoverflow.com/questions/4737841/urlencoder-not-able-to-translate-space-character
		      uri = uri.queryParam(key, URLEncoder.encode((String)keyValue.get(key), "UTF-8").replaceAll("\\+", "%20"))
		    		  ;
		}

		
		return uri.build().toString();
	}
	
	/**
	 * This function use {@link HttpURLConnection} which is not
	 * For multiple request, use 
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static String getFromServer(String url) throws MalformedURLException, IOException {
		
        HttpURLConnection con = null ;
        InputStream is = null;

//        setElapsedNetwork("0");
//        StopWatch sw = StopWatch.AutoStart();
        try {
            con = (HttpURLConnection) ( new URL(url)).openConnection();
            con.setRequestMethod("GET");
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
            
//            setElapsedNetwork(sw.stopAndGetAsString());
            
           return buffer.toString(); 
    		
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }
	
	}

	public static String getFromServer(String url, int timeOutMinutes) throws InterruptedException, IOException, ExecutionException{
		
		int timeOutMilSec = timeOutMinutes * 60 * 1000;
//		AsyncHttpClientConfig config = new AsyncHttpClientConfig.Builder().setRequestTimeout(8 * 60 * 1000).build();
		AsyncHttpClientConfig config = new AsyncHttpClientConfig.Builder()
									.setReadTimeout(timeOutMilSec)
									.setConnectTimeout(timeOutMilSec)
									.setRequestTimeout(timeOutMilSec)
//									.setWebSocketTimeout(timeOutMilSec)
//									.setProxyServer(new ProxyServer("127.0.0.1", 38080))
									.build();

		AsyncHttpClient asyncHttpClient = new AsyncHttpClient(config);
		
		//you may change response to Integer to getstatuscode for instance
		Future<Response> f = asyncHttpClient.prepareGet(url).execute(
		   new AsyncCompletionHandler<Response>(){

		    @Override
		    public Response onCompleted(Response response) throws Exception{
		        // Do something with the Response
//		    	data = response.getResponseBody();
		        return response;
		    }

		    @Override
		    public void onThrowable(Throwable t){
		        // Something wrong happened.
		    	t.printStackTrace();
		    }
		});
		
		boolean isDone = false;
		do{
			isDone = f.isDone();

			Thread.sleep(50);
		}while (!isDone);
		
		//because done need to close
		asyncHttpClient.close();
		
		return f.get().getResponseBody();
	}
}
