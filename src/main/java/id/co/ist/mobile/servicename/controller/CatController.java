package id.co.ist.mobile.servicename.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.ist.mobile.common.constant.GenericResponseKey;
import id.co.ist.mobile.common.domain.ApiResponse;
import id.co.ist.mobile.common.util.ResponseUtil;
import id.co.ist.mobile.common.util.StringUtil;
import id.co.ist.mobile.servicename.constant.ServiceResponseKey;
import id.co.ist.mobile.servicename.domain.dto.internal.CatOwnerDto;
import id.co.ist.mobile.servicename.domain.dto.test.RequestInquiryDTO;
import id.co.ist.mobile.servicename.domain.dto.test.RequestInquiryModel;
import id.co.ist.mobile.servicename.domain.dto.test.ResponseInquiry;
import id.co.ist.mobile.servicename.service.CatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
@RestController
//@RequestMapping(value = "/cat", produces = MediaType.APPLICATION_JSON_VALUE, consumes = ApiVersion.V1)
@RequestMapping(value = "/cat", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatController {

    @Autowired
    private CatService catService;

    @Autowired
    private ObjectMapper objectMapper;

    @Operation(
            summary = "get cat fact",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = {@Content(examples =
                            {@ExampleObject(value = "{}")}
                    )}
            )
    )
    /**
     * NOTES: for any controller that don't have request body and wanted to documented
     * automatically using springfox 3.0.0 can follow this pattern
     * 1. add annotation @Operation with content argument (take a look at above @Operation block of code)
     * 2. In controller's parameters there have to be 1 param that given annotation @RequestBody
     */

    //===========================================================================================================================================//

    @ResponseBody
    @PostMapping(value="/test/inquiry")
    public ResponseEntity<Object> inquiry(@RequestBody String jsonReq) throws JsonProcessingException {
        log.info("INCOMING JSON ORI : {}", jsonReq);

        String ret = "";
        RequestInquiryModel requestInquiryModel = objectMapper.readValue(jsonReq, RequestInquiryModel.class);

        try {
            if (requestInquiryModel.getSourceAccountNumber().equals("") || requestInquiryModel.getSourceAccountNumber() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SOURCE ACCOUNT NUMBER TIDAK BOLEH NULL ATAU STRING KOSONG");
            }

            if (requestInquiryModel.getSourceAccountName().equals("") || requestInquiryModel.getSourceAccountName() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SOURCE ACCOUNT NAME TIDAK BOLEH NULL ATAU STRING KOSONG");
            }

            if (requestInquiryModel.getDestinationAccountNumber().equals("") || requestInquiryModel.getDestinationAccountNumber() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DESTINATION ACCOUNT NUMBER TIDAK BOLEH NULL ATAU STRING KOSONG");
            }

            if (requestInquiryModel.getDestinationAccountName().equals("") || requestInquiryModel.getDestinationAccountName() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DESTINATION ACCOUNT NAME TIDAK BOLEH NULL ATAU STRING KOSONG");
            }

            if (requestInquiryModel.getAmount().intValue() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("AMOUNT HARUS LEBIH DARI 0");
            }

            ret = catService.inquiry(requestInquiryModel);
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR");
        } finally {
            log.info("REQUEST JSON  : {}", objectMapper.writeValueAsString(requestInquiryModel));
            log.info("RESPONSE JSON : {}", ret);
        }
        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    @GetMapping("/test/getAll")
    public ResponseEntity<Object> findAll() throws JsonProcessingException {
        List<RequestInquiryDTO> ret = new ArrayList<>();

        try {
            List<String> jsonStringList = catService.findAll();

            for (String jsonString : jsonStringList) {
                RequestInquiryDTO requestInquiryDTO = objectMapper.readValue(jsonString, RequestInquiryDTO.class);
                ret.add(requestInquiryDTO);
            }
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR");
        } finally {
            log.info("DATA : {}", objectMapper.writeValueAsString(ret));
        }
        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    @ResponseBody
    @PostMapping(value="/test/payment")
    public ResponseEntity<Object> payment(@RequestBody String jsonReq) throws JsonProcessingException {
        log.info("INCOMING JSON : {}", jsonReq);

        String ret = "";
        ResponseInquiry responseInquiry = objectMapper.readValue(jsonReq, ResponseInquiry.class);

        try {
            if (responseInquiry.getTransactionId().equals("") || responseInquiry.getTransactionId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TRANSACTION ID TIDAK BOLEH NULL ATAU STRING KOSONG");
            }

            ret = catService.payment(responseInquiry);
            if (ret.equals("") || ret == null){
                return ResponseEntity.status(HttpStatus.OK).body("DATA TIDAK DITEMUKAN");
            }
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR");
        } finally {
            log.info("REQUEST JSON  : {}", objectMapper.writeValueAsString(responseInquiry));
            log.info("RESPONSE JSON : {}", ret);
        }
        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    @ResponseBody
    @PostMapping(value="/test/delete")
    public ResponseEntity<Object> delete(@RequestBody String jsonReq) throws JsonProcessingException {
        log.info("INCOMING JSON : {}", jsonReq);

        String ret = "";
        ResponseInquiry responseInquiry = objectMapper.readValue(jsonReq, ResponseInquiry.class);

        try {
            ret = catService.delete(responseInquiry);
            if (ret.equals("") || ret == null){
                return ResponseEntity.status(HttpStatus.OK).body("DATA TIDAK DITEMUKAN");
            }
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL SERVER ERROR");
        } finally {
            log.info("REQUEST JSON  : {}", objectMapper.writeValueAsString(responseInquiry));
            log.info("RESPONSE JSON : {}", ret);
        }
        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    //===========================================================================================================================================//

    @ResponseBody
    @PostMapping(value = "/fact/")
    public ResponseEntity<Object> getCatFact(@RequestBody Object o, HttpServletRequest request, HttpServletResponse response) {
        return ResponseUtil.buildResponse(
                GenericResponseKey.SUCCESS.getResponseKey(),
                new ApiResponse.Message("", GenericResponseKey.SUCCESS.getResponseKey(), "", GenericResponseKey.SUCCESS.getResponseKey()),
                catService.getCatFact(),
                HttpStatus.OK);
    }

    @Operation(summary = "save cat owner data")
    @ResponseBody
    @PostMapping(value = "/owner/create")
    public ResponseEntity<Object> createCatOwnerData(@RequestBody CatOwnerDto requestBody, HttpServletRequest request, HttpServletResponse response) {
        if (Objects.isNull(requestBody.getFirstName()) && Objects.isNull(requestBody.getLastName()) && Objects.isNull(requestBody.getGender())) {
            return ResponseUtil.buildResponse(
                    ServiceResponseKey.PARAMETER_INVALID,
                    new ApiResponse.Message(),
                    null,
                    HttpStatus.BAD_REQUEST);
        }

        return ResponseUtil.buildResponse(
                GenericResponseKey.SUCCESS.getResponseKey(),
                new ApiResponse.Message("", GenericResponseKey.SUCCESS.getResponseKey(), "", GenericResponseKey.SUCCESS.getResponseKey()), catService.createCatOwner(requestBody),
                HttpStatus.CREATED);
    }

    @Operation(summary = "save cat owner data")
    @ResponseBody
    @PostMapping(value = "/owner/update")
    public ResponseEntity<Object> updateCatOwnerData(@RequestBody CatOwnerDto requestBody, HttpServletRequest request, HttpServletResponse response) {
        if (Objects.isNull(requestBody.getId()) ||
                (Objects.isNull(requestBody.getFirstName()) && Objects.isNull(requestBody.getLastName()) && Objects.isNull(requestBody.getGender()))) {
            return ResponseUtil.buildResponse(
                    ServiceResponseKey.PARAMETER_INVALID,
                    new ApiResponse.Message(),
                    null,
                    HttpStatus.BAD_REQUEST);

        }
        Optional<CatOwnerDto> catOwnerUpdated = catService.updateCatOwner(requestBody);
        if (catOwnerUpdated.isEmpty()) {
            return ResponseUtil.buildResponse(
                    ServiceResponseKey.NOT_FOUND,
                    new ApiResponse.Message(),
                    null,
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseUtil.buildResponse(
                GenericResponseKey.SUCCESS.getResponseKey(),
                new ApiResponse.Message("", GenericResponseKey.SUCCESS.getResponseKey(), "", GenericResponseKey.SUCCESS.getResponseKey()), catOwnerUpdated.get(),
                HttpStatus.OK);
    }


    @Operation(summary = "delete cat owner data")
    @ResponseBody
    @PostMapping(value = "/owner/delete")
    public ResponseEntity<Object> deleteCatOwnerData(@RequestBody CatOwnerDto requestBody, HttpServletRequest request, HttpServletResponse response) {
        if (Objects.isNull(requestBody.getId())) {
            return ResponseUtil.buildResponse(
                    ServiceResponseKey.PARAMETER_INVALID,
                    new ApiResponse.Message(),
                    null,
                    HttpStatus.BAD_REQUEST);
        }
        Optional<CatOwnerDto> deletedCatOwner = catService.deleteCatOwner(requestBody.getId());
        if (deletedCatOwner.isEmpty()) {
            return ResponseUtil.buildResponse(
                    ServiceResponseKey.NOT_FOUND,
                    new ApiResponse.Message(),
                    null,
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseUtil.buildResponse(
                GenericResponseKey.SUCCESS.getResponseKey(),
                new ApiResponse.Message("", GenericResponseKey.SUCCESS.getResponseKey(), "", GenericResponseKey.SUCCESS.getResponseKey()), deletedCatOwner.get(),
                HttpStatus.OK);
    }
}
