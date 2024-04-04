package id.co.ist.mobile.servicename.controller;


import id.co.ist.mobile.common.constant.ApiVersion;
import id.co.ist.mobile.common.constant.GenericResponseKey;
import id.co.ist.mobile.common.domain.ApiResponse;
import id.co.ist.mobile.common.util.ResponseUtil;
import id.co.ist.mobile.servicename.domain.dto.internal.AcctDetailRq;
import id.co.ist.mobile.servicename.service.ESBService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * this is a controller class only for testing connection pool with soap connector,
 * run the app and hit from swagger for test the connection pool
 * to set the timeout and connection pool see class:
 * - ConnectionPoolConfiguration
 * - SoapConfig
 * - SoapConnector
 * - ESBService
 */
@Slf4j
@RestController
@RequestMapping(value = "/esb", produces = MediaType.APPLICATION_JSON_VALUE, consumes = ApiVersion.V1)
public class ESBTestController {

    @Autowired
    private ESBService esbService;

    @Operation(summary = "test hit to esb")
    @PostMapping("/test")
    public ResponseEntity<Object> testCallToEsb(@RequestBody AcctDetailRq req) {
        return ResponseUtil.buildResponse(GenericResponseKey.SUCCESS.getResponseKey(),
                ApiResponse.Message.builder().build(),
                esbService.getAccountInquiryDetail(req.getAccountNumber(), req.getAccountType()),
                HttpStatus.OK
        );
    }

}
