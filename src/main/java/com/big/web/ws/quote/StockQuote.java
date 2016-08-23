package com.big.web.ws.quote;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.0.3
 * 2015-01-26T15:08:59.880+07:00
 * Generated source version: 3.0.3
 * 
 */
@WebServiceClient(name = "StockQuote", 
                  wsdlLocation = "quote.wsdl",
                  targetNamespace = "http://www.webserviceX.NET/") 
public class StockQuote extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.webserviceX.NET/", "StockQuote");
    public final static QName StockQuoteHttpPost = new QName("http://www.webserviceX.NET/", "StockQuoteHttpPost");
    public final static QName StockQuoteSoap = new QName("http://www.webserviceX.NET/", "StockQuoteSoap");
    public final static QName StockQuoteSoap12 = new QName("http://www.webserviceX.NET/", "StockQuoteSoap12");
    public final static QName StockQuoteHttpGet = new QName("http://www.webserviceX.NET/", "StockQuoteHttpGet");
    static {
        URL url = StockQuote.class.getResource("quote.wsdl");
        if (url == null) {
            url = StockQuote.class.getClassLoader().getResource("quote.wsdl");
        } 
        if (url == null) {
            java.util.logging.Logger.getLogger(StockQuote.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "quote.wsdl");
        }       
        WSDL_LOCATION = url;
    }

    public StockQuote(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public StockQuote(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public StockQuote() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public StockQuote(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public StockQuote(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public StockQuote(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    

    /**
     *
     * @return
     *     returns StockQuoteHttpPost
     */
    @WebEndpoint(name = "StockQuoteHttpPost")
    public StockQuoteHttpPost getStockQuoteHttpPost() {
        return super.getPort(StockQuoteHttpPost, StockQuoteHttpPost.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StockQuoteHttpPost
     */
    @WebEndpoint(name = "StockQuoteHttpPost")
    public StockQuoteHttpPost getStockQuoteHttpPost(WebServiceFeature... features) {
        return super.getPort(StockQuoteHttpPost, StockQuoteHttpPost.class, features);
    }
    /**
     *
     * @return
     *     returns StockQuoteSoap
     */
    @WebEndpoint(name = "StockQuoteSoap")
    public StockQuoteSoap getStockQuoteSoap() {
        return super.getPort(StockQuoteSoap, StockQuoteSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StockQuoteSoap
     */
    @WebEndpoint(name = "StockQuoteSoap")
    public StockQuoteSoap getStockQuoteSoap(WebServiceFeature... features) {
        return super.getPort(StockQuoteSoap, StockQuoteSoap.class, features);
    }
    /**
     *
     * @return
     *     returns StockQuoteSoap
     */
    @WebEndpoint(name = "StockQuoteSoap12")
    public StockQuoteSoap getStockQuoteSoap12() {
        return super.getPort(StockQuoteSoap12, StockQuoteSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StockQuoteSoap
     */
    @WebEndpoint(name = "StockQuoteSoap12")
    public StockQuoteSoap getStockQuoteSoap12(WebServiceFeature... features) {
        return super.getPort(StockQuoteSoap12, StockQuoteSoap.class, features);
    }
    /**
     *
     * @return
     *     returns StockQuoteHttpGet
     */
    @WebEndpoint(name = "StockQuoteHttpGet")
    public StockQuoteHttpGet getStockQuoteHttpGet() {
        return super.getPort(StockQuoteHttpGet, StockQuoteHttpGet.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StockQuoteHttpGet
     */
    @WebEndpoint(name = "StockQuoteHttpGet")
    public StockQuoteHttpGet getStockQuoteHttpGet(WebServiceFeature... features) {
        return super.getPort(StockQuoteHttpGet, StockQuoteHttpGet.class, features);
    }

}