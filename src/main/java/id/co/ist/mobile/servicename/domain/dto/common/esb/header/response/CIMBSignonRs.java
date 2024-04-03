//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.08.13 at 05:46:59 PM WIB 
//


package id.co.ist.mobile.servicename.domain.dto.common.esb.header.response;

import id.co.ist.mobile.servicename.domain.dto.common.esb.header.ClientApp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="ServerDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Language" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CIMB_HeaderRs">
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
 *                                       &lt;element name="CIMB_ProviderAuthDetail">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="CIMB_ProviderUserId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="CIMB_ProviderUserPasswd" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="CIMB_ProviderRespDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                                       &lt;element name="CIMB_ProviderRespStatusList">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="CIMB_ProviderRespStatus">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="CIMB_StatusRqUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                           &lt;element name="CIMB_StatusCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                           &lt;element name="CIMB_StatusDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                           &lt;element name="CIMB_Severity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
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
 *                   &lt;element name="CIMB_ServicePagingFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CIMB_PaginationList">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="CIMB_Pagination">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="CIMB_ProviderPaginationKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "serverDt",
    "language",
    "cimbHeaderRs"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CIMBSignonRs  {

    @XmlElement(name = "ClientDt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar clientDt;
    @XmlElement(name = "CustLangPref", required = true)
    protected String custLangPref;
    @XmlElement(name = "ClientApp", required = true)
    protected ClientApp clientApp;
    @XmlElement(name = "ServerDt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar serverDt;
    @XmlElement(name = "Language", required = true)
    protected String language;
    @XmlElement(name = "CIMB_HeaderRs", required = true)
    protected CIMBHeaderRs cimbHeaderRs;

}
