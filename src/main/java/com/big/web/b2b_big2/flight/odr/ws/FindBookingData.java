
package com.big.web.b2b_big2.flight.odr.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for FindBookingData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindBookingData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExtensionData" type="{http://tempuri.org/}ExtensionDataObject" minOccurs="0"/>
 *         &lt;element name="FlightDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FromCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RecordLocator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BookingID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="PassengerID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="BookingStatus" type="{http://tempuri.org/}BookingStatus"/>
 *         &lt;element name="FlightNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChannelType" type="{http://tempuri.org/}ChannelType"/>
 *         &lt;element name="SourceOrganizationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SourceDomainCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SourceAgentCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Editable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Name" type="{http://tempuri.org/}BookingName" minOccurs="0"/>
 *         &lt;element name="ExpiredDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="AllowedToModifyGDSBooking" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SystemCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindBookingData", propOrder = {
    "extensionData",
    "flightDate",
    "fromCity",
    "toCity",
    "recordLocator",
    "bookingID",
    "passengerID",
    "bookingStatus",
    "flightNumber",
    "channelType",
    "sourceOrganizationCode",
    "sourceDomainCode",
    "sourceAgentCode",
    "editable",
    "name",
    "expiredDate",
    "allowedToModifyGDSBooking",
    "systemCode"
})
public class FindBookingData {

    @XmlElement(name = "ExtensionData")
    protected ExtensionDataObject extensionData;
    @XmlElement(name = "FlightDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar flightDate;
    @XmlElement(name = "FromCity")
    protected String fromCity;
    @XmlElement(name = "ToCity")
    protected String toCity;
    @XmlElement(name = "RecordLocator")
    protected String recordLocator;
    @XmlElement(name = "BookingID")
    protected long bookingID;
    @XmlElement(name = "PassengerID")
    protected long passengerID;
    @XmlElement(name = "BookingStatus", required = true)
    @XmlSchemaType(name = "string")
    protected BookingStatus bookingStatus;
    @XmlElement(name = "FlightNumber")
    protected String flightNumber;
    @XmlElement(name = "ChannelType", required = true)
    @XmlSchemaType(name = "string")
    protected ChannelType channelType;
    @XmlElement(name = "SourceOrganizationCode")
    protected String sourceOrganizationCode;
    @XmlElement(name = "SourceDomainCode")
    protected String sourceDomainCode;
    @XmlElement(name = "SourceAgentCode")
    protected String sourceAgentCode;
    @XmlElement(name = "Editable")
    protected boolean editable;
    @XmlElement(name = "Name")
    protected BookingName name;
    @XmlElement(name = "ExpiredDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expiredDate;
    @XmlElement(name = "AllowedToModifyGDSBooking")
    protected boolean allowedToModifyGDSBooking;
    @XmlElement(name = "SystemCode")
    protected String systemCode;

    /**
     * Gets the value of the extensionData property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionDataObject }
     *     
     */
    public ExtensionDataObject getExtensionData() {
        return extensionData;
    }

    /**
     * Sets the value of the extensionData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionDataObject }
     *     
     */
    public void setExtensionData(ExtensionDataObject value) {
        this.extensionData = value;
    }

    /**
     * Gets the value of the flightDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFlightDate() {
        return flightDate;
    }

    /**
     * Sets the value of the flightDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFlightDate(XMLGregorianCalendar value) {
        this.flightDate = value;
    }

    /**
     * Gets the value of the fromCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromCity() {
        return fromCity;
    }

    /**
     * Sets the value of the fromCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromCity(String value) {
        this.fromCity = value;
    }

    /**
     * Gets the value of the toCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToCity() {
        return toCity;
    }

    /**
     * Sets the value of the toCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToCity(String value) {
        this.toCity = value;
    }

    /**
     * Gets the value of the recordLocator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordLocator() {
        return recordLocator;
    }

    /**
     * Sets the value of the recordLocator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordLocator(String value) {
        this.recordLocator = value;
    }

    /**
     * Gets the value of the bookingID property.
     * 
     */
    public long getBookingID() {
        return bookingID;
    }

    /**
     * Sets the value of the bookingID property.
     * 
     */
    public void setBookingID(long value) {
        this.bookingID = value;
    }

    /**
     * Gets the value of the passengerID property.
     * 
     */
    public long getPassengerID() {
        return passengerID;
    }

    /**
     * Sets the value of the passengerID property.
     * 
     */
    public void setPassengerID(long value) {
        this.passengerID = value;
    }

    /**
     * Gets the value of the bookingStatus property.
     * 
     * @return
     *     possible object is
     *     {@link BookingStatus }
     *     
     */
    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    /**
     * Sets the value of the bookingStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingStatus }
     *     
     */
    public void setBookingStatus(BookingStatus value) {
        this.bookingStatus = value;
    }

    /**
     * Gets the value of the flightNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Sets the value of the flightNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightNumber(String value) {
        this.flightNumber = value;
    }

    /**
     * Gets the value of the channelType property.
     * 
     * @return
     *     possible object is
     *     {@link ChannelType }
     *     
     */
    public ChannelType getChannelType() {
        return channelType;
    }

    /**
     * Sets the value of the channelType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChannelType }
     *     
     */
    public void setChannelType(ChannelType value) {
        this.channelType = value;
    }

    /**
     * Gets the value of the sourceOrganizationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceOrganizationCode() {
        return sourceOrganizationCode;
    }

    /**
     * Sets the value of the sourceOrganizationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceOrganizationCode(String value) {
        this.sourceOrganizationCode = value;
    }

    /**
     * Gets the value of the sourceDomainCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceDomainCode() {
        return sourceDomainCode;
    }

    /**
     * Sets the value of the sourceDomainCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceDomainCode(String value) {
        this.sourceDomainCode = value;
    }

    /**
     * Gets the value of the sourceAgentCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceAgentCode() {
        return sourceAgentCode;
    }

    /**
     * Sets the value of the sourceAgentCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceAgentCode(String value) {
        this.sourceAgentCode = value;
    }

    /**
     * Gets the value of the editable property.
     * 
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Sets the value of the editable property.
     * 
     */
    public void setEditable(boolean value) {
        this.editable = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link BookingName }
     *     
     */
    public BookingName getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookingName }
     *     
     */
    public void setName(BookingName value) {
        this.name = value;
    }

    /**
     * Gets the value of the expiredDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpiredDate() {
        return expiredDate;
    }

    /**
     * Sets the value of the expiredDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpiredDate(XMLGregorianCalendar value) {
        this.expiredDate = value;
    }

    /**
     * Gets the value of the allowedToModifyGDSBooking property.
     * 
     */
    public boolean isAllowedToModifyGDSBooking() {
        return allowedToModifyGDSBooking;
    }

    /**
     * Sets the value of the allowedToModifyGDSBooking property.
     * 
     */
    public void setAllowedToModifyGDSBooking(boolean value) {
        this.allowedToModifyGDSBooking = value;
    }

    /**
     * Gets the value of the systemCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * Sets the value of the systemCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemCode(String value) {
        this.systemCode = value;
    }

}
