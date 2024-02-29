//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.08.13 at 05:45:30 PM WIB 
//


package id.co.danamon.dbank.servicename.domain.dto.external.accountdetailinquiry.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CIMB_RqUID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="CIMB_NonFinSvcHeaderInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="BankId" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                   &lt;element name="BranchId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AcctId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="AcctType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "cimbRqUID",
        "cimbNonFinSvcHeaderInfo",
        "acctId",
        "acctType"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CIMBAcctInqRq  {

    @XmlElement(name = "CIMB_RqUID", required = true)
    protected String cimbRqUID;
    @XmlElement(name = "CIMB_NonFinSvcHeaderInfo", required = true)
    protected CIMBNonFinSvcHeaderInfo cimbNonFinSvcHeaderInfo;
    @XmlElement(name = "AcctId")
    protected long acctId;
    @XmlElement(name = "AcctType", required = true)
    protected String acctType;
}
