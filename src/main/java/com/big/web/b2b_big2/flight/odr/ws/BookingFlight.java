
package com.big.web.b2b_big2.flight.odr.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BookingFlight complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BookingFlight">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BookID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CarrierCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Origin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Destination" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DepartureDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fareSellKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PassCount" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="JourneyKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BookingCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BookingAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="NTA" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TimeToPay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BookStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingFlight", propOrder = {
    "bookID",
    "carrierCode",
    "origin",
    "destination",
    "departureDate",
    "fareSellKey",
    "passCount",
    "journeyKey",
    "bookingCode",
    "bookingAmount",
    "nta",
    "errorCode",
    "timeToPay",
    "bookStatus"
})
public class BookingFlight {

    @XmlElement(name = "BookID")
    protected String bookID;
    @XmlElement(name = "CarrierCode")
    protected String carrierCode;
    @XmlElement(name = "Origin")
    protected String origin;
    @XmlElement(name = "Destination")
    protected String destination;
    @XmlElement(name = "DepartureDate")
    protected String departureDate;
    protected String fareSellKey;
    @XmlElement(name = "PassCount")
    protected short passCount;
    @XmlElement(name = "JourneyKey")
    protected String journeyKey;
    @XmlElement(name = "BookingCode")
    protected String bookingCode;
    @XmlElement(name = "BookingAmount")
    protected double bookingAmount;
    @XmlElement(name = "NTA")
    protected double nta;
    @XmlElement(name = "ErrorCode")
    protected String errorCode;
    @XmlElement(name = "TimeToPay")
    protected String timeToPay;
    @XmlElement(name = "BookStatus")
    protected String bookStatus;

    /**
     * Gets the value of the bookID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookID() {
        return bookID;
    }

    /**
     * Sets the value of the bookID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookID(String value) {
        this.bookID = value;
    }

    /**
     * Gets the value of the carrierCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarrierCode() {
        return carrierCode;
    }

    /**
     * Sets the value of the carrierCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarrierCode(String value) {
        this.carrierCode = value;
    }

    /**
     * Gets the value of the origin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the value of the origin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigin(String value) {
        this.origin = value;
    }

    /**
     * Gets the value of the destination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestination(String value) {
        this.destination = value;
    }

    /**
     * Gets the value of the departureDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the value of the departureDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureDate(String value) {
        this.departureDate = value;
    }

    /**
     * Gets the value of the fareSellKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFareSellKey() {
        return fareSellKey;
    }

    /**
     * Sets the value of the fareSellKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFareSellKey(String value) {
        this.fareSellKey = value;
    }

    /**
     * Gets the value of the passCount property.
     * 
     */
    public short getPassCount() {
        return passCount;
    }

    /**
     * Sets the value of the passCount property.
     * 
     */
    public void setPassCount(short value) {
        this.passCount = value;
    }

    /**
     * Gets the value of the journeyKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJourneyKey() {
        return journeyKey;
    }

    /**
     * Sets the value of the journeyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJourneyKey(String value) {
        this.journeyKey = value;
    }

    /**
     * Gets the value of the bookingCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingCode() {
        return bookingCode;
    }

    /**
     * Sets the value of the bookingCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingCode(String value) {
        this.bookingCode = value;
    }

    /**
     * Gets the value of the bookingAmount property.
     * 
     */
    public double getBookingAmount() {
        return bookingAmount;
    }

    /**
     * Sets the value of the bookingAmount property.
     * 
     */
    public void setBookingAmount(double value) {
        this.bookingAmount = value;
    }

    /**
     * Gets the value of the nta property.
     * 
     */
    public double getNTA() {
        return nta;
    }

    /**
     * Sets the value of the nta property.
     * 
     */
    public void setNTA(double value) {
        this.nta = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the timeToPay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeToPay() {
        return timeToPay;
    }

    /**
     * Sets the value of the timeToPay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeToPay(String value) {
        this.timeToPay = value;
    }

    /**
     * Gets the value of the bookStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookStatus() {
        return bookStatus;
    }

    /**
     * Sets the value of the bookStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookStatus(String value) {
        this.bookStatus = value;
    }

}
