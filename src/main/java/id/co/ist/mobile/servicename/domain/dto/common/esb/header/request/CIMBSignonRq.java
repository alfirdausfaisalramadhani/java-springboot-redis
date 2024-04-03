//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.08.13 at 05:45:30 PM WIB 
//


package id.co.ist.mobile.servicename.domain.dto.common.esb.header.request;

import id.co.ist.mobile.servicename.domain.dto.common.esb.header.ClientApp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;


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
 *         &lt;element name="ClientDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CustLangPref" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ClientApp">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Org" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="CIMB_HeaderRq">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CIMB_ConsumerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CIMB_ConsumerPasswd" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CIMB_ServiceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CIMB_ServiceVersion" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                   &lt;element name="CIMB_SrcSystem" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CIMB_ProviderList">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="CIMB_Provider">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="CIMB_ProviderRqUID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                       &lt;element name="CIMB_ProviderSystemCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="CIMB_SrcCountryCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "clientDt",
    "custLangPref",
    "clientApp",
    "cimbHeaderRq"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CIMBSignonRq  {

    @XmlElement(name = "ClientDt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected String clientDt;
    @XmlElement(name = "CustLangPref", required = true)
    protected String custLangPref;
    @XmlElement(name = "ClientApp", required = true)
    protected ClientApp clientApp;
    @XmlElement(name = "CIMB_HeaderRq", required = true)
    protected CIMBHeaderRq cimbHeaderRq;

}