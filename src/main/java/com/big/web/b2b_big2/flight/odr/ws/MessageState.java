
package com.big.web.b2b_big2.flight.odr.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MessageState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MessageState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="New"/>
 *     &lt;enumeration value="Clean"/>
 *     &lt;enumeration value="Modified"/>
 *     &lt;enumeration value="Deleted"/>
 *     &lt;enumeration value="Confirmed"/>
 *     &lt;enumeration value="Unmapped"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MessageState")
@XmlEnum
public enum MessageState {

    @XmlEnumValue("New")
    NEW("New"),
    @XmlEnumValue("Clean")
    CLEAN("Clean"),
    @XmlEnumValue("Modified")
    MODIFIED("Modified"),
    @XmlEnumValue("Deleted")
    DELETED("Deleted"),
    @XmlEnumValue("Confirmed")
    CONFIRMED("Confirmed"),
    @XmlEnumValue("Unmapped")
    UNMAPPED("Unmapped");
    private final String value;

    MessageState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MessageState fromValue(String v) {
        for (MessageState c: MessageState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
