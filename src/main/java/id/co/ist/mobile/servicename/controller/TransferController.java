package id.co.ist.mobile.servicename.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.ist.mobile.servicename.domain.dto.transfer.RequestInquiryDTO;
import id.co.ist.mobile.servicename.domain.dto.transfer.RequestInquiryModel;
import id.co.ist.mobile.servicename.domain.dto.transfer.ResponseInquiry;
import id.co.ist.mobile.servicename.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RestController
@RequestMapping(value = "/cat", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransferController {

    @Autowired
    private TransferService transferService;

    @Autowired
    private ObjectMapper objectMapper;

    @ResponseBody
    @PostMapping(value="/test/inquiry")
    public ResponseEntity<Object> inquiry(@RequestBody String jsonReq) throws JsonProcessingException {
        log.info("INCOMING JSON : {}", jsonReq);

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

            ret = transferService.inquiry(requestInquiryModel);
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
            List<String> jsonStringList = transferService.findAll();

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

            ret = transferService.payment(responseInquiry);
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
            ret = transferService.delete(responseInquiry);
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
}
