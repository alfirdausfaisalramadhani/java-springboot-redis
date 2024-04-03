package service;

import id.co.ist.mobile.Application;
import id.co.ist.mobile.common.constant.SourceSystem;
import id.co.ist.mobile.common.exception.MobileRestException;
import id.co.ist.mobile.servicename.config.SOAPConnector;
import id.co.ist.mobile.servicename.domain.dto.common.esb.body.Status;
import id.co.ist.mobile.servicename.domain.dto.common.esb.header.CIMBProvider;
import id.co.ist.mobile.servicename.domain.dto.common.esb.header.CIMBProviderRespStatus;
import id.co.ist.mobile.servicename.domain.dto.common.esb.header.CIMBProviderRespStatusList;
import id.co.ist.mobile.servicename.domain.dto.external.accountdetailinquiry.CIMBAcctInqSvc;
import id.co.ist.mobile.servicename.service.ESBService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ESBServiceTest {
    @Qualifier("soapConnector")
    @MockBean
    private SOAPConnector soapConnector;

    @Value("${esb.accountDetailInquiry.url}")
    private String esbAccountDetailInquiryURL;

    @Autowired
    ESBService esbService;

    @Test
    public void getAccountInquiryDetailTest() throws SOAPException, JAXBException, IOException {
        CIMBAcctInqSvc esbResponse = generateResponseESBforAccountInquiry(responseESBAccountInquiry);
        when(soapConnector.callWebService(eq(esbAccountDetailInquiryURL), any()))
                .thenReturn(esbResponse);
        CIMBAcctInqSvc response = esbService.getAccountInquiryDetail("1245", "SDA");
        assertNotNull(response);

    }

    public static CIMBAcctInqSvc generateResponseESBforAccountInquiry(String response) throws SOAPException, JAXBException, IOException {
        CIMBAcctInqSvc cimbAcctInqSvc = new CIMBAcctInqSvc();
        try {
            SOAPMessage message = MessageFactory.newInstance().createMessage(null, new ByteArrayInputStream(response.getBytes()));
            Unmarshaller unmarshaller = JAXBContext.newInstance(CIMBAcctInqSvc.class).createUnmarshaller();
            cimbAcctInqSvc = (CIMBAcctInqSvc) unmarshaller.unmarshal(message.getSOAPBody().extractContentAsDocument());
        } catch (Exception e) {
            log.error("errors {}", e);
            throw e;
        }
        log.info("hasil ubah xml ke java object {}", cimbAcctInqSvc);
        return cimbAcctInqSvc;
    }

    public static final String responseESBAccountInquiry =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<soapenv:Envelope\n" +
                    "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                    "    xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                    "    xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                    "    <soapenv:Body>\n" +
                    "        <esb:CIMB_AcctInqSvc\n" +
                    "            xmlns:esb=\"urn:ifxforum-org:XSD:1\">\n" +
                    "            <esb:CIMB_SignonRs>\n" +
                    "                <esb:ClientDt>2018-12-20T11:55:29.000628</esb:ClientDt>\n" +
                    "                <esb:CustLangPref>en</esb:CustLangPref>\n" +
                    "                <esb:ClientApp>\n" +
                    "                    <esb:Org>cimb.id.cimbniaga</esb:Org>\n" +
                    "                    <esb:Name>CIMB Niaga</esb:Name>\n" +
                    "                    <esb:Version>1.0</esb:Version>\n" +
                    "                </esb:ClientApp>\n" +
                    "                <esb:ServerDt>2021-08-09T16:17:34.325763</esb:ServerDt>\n" +
                    "                <esb:Language>EN</esb:Language>\n" +
                    "                <esb:CIMB_HeaderRs>\n" +
                    "                    <esb:CIMB_ConsumerId>GOMOB3_ID</esb:CIMB_ConsumerId>\n" +
                    "                    <esb:CIMB_ConsumerPasswd/>\n" +
                    "                    <esb:CIMB_ServiceName>AccountInquiry</esb:CIMB_ServiceName>\n" +
                    "                    <esb:CIMB_ServiceVersion>1.1</esb:CIMB_ServiceVersion>\n" +
                    "                    <esb:CIMB_SrcSystem>GOMOBILE_ID</esb:CIMB_SrcSystem>\n" +
                    "                    <esb:CIMB_ProviderList>\n" +
                    "                        <esb:CIMB_Provider>\n" +
                    "                            <esb:CIMB_ProviderRqUID>2919033896</esb:CIMB_ProviderRqUID>\n" +
                    "                            <esb:CIMB_ProviderSystemCode>SIBS_ID</esb:CIMB_ProviderSystemCode>\n" +
                    "                            <esb:CIMB_ProviderAuthDetail>\n" +
                    "                                <esb:CIMB_ProviderUserId/>\n" +
                    "                                <esb:CIMB_ProviderUserPasswd/>\n" +
                    "                            </esb:CIMB_ProviderAuthDetail>\n" +
                    "                            <esb:CIMB_ProviderRespDateTime>2021-08-09T16:17:34.325763</esb:CIMB_ProviderRespDateTime>\n" +
                    "                            <esb:CIMB_ProviderRespStatusList>\n" +
                    "                                <esb:CIMB_ProviderRespStatus>\n" +
                    "                                    <esb:CIMB_StatusRqUID>18sr8f93j3uv</esb:CIMB_StatusRqUID>\n" +
                    "                                    <esb:CIMB_StatusCode/>\n" +
                    "                                    <esb:CIMB_StatusDesc/>\n" +
                    "                                    <esb:CIMB_Severity/>\n" +
                    "                                </esb:CIMB_ProviderRespStatus>\n" +
                    "                            </esb:CIMB_ProviderRespStatusList>\n" +
                    "                        </esb:CIMB_Provider>\n" +
                    "                    </esb:CIMB_ProviderList>\n" +
                    "                    <esb:CIMB_SrcCountryCode>IDN</esb:CIMB_SrcCountryCode>\n" +
                    "                    <esb:CIMB_ServicePagingFlag>N</esb:CIMB_ServicePagingFlag>\n" +
                    "                    <esb:CIMB_PaginationList>\n" +
                    "                        <esb:CIMB_Pagination>\n" +
                    "                            <esb:CIMB_ProviderPaginationKey/>\n" +
                    "                        </esb:CIMB_Pagination>\n" +
                    "                    </esb:CIMB_PaginationList>\n" +
                    "                </esb:CIMB_HeaderRs>\n" +
                    "            </esb:CIMB_SignonRs>\n" +
                    "            <esb:CIMB_AcctInqRs>\n" +
                    "                <esb:CIMB_RqUID>15452817296282919033896</esb:CIMB_RqUID>\n" +
                    "                <esb:Status>\n" +
                    "                    <esb:StatusCode>0</esb:StatusCode>\n" +
                    "                    <esb:Severity/>\n" +
                    "                    <esb:StatusDesc>SUCCESS</esb:StatusDesc>\n" +
                    "                </esb:Status>\n" +
                    "                <esb:CIMB_AcctInfo>\n" +
                    "                    <esb:CIMB_AcctName>\n" +
                    "                        <esb:CIMB_PriAcctName>BULK162</esb:CIMB_PriAcctName>\n" +
                    "                    </esb:CIMB_AcctName>\n" +
                    "                    <esb:AcctId>703089258100</esb:AcctId>\n" +
                    "                    <esb:CustPermId>11120000188745</esb:CustPermId>\n" +
                    "                    <esb:BankInfo>\n" +
                    "                        <esb:BranchId>20029</esb:BranchId>\n" +
                    "                    </esb:BankInfo>\n" +
                    "                    <esb:ProductId>SAXTRA0034</esb:ProductId>\n" +
                    "                    <esb:Desc>OCTO SAVERS</esb:Desc>\n" +
                    "                    <esb:CurCode>IDR</esb:CurCode>\n" +
                    "                    <esb:AcctBal>\n" +
                    "                        <esb:CurAmt>\n" +
                    "                            <esb:Amt>00000001423979859</esb:Amt>\n" +
                    "                        </esb:CurAmt>\n" +
                    "                        <esb:BalType>Current</esb:BalType>\n" +
                    "                    </esb:AcctBal>\n" +
                    "                    <esb:AcctBal>\n" +
                    "                        <esb:CurAmt>\n" +
                    "                            <esb:Amt>00000001423979859</esb:Amt>\n" +
                    "                        </esb:CurAmt>\n" +
                    "                        <esb:BalType>Available</esb:BalType>\n" +
                    "                    </esb:AcctBal>\n" +
                    "                    <esb:AcctBal>\n" +
                    "                        <esb:CurAmt>\n" +
                    "                            <esb:Amt>00000001424122716</esb:Amt>\n" +
                    "                        </esb:CurAmt>\n" +
                    "                        <esb:BalType>Average</esb:BalType>\n" +
                    "                    </esb:AcctBal>\n" +
                    "                    <esb:AcctBal>\n" +
                    "                        <esb:CurAmt>\n" +
                    "                            <esb:Amt>00000000000000000</esb:Amt>\n" +
                    "                        </esb:CurAmt>\n" +
                    "                        <esb:BalType>Hold</esb:BalType>\n" +
                    "                    </esb:AcctBal>\n" +
                    "                    <esb:AcctBal>\n" +
                    "                        <esb:CurAmt>\n" +
                    "                            <esb:Amt>00000001423979859</esb:Amt>\n" +
                    "                        </esb:CurAmt>\n" +
                    "                        <esb:BalType>Yesterday Current</esb:BalType>\n" +
                    "                    </esb:AcctBal>\n" +
                    "                    <esb:AcctBal>\n" +
                    "                        <esb:BalType>Nisbah</esb:BalType>\n" +
                    "                        <esb:CurAmt>\n" +
                    "                            <esb:Amt>00000000000</esb:Amt>\n" +
                    "                        </esb:CurAmt>\n" +
                    "                    </esb:AcctBal>\n" +
                    "                    <esb:AcctBal>\n" +
                    "                        <esb:BalType>TargetAmt</esb:BalType>\n" +
                    "                        <esb:CurAmt>\n" +
                    "                            <esb:Amt>00000000000000000</esb:Amt>\n" +
                    "                        </esb:CurAmt>\n" +
                    "                    </esb:AcctBal>\n" +
                    "                    <esb:CIMB_DepAcctInfo>\n" +
                    "                        <esb:Rate>28000000</esb:Rate>\n" +
                    "                        <esb:CIMB_OverdraftRate>0</esb:CIMB_OverdraftRate>\n" +
                    "                        <esb:DepMatureDt>9999-12-31</esb:DepMatureDt>\n" +
                    "                        <esb:LastTrnDt>9999-12-31</esb:LastTrnDt>\n" +
                    "                        <esb:CIMB_MaturityAmt>0</esb:CIMB_MaturityAmt>\n" +
                    "                        <esb:CIMB_ExtnInfo FieldName=\"Zakat Percentage\">0</esb:CIMB_ExtnInfo>\n" +
                    "                        <esb:CIMB_ExtnInfo FieldName=\"Disposition Account No\">703061909100</esb:CIMB_ExtnInfo>\n" +
                    "                        <esb:CIMB_ExtnInfo FieldName=\"Insurance Account Type\">S</esb:CIMB_ExtnInfo>\n" +
                    "                    </esb:CIMB_DepAcctInfo>\n" +
                    "                    <esb:OpenDt>2020-02-11</esb:OpenDt>\n" +
                    "                    <esb:BankAcctStatus>\n" +
                    "                        <esb:BankAcctStatusCode>9</esb:BankAcctStatusCode>\n" +
                    "                    </esb:BankAcctStatus>\n" +
                    "                    <esb:CIMB_TotalNumber>0</esb:CIMB_TotalNumber>\n" +
                    "                    <esb:ExtAcctBal>\n" +
                    "                        <esb:CurAmt>\n" +
                    "                            <esb:Amt>00000000000000000</esb:Amt>\n" +
                    "                        </esb:CurAmt>\n" +
                    "                        <esb:ExtBalType>ODBalance</esb:ExtBalType>\n" +
                    "                    </esb:ExtAcctBal>\n" +
                    "                    <esb:ExtAcctBal>\n" +
                    "                        <esb:CurAmt>\n" +
                    "                            <esb:Amt>00000000000000000</esb:Amt>\n" +
                    "                        </esb:CurAmt>\n" +
                    "                        <esb:ExtBalType>ODLimit</esb:ExtBalType>\n" +
                    "                    </esb:ExtAcctBal>\n" +
                    "                    <esb:ExtAcctBal>\n" +
                    "                        <esb:CurAmt>\n" +
                    "                            <esb:Amt>0000000001601403300</esb:Amt>\n" +
                    "                        </esb:CurAmt>\n" +
                    "                        <esb:ExtBalType>AccInt</esb:ExtBalType>\n" +
                    "                    </esb:ExtAcctBal>\n" +
                    "                    <esb:ExtAcctBal>\n" +
                    "                        <esb:ExtBalType>InstAmt</esb:ExtBalType>\n" +
                    "                        <esb:CurAmt>\n" +
                    "                            <esb:Amt>00000000000000000</esb:Amt>\n" +
                    "                        </esb:CurAmt>\n" +
                    "                    </esb:ExtAcctBal>\n" +
                    "                    <esb:ExtAcctBal>\n" +
                    "                        <esb:ExtBalType>OneDayFlt</esb:ExtBalType>\n" +
                    "                        <esb:CurAmt>\n" +
                    "                            <esb:Amt>00000000000000000</esb:Amt>\n" +
                    "                        </esb:CurAmt>\n" +
                    "                    </esb:ExtAcctBal>\n" +
                    "                    <esb:ExtAcctBal>\n" +
                    "                        <esb:ExtBalType>TwoDayFlt</esb:ExtBalType>\n" +
                    "                        <esb:CurAmt>\n" +
                    "                            <esb:Amt>00000000000000000</esb:Amt>\n" +
                    "                        </esb:CurAmt>\n" +
                    "                    </esb:ExtAcctBal>\n" +
                    "                    <esb:ExtAcctBal>\n" +
                    "                        <esb:ExtBalType>ThreeDayFlt</esb:ExtBalType>\n" +
                    "                        <esb:CurAmt>\n" +
                    "                            <esb:Amt>00000000000000000</esb:Amt>\n" +
                    "                        </esb:CurAmt>\n" +
                    "                    </esb:ExtAcctBal>\n" +
                    "                    <esb:ExtAcctBal>\n" +
                    "                        <esb:ExtBalType>MinimumBal</esb:ExtBalType>\n" +
                    "                        <esb:CurAmt>\n" +
                    "                            <esb:Amt>00000000005000000</esb:Amt>\n" +
                    "                        </esb:CurAmt>\n" +
                    "                    </esb:ExtAcctBal>\n" +
                    "                    <esb:ExtAcctBal>\n" +
                    "                        <esb:ExtBalType>TotalCollectionAmt</esb:ExtBalType>\n" +
                    "                        <esb:CurAmt>\n" +
                    "                            <esb:Amt>00000000000000000</esb:Amt>\n" +
                    "                        </esb:CurAmt>\n" +
                    "                    </esb:ExtAcctBal>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"PassBook\">N</esb:CIMB_ExtnInfo>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Facility Record\">N</esb:CIMB_ExtnInfo>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Next Deposit Date\">9999-12-31</esb:CIMB_ExtnInfo>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Deliquent Date\">9999-12-31</esb:CIMB_ExtnInfo>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Last Transaction Teller ID\">DD4400</esb:CIMB_ExtnInfo>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Last Transaction Teller ID Desc\">RM29091X</esb:CIMB_ExtnInfo>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Last Transaction Source\"/>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Last Transaction Code\">431</esb:CIMB_ExtnInfo>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Last Transaction D/C Code\">D</esb:CIMB_ExtnInfo>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Last Transaction Amount\">500000</esb:CIMB_ExtnInfo>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Program Description\"/>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"SoF Flag\">N</esb:CIMB_ExtnInfo>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Investor Account Flag\"/>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Insurance Coverage\"/>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Schedule Disbursement 1\">0</esb:CIMB_ExtnInfo>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Schedule Disbursement 2\">0</esb:CIMB_ExtnInfo>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Schedule Disbursement 3\">0</esb:CIMB_ExtnInfo>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Schedule Disbursement 4\">0</esb:CIMB_ExtnInfo>\n" +
                    "                    <esb:CIMB_ExtnInfo FieldName=\"Schedule Disbursement 5\">0</esb:CIMB_ExtnInfo>\n" +
                    "                    <esb:CIMB_LoanAcctInfo>\n" +
                    "                        <esb:CIMB_MaturityDt>0</esb:CIMB_MaturityDt>\n" +
                    "                        <esb:CIMB_AppType/>\n" +
                    "                    </esb:CIMB_LoanAcctInfo>\n" +
                    "                    <esb:CIMB_Term>0</esb:CIMB_Term>\n" +
                    "                    <esb:CustInfo>\n" +
                    "                        <esb:PersonInfo>\n" +
                    "                            <esb:FullName/>\n" +
                    "                        </esb:PersonInfo>\n" +
                    "                    </esb:CustInfo>\n" +
                    "                    <esb:CustInfo>\n" +
                    "                        <esb:PersonInfo>\n" +
                    "                            <esb:BirthDt>9999-12-31</esb:BirthDt>\n" +
                    "                        </esb:PersonInfo>\n" +
                    "                    </esb:CustInfo>\n" +
                    "                    <esb:AcctType/>\n" +
                    "                    <esb:CIMB_ProgramCode/>\n" +
                    "                    <esb:CIMB_Flag FieldName=\"Transaction Not Allowed\"/>\n" +
                    "                    <esb:CIMB_Flag FieldName=\"Exclude Channel Inquiry\">N</esb:CIMB_Flag>\n" +
                    "                    <esb:CIMB_Flag FieldName=\"Islamic Contract\"/>\n" +
                    "                    <esb:CIMB_Flag FieldName=\"Zakat Flag\">N</esb:CIMB_Flag>\n" +
                    "                    <esb:CIMB_ProductType/>\n" +
                    "                    <esb:CIMB_ConvIslamic>N</esb:CIMB_ConvIslamic>\n" +
                    "                    <esb:InitialDeposit>\n" +
                    "                        <esb:Amt>00000001313000000</esb:Amt>\n" +
                    "                    </esb:InitialDeposit>\n" +
                    "                </esb:CIMB_AcctInfo>\n" +
                    "                <esb:CIMB_AcctInfo>\n" +
                    "                    <esb:OpenDt>9999-12-31</esb:OpenDt>\n" +
                    "                    <esb:AcctId>0</esb:AcctId>\n" +
                    "                    <esb:CIMB_TotalNumber>0</esb:CIMB_TotalNumber>\n" +
                    "                </esb:CIMB_AcctInfo>\n" +
                    "                <esb:CIMB_LoanAcct>\n" +
                    "                    <esb:LoanAcctId>\n" +
                    "                        <esb:AcctType/>\n" +
                    "                    </esb:LoanAcctId>\n" +
                    "                </esb:CIMB_LoanAcct>\n" +
                    "            </esb:CIMB_AcctInqRs>\n" +
                    "        </esb:CIMB_AcctInqSvc>\n" +
                    "    </soapenv:Body>\n" +
                    "</soapenv:Envelope>";


    @Test
    public void validateESBResponseTestSuccess() {
        CIMBProvider cimbProvider = mock(CIMBProvider.class);
        esbService.validateESBResponse(Status.builder().statusCode(0).build(), CIMBProvider.builder().build());
        verify(cimbProvider, times(0)).getCimbProviderRespStatusList();
    }

    @Test(expected = MobileRestException.class)
    public void validateESBResponseTestProviderError() {

        esbService.validateESBResponse(
                Status.builder().statusCode(9999).statusDesc("error").build(),
                CIMBProvider.builder()
                        .cimbProviderSystemCode(SourceSystem.FDS)
                        .cimbProviderRespStatusList(CIMBProviderRespStatusList.builder()
                                .cimbProviderRespStatus(CIMBProviderRespStatus.builder()
                                        .cimbStatusCode("1")
                                        .build())
                                .build())
                        .build()
        );

    }

    @Test(expected = MobileRestException.class)
    public void validateESBResponseTestProviderError2() {

        esbService.validateESBResponse(
                Status.builder().statusCode(9998).statusDesc("error").build(),
                CIMBProvider.builder()
                        .cimbProviderSystemCode(SourceSystem.FDS)
                        .cimbProviderRespStatusList(CIMBProviderRespStatusList.builder()
                                .cimbProviderRespStatus(CIMBProviderRespStatus.builder()
                                        .cimbStatusCode("1")
                                        .build())
                                .build())
                        .build()
        );

    }

    @Test(expected = MobileRestException.class)
    public void validateESBResponseTestESBError() {
        esbService.validateESBResponse(
                Status.builder().statusCode(1).statusDesc("error").build(),
                CIMBProvider.builder().build()
        );
    }

}
