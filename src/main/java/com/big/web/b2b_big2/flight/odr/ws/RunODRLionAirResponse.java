
package com.big.web.b2b_big2.flight.odr.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="runODRLionAirResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "runODRLionAirResult"
})
@XmlRootElement(name = "runODRLionAirResponse")
public class RunODRLionAirResponse {

    protected String runODRLionAirResult;

    /**
     * Gets the value of the runODRLionAirResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRunODRLionAirResult() {
        return runODRLionAirResult;
    }

    /**
     * Sets the value of the runODRLionAirResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRunODRLionAirResult(String value) {
        this.runODRLionAirResult = value;
    }

}
