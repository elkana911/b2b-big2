
package com.big.web.b2b_big2.flight.odr.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChannelType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ChannelType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Default"/>
 *     &lt;enumeration value="Direct"/>
 *     &lt;enumeration value="Web"/>
 *     &lt;enumeration value="GDS"/>
 *     &lt;enumeration value="API"/>
 *     &lt;enumeration value="Unmapped"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ChannelType")
@XmlEnum
public enum ChannelType {

    @XmlEnumValue("Default")
    DEFAULT("Default"),
    @XmlEnumValue("Direct")
    DIRECT("Direct"),
    @XmlEnumValue("Web")
    WEB("Web"),
    GDS("GDS"),
    API("API"),
    @XmlEnumValue("Unmapped")
    UNMAPPED("Unmapped");
    private final String value;

    ChannelType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChannelType fromValue(String v) {
        for (ChannelType c: ChannelType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
