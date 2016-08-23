package com.big.web.ws.quote;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.big.web.ws.quote.bean.SQuotes;
 

/**
 * Contoh panduan web service client.
 * Class2 yang dibuat manual:
 * SOAPClient.java
 * *.bean.SQuote.java
 * *.bean.SQuotes.java
 * 
 * Class2 yg dibuat oleh wsdl2java:
 * GetQuote.java	> abaikan
 * GetQuoteResponse.java	>abaikan
 * ObjectFactory.java	>abaikan
 * package-info.java	>abaikan
 * StockQuote.java		>useful
 * StockQuoteHttpGet.java	>abaikan
 * StockQuoteHttpPost.java	>abaikan
 * StockQuoteSoap.java	>useful
 * 
 * Cara dapatin object via wsdl2java (2 langkah cepat):
 * 1. paste wsdl url (seperti ****?WSDL) ke browser, save as "afile.wsdl"
 * 2. wsdl2java afile.wsdl
 * @author Administrator
 *
 */
public class SOAPClient {

	public static void getWSToString(){
    	StockQuote quote = new StockQuote();
    	
    	StockQuoteSoap soap =  quote.getStockQuoteSoap();
    	
    	System.err.println(soap.getQuote("AAPL"));
    	//<StockQuotes><Stock><Symbol>AAPL</Symbol><Last>112.98</Last><Date>1/23/2015</Date><Time>4:00pm</Time><Change>+0.58</Change><Open>112.32</Open><High>113.75</High><Low>111.53</Low><Volume>46464828</Volume><MktCap>662.6B</MktCap><PreviousClose>112.40</PreviousClose><PercentageChange>+0.52%</PercentageChange><AnnRange>70.5071 - 119.75</AnnRange><Earns>6.45</Earns><P-E>17.43</P-E><Name>Apple Inc.</Name></Stock></StockQuotes>

	}
	
	public static void getWSToObject() throws JAXBException{
    	StockQuote quote = new StockQuote();
    	
    	StockQuoteSoap soap =  quote.getStockQuoteSoap();
    	
		JAXBContext jaxbContext= JAXBContext.newInstance(SQuotes.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		String xml = soap.getQuote("AAPL");
		InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
		SQuotes item = (SQuotes) unmarshaller.unmarshal(inputStream);
		
		System.err.println(item.toString());
	}
	
    public static void main(String[] args) {
    	try {
			getWSToObject();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
}