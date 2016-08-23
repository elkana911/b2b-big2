
package com.big.web.b2b_big2.flight.odr.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="ConfirmBookingResult" type="{http://tempuri.org/}BookingFlight" minOccurs="0"/>
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
    "confirmBookingResult"
})
@XmlRootElement(name = "ConfirmBookingResponse")
public class ConfirmBookingResponse {

    @XmlElement(name = "ConfirmBookingResult")
    protected BookingFlight confirmBookingResult;

    /**
     * Gets the value of the confirmBookingResult property.
     * 
     * @return
     *     possible object is
     *     {@link BookingFlight }
     *     
     */
    public BookingFlight getConfirmBookingResult() {
        return confirmBookingResult;
    }

    /**
     * Sets the value of the confirmBookingResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingFlight }
     *     
     */
    public void setConfirmBookingResult(BookingFlight value) {
        this.confirmBookingResult = value;
    }

}
