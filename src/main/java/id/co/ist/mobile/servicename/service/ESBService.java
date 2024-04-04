package id.co.ist.mobile.servicename.service;


import id.co.ist.mobile.common.constant.SourceSystem;
import id.co.ist.mobile.common.domain.ApiResponse;
import id.co.ist.mobile.common.exception.MobileRestException;
import id.co.ist.mobile.servicename.config.SOAPConnector;
import id.co.ist.mobile.servicename.domain.dto.common.esb.body.Status;
import id.co.ist.mobile.servicename.domain.dto.common.esb.header.CIMBProvider;
import id.co.ist.mobile.servicename.domain.dto.common.esb.header.CIMBProviderList;
import id.co.ist.mobile.servicename.domain.dto.common.esb.header.CIMBProviderRespStatus;
import id.co.ist.mobile.servicename.domain.dto.common.esb.header.ClientApp;
import id.co.ist.mobile.servicename.domain.dto.common.esb.header.request.CIMBHeaderRq;
import id.co.ist.mobile.servicename.domain.dto.common.esb.header.request.CIMBSignonRq;
import id.co.ist.mobile.servicename.domain.dto.external.accountdetailinquiry.CIMBAcctInqSvc;
import id.co.ist.mobile.servicename.domain.dto.external.accountdetailinquiry.request.CIMBAcctInqRq;
import id.co.ist.mobile.servicename.domain.dto.external.accountdetailinquiry.request.CIMBNonFinSvcHeaderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Service
@Slf4j
public class ESBService {


    /**
     * this is autowiring to soapConnector for non transactional call to esb,
     * for transactional call to esb, you have to create different bean
     * with annotation @Qualifier that have value "soapConnectorTransactional"
     * example:
     *
     * @Qualifier("soapConnectorTransactional")
     * @Autowired private SOAPConnector soapConnectorTransactional;
     */
    @Autowired
    private SOAPConnector soapConnector;
    @Value("${esb.client.name}")
    private String clientAppName;
    @Value("${esb.client.version}")
    private String clientAppVersion;
    @Value("${esb.header.consumerid}")
    private String headerConsumerId;
    @Value("${esb.header.sourcesystem}")
    private String headerSourceSystem;
    @Value("${esb.accountDetailInquiry.url}")
    private String esbAccountDetailInquiryURL;
    @Value("${esb.header.password}")
    private String headerConsumerPass;
    @Value("${esb.header.accountDetailInquiry.serviceName}")
    private String accountDetailInquiryServiceName;
    @Value("${esb.header.accountDetailInquiry.serviceVersion}")
    private String accountDetailInquiryServiceVersion;
    @Value("${esb.client.org}")
    private String clientAppOrg;
    @Value("${esb.custlangpref}")
    private String custlangpref;
    @Value("${esb.bankid}")
    private String bankId;
    @Value("${esb.branchid}")
    private String branchId;
    @Value("${esb.header.sourcecountrycode}")
    private String sourceCountryCode;

    private SimpleDateFormat formatterRequest = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSSSS");

    public CIMBAcctInqSvc getAccountInquiryDetail(String accountNumber, String accountType) {
        CIMBAcctInqSvc requestObject = populateRequestForGetAccountDetailInquiry(accountNumber, accountType);
        log.info("call {} with request {}", accountDetailInquiryServiceName.concat(accountDetailInquiryServiceVersion), requestObject);
        CIMBAcctInqSvc responseObject = null;
        try {
            responseObject = (CIMBAcctInqSvc) soapConnector.callWebService(esbAccountDetailInquiryURL, requestObject);
            log.info("cat to {} got response {}", accountDetailInquiryServiceName.concat(accountDetailInquiryServiceVersion), responseObject);
            this.validateESBResponse(
                    responseObject.getCimbAcctInqRs().getStatus(),
                    responseObject.getCimbSignonRs().getCimbHeaderRs().getCimbProviderList().getCimbProvider()
            );
            return responseObject;
        } catch (Exception ex) {
            log.error("ERROR WHILE RETRIEVING DATA FROM ESB >> ", ex);
            log.error(ex.getMessage());
            throw ex;
        }
    }

    private String getRandomUid() {
        int numOfDigits = 10;
        StringBuilder min = new StringBuilder("0");
        StringBuilder max = new StringBuilder("9");

        while (numOfDigits > 1) {
            min.append(0);
            max.append(0);
            numOfDigits--;
        }

        return Long.toString((long) Math.floor(Math.random() * Long.parseLong(max.toString()) + Long.parseLong(min.toString())));
    }

