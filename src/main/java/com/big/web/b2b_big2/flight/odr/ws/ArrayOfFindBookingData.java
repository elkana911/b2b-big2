
package com.big.web.b2b_big2.flight.odr.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfFindBookingData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfFindBookingData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FindBookingData" type="{http://tempuri.org/}FindBookingData" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFindBookingData", propOrder = {
    "findBookingData"
})
public class ArrayOfFindBookingData {

    @XmlElement(name = "FindBookingData", nillable = true)
    protected List<FindBookingData> findBookingData;

    /**
     * Gets the value of the findBookingData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the findBookingData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFindBookingData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FindBookingData }
     * 
     * 
     */
    public List<FindBookingData> getFindBookingData() {
        if (findBookingData == null) {
            findBookingData = new ArrayList<FindBookingData>();
        }
        return this.findBookingData;
    }

}
