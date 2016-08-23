
package com.big.web.b2b_big2.flight.odr.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExtensionData" type="{http://tempuri.org/}ExtensionDataObject" minOccurs="0"/>
 *         &lt;element name="OtherServiceInfoList" type="{http://tempuri.org/}ArrayOfOtherServiceInformation" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceMessage", propOrder = {
    "extensionData",
    "otherServiceInfoList"
})
@XmlSeeAlso({
    FindBookingResponseData.class
})
public class ServiceMessage {

    @XmlElement(name = "ExtensionData")
    protected ExtensionDataObject extensionData;
    @XmlElement(name = "OtherServiceInfoList")
    protected ArrayOfOtherServiceInformation otherServiceInfoList;

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
     * Gets the value of the otherServiceInfoList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOtherServiceInformation }
     *     
     */
    public ArrayOfOtherServiceInformation getOtherServiceInfoList() {
        return otherServiceInfoList;
    }

    /**
     * Sets the value of the otherServiceInfoList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOtherServiceInformation }
     *     
     */
    public void setOtherServiceInfoList(ArrayOfOtherServiceInformation value) {
        this.otherServiceInfoList = value;
    }

}
