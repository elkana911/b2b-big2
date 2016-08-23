
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
 *         &lt;element name="CommitBookingResult" type="{http://tempuri.org/}BookingFlight" minOccurs="0"/>
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
    "commitBookingResult"
})
@XmlRootElement(name = "CommitBookingResponse")
public class CommitBookingResponse {

    @XmlElement(name = "CommitBookingResult")
    protected BookingFlight commitBookingResult;

    /**
     * Gets the value of the commitBookingResult property.
     * 
     * @return
     *     possible object is
     *     {@link BookingFlight }
     *     
     */
    public BookingFlight getCommitBookingResult() {
        return commitBookingResult;
    }

    /**
     * Sets the value of the commitBookingResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingFlight }
     *     
     */
    public void setCommitBookingResult(BookingFlight value) {
        this.commitBookingResult = value;
    }

}