    private CIMBAcctInqSvc populateRequestForGetAccountDetailInquiry(String accountNumber, String accountType) {

        CIMBProvider cimbProvider = new CIMBProvider();
        cimbProvider.setCimbProviderRqUID(getRandomUid());
        cimbProvider.setCimbProviderSystemCode(SourceSystem.FDS);

        CIMBProviderList cimbProviderList = new CIMBProviderList();
        cimbProviderList.setCimbProvider(cimbProvider);

        /* generate CIMB Header Request */
        CIMBHeaderRq cimbHeaderRq = new CIMBHeaderRq();
        cimbHeaderRq.setCimbConsumerId(headerConsumerId);
        cimbHeaderRq.setCimbConsumerPasswd(headerConsumerPass);
        cimbHeaderRq.setCimbServiceName(accountDetailInquiryServiceName);
        cimbHeaderRq.setCimbServiceVersion(accountDetailInquiryServiceVersion);
        cimbHeaderRq.setCimbSrcSystem(headerSourceSystem);
        cimbHeaderRq.setCimbProviderList(cimbProviderList);
        cimbHeaderRq.setCimbSrcCountryCode(sourceCountryCode);

        ClientApp clientApp = new ClientApp();
        clientApp.setOrg(clientAppOrg);
        clientApp.setName(clientAppName);
        clientApp.setVersion(clientAppVersion);

        /* generate CIMB Signon Request */
        CIMBSignonRq cimbSignonRq = new CIMBSignonRq();
        cimbSignonRq.setClientDt(formatterRequest.format(new Date()));
        cimbSignonRq.setClientApp(clientApp);
        cimbSignonRq.setCustLangPref(custlangpref);
        cimbSignonRq.setCimbHeaderRq(cimbHeaderRq);

        /* generate CIMB Account Inquiry Service */
        CIMBAcctInqSvc cimbAcctInqSvc = new CIMBAcctInqSvc();
        cimbAcctInqSvc.setCimbAcctInqRq(generateCIMBAcctInqRq(accountNumber, accountType));
        cimbAcctInqSvc.setCimbSignonRq(cimbSignonRq);

        return cimbAcctInqSvc;
    }

    private CIMBAcctInqRq generateCIMBAcctInqRq(String accountNumber, String accountType) {
        CIMBAcctInqRq cimbAcctInqRq = new CIMBAcctInqRq();
        String uuidStr = UUID.randomUUID().toString();

        cimbAcctInqRq.setCimbRqUID(uuidStr);
        cimbAcctInqRq.setAcctId(Long.valueOf(accountNumber));
        cimbAcctInqRq.setAcctType(accountType);

        /* Header Info '/CIMB_AcctInqSvc/CIMB_AcctInqRq/CIMB_NonFinSvcHeaderInfo' */
        CIMBNonFinSvcHeaderInfo cimbNonFinSvcHeaderInfo = new CIMBNonFinSvcHeaderInfo();
        cimbNonFinSvcHeaderInfo.setBankId(bankId);
        cimbNonFinSvcHeaderInfo.setBranchId(Integer.valueOf(branchId));

        cimbAcctInqRq.setCimbNonFinSvcHeaderInfo(cimbNonFinSvcHeaderInfo);

        return cimbAcctInqRq;
    }

    public void validateESBResponse(Status esbResponseStatus, CIMBProvider esbProvider) {
        int esbStatusCode = esbResponseStatus.getStatusCode();

        if (0 != esbStatusCode) {
            //Throws error
            log.error("Got ESB Error response : {}", esbResponseStatus);
            if (9999 == esbStatusCode || 9998 == esbStatusCode) {
                CIMBProviderRespStatus providerRespStatus = esbProvider.getCimbProviderRespStatusList().getCimbProviderRespStatus();
                log.error("Got error from ESB provider: {}", providerRespStatus);
                throw new MobileRestException(ApiResponse.builder()
                        .message(ApiResponse.Message.builder().build())
                        .sourceSystem(esbProvider.getCimbProviderSystemCode())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                throw new MobileRestException(ApiResponse.builder()
                        .message(ApiResponse.Message.builder().build())
                        .sourceSystem(SourceSystem.FDS)
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }
}
