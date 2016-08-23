
package com.big.web.b2b_big2.flight.odr.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfOtherServiceInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOtherServiceInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OtherServiceInformation" type="{http://tempuri.org/}OtherServiceInformation" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOtherServiceInformation", propOrder = {
    "otherServiceInformation"
})
public class ArrayOfOtherServiceInformation {

    @XmlElement(name = "OtherServiceInformation", nillable = true)
    protected List<OtherServiceInformation> otherServiceInformation;

    /**
     * Gets the value of the otherServiceInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otherServiceInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtherServiceInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OtherServiceInformation }
     * 
     * 
     */
    public List<OtherServiceInformation> getOtherServiceInformation() {
        if (otherServiceInformation == null) {
            otherServiceInformation = new ArrayList<OtherServiceInformation>();
        }
        return this.otherServiceInformation;
    }

}
