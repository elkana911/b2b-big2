package com.big.web.ws.quote;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.0.3
 * 2015-01-26T15:08:59.803+07:00
 * Generated source version: 3.0.3
 * 
 */
@WebService(targetNamespace = "http://www.webserviceX.NET/", name = "StockQuoteHttpPost")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface StockQuoteHttpPost {

    /**
     * Get Stock quote for a company Symbol
     */
    @WebResult(name = "string", targetNamespace = "http://www.webserviceX.NET/", partName = "Body")
    @WebMethod(operationName = "GetQuote")
    public java.lang.String getQuote(
        @WebParam(partName = "symbol", name = "symbol", targetNamespace = "http://www.webserviceX.NET/")
        java.lang.String symbol
    );
}
