package id.co.ist.mobile.servicename.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.ist.mobile.servicename.domain.dto.transfer.RequestInquiryDTO;
import id.co.ist.mobile.servicename.domain.dto.transfer.RequestInquiryModel;
import id.co.ist.mobile.servicename.domain.dto.transfer.ResponseInquiry;
import id.co.ist.mobile.servicename.repository.TransferClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TransferService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TransferClassRepository transferClassRepository;

    public String inquiry(RequestInquiryModel requestInquiryModel) {
        String ret = "";
        ResponseInquiry responseInquiry = new ResponseInquiry();
        RequestInquiryDTO payload = new RequestInquiryDTO();

        try {
            String transactionId = UUID.randomUUID().toString();
            payload.setTransactionId(transactionId);
            payload.setSourceAccountNumber(requestInquiryModel.getSourceAccountNumber());
            payload.setSourceAccountName(requestInquiryModel.getSourceAccountName());
            payload.setDestinationAccountNumber(requestInquiryModel.getDestinationAccountNumber());
            payload.setDestinationAccountName(requestInquiryModel.getDestinationAccountName());
            payload.setAmount(requestInquiryModel.getAmount());
            transferClassRepository.save(payload);

            responseInquiry.setTransactionId(transactionId);
            ret = objectMapper.writeValueAsString(responseInquiry);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    public List<String> findAll() {
        List<String> payload = new ArrayList<>();

        try {
            payload = transferClassRepository.findAll();
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return payload;
    }

    public String payment(ResponseInquiry responseInquiry) {
        String ret = "";
        RequestInquiryModel requestInquiryModel = new RequestInquiryModel();

        try {
            String res = transferClassRepository.findById(responseInquiry.getTransactionId());
            log.info("DATA : {}", res);
            if (res.equals("") || res == null){
                return ret;
            }

            RequestInquiryDTO model = objectMapper.readValue(res, RequestInquiryDTO.class);
            log.info("MODEL : {}", objectMapper.writeValueAsString(model));
            requestInquiryModel.setSourceAccountNumber(model.getSourceAccountNumber());
            requestInquiryModel.setSourceAccountName(model.getSourceAccountName());
            requestInquiryModel.setDestinationAccountNumber(model.getDestinationAccountNumber());
            requestInquiryModel.setDestinationAccountName(model.getDestinationAccountName());
            requestInquiryModel.setAmount(model.getAmount());

            ret = objectMapper.writeValueAsString(requestInquiryModel);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            log.info("RESPONSE : {}", ret);
        }
        return ret;
    }

    public String delete(ResponseInquiry responseInquiry) {
        String ret = "";

        try {
            String res = transferClassRepository.findById(responseInquiry.getTransactionId());
            log.info("DATA : {}", res);
            if (res.equals("") || res == null){
                ret = "DATA KOSONG";
            } else {
                transferClassRepository.deleteById(responseInquiry.getTransactionId());
                ret = "DATA SUKSES TERHAPUS";
            }
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }
}
