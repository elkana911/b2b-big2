
package com.big.web.b2b_big2.flight.odr.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FindBookingResponseData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindBookingResponseData">
 *   &lt;complexContent>
 *     &lt;extension base="{http://tempuri.org/}ServiceMessage">
 *       &lt;sequence>
 *         &lt;element name="Records" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EndingID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="FindBookingDataList" type="{http://tempuri.org/}ArrayOfFindBookingData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindBookingResponseData", propOrder = {
    "records",
    "endingID",
    "findBookingDataList"
})
public class FindBookingResponseData
    extends ServiceMessage
{

    @XmlElement(name = "Records")
    protected int records;
    @XmlElement(name = "EndingID")
    protected long endingID;
    @XmlElement(name = "FindBookingDataList")
    protected ArrayOfFindBookingData findBookingDataList;

    /**
     * Gets the value of the records property.
     * 
     */
    public int getRecords() {
        return records;
    }

    /**
     * Sets the value of the records property.
     * 
     */
    public void setRecords(int value) {
        this.records = value;
    }

    /**
     * Gets the value of the endingID property.
     * 
     */
    public long getEndingID() {
        return endingID;
    }

    /**
     * Sets the value of the endingID property.
     * 
     */
    public void setEndingID(long value) {
        this.endingID = value;
    }

    /**
     * Gets the value of the findBookingDataList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFindBookingData }
     *     
     */
    public ArrayOfFindBookingData getFindBookingDataList() {
        return findBookingDataList;
    }

    /**
     * Sets the value of the findBookingDataList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFindBookingData }
     *     
     */
    public void setFindBookingDataList(ArrayOfFindBookingData value) {
        this.findBookingDataList = value;
    }

}
