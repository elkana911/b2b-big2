
package com.big.web.b2b_big2.flight.odr.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OSISeverity.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OSISeverity">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="General"/>
 *     &lt;enumeration value="Warning"/>
 *     &lt;enumeration value="Critical"/>
 *     &lt;enumeration value="Unmapped"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OSISeverity")
@XmlEnum
public enum OSISeverity {

    @XmlEnumValue("General")
    GENERAL("General"),
    @XmlEnumValue("Warning")
    WARNING("Warning"),
    @XmlEnumValue("Critical")
    CRITICAL("Critical"),
    @XmlEnumValue("Unmapped")
    UNMAPPED("Unmapped");
    private final String value;

    OSISeverity(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OSISeverity fromValue(String v) {
        for (OSISeverity c: OSISeverity.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
