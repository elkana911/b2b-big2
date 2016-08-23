
package com.big.web.b2b_big2.flight.odr.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BookingStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BookingStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Default"/>
 *     &lt;enumeration value="Hold"/>
 *     &lt;enumeration value="Confirmed"/>
 *     &lt;enumeration value="Closed"/>
 *     &lt;enumeration value="HoldCanceled"/>
 *     &lt;enumeration value="PendingArchive"/>
 *     &lt;enumeration value="Archived"/>
 *     &lt;enumeration value="Unmapped"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BookingStatus")
@XmlEnum
public enum BookingStatus {

    @XmlEnumValue("Default")
    DEFAULT("Default"),
    @XmlEnumValue("Hold")
    HOLD("Hold"),
    @XmlEnumValue("Confirmed")
    CONFIRMED("Confirmed"),
    @XmlEnumValue("Closed")
    CLOSED("Closed"),
    @XmlEnumValue("HoldCanceled")
    HOLD_CANCELED("HoldCanceled"),
    @XmlEnumValue("PendingArchive")
    PENDING_ARCHIVE("PendingArchive"),
    @XmlEnumValue("Archived")
    ARCHIVED("Archived"),
    @XmlEnumValue("Unmapped")
    UNMAPPED("Unmapped");
    private final String value;

    BookingStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BookingStatus fromValue(String v) {
        for (BookingStatus c: BookingStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
