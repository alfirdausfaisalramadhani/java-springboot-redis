package id.co.ist.mobile.servicename.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.ist.mobile.common.util.MapperUtil;
import id.co.ist.mobile.common.util.StringUtil;
import id.co.ist.mobile.servicename.domain.dao.CatOwner;
import id.co.ist.mobile.servicename.domain.dto.external.CatFactDto;
import id.co.ist.mobile.servicename.domain.dto.internal.CatOwnerDto;
import id.co.ist.mobile.servicename.domain.dto.test.RequestInquiryDTO;
import id.co.ist.mobile.servicename.domain.dto.test.RequestInquiryModel;
import id.co.ist.mobile.servicename.domain.dto.test.ResponseInquiry;
import id.co.ist.mobile.servicename.repository.CatOwnerRepository;
import id.co.ist.mobile.servicename.repository.TransactionDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
public class CatService {
    @Autowired
    private CatOwnerRepository catOwnerRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TransactionDao transactionDao;


    public CatFactDto getCatFact() {
        return restTemplate.getForEntity("https://catfact.ninja/fact", CatFactDto.class)
                .getBody();
    }

    public CatOwnerDto createCatOwner(CatOwnerDto catOwnerDto) {
        CatOwner owner = catOwnerRepository.save((CatOwner) MapperUtil.transform(catOwnerDto, new CatOwner(), true));
        catOwnerDto.setId(owner.getId());
        return catOwnerDto;
    }

    public Optional<CatOwnerDto> updateCatOwner(CatOwnerDto catOwnerDto) {
        Optional<CatOwner> ownerInDb = catOwnerRepository.findById(catOwnerDto.getId());

        if (ownerInDb.isEmpty()) {
            return Optional.empty();
        }
        CatOwner ownerToUpdate = catOwnerRepository.save((CatOwner) MapperUtil.transform(catOwnerDto, new CatOwner(), true));
        CatOwnerDto updatedCatOwnerData = (CatOwnerDto) MapperUtil.transform(ownerToUpdate, new CatOwnerDto(), true);

        return Optional.of(updatedCatOwnerData);
    }

    public Optional<CatOwnerDto> deleteCatOwner(Long id) {
        Optional<CatOwner> catOwner = catOwnerRepository.findById(id);
        if (catOwner.isEmpty()) {
            return Optional.empty();
        }
        catOwnerRepository.deleteById(id);
        return Optional.of((CatOwnerDto) MapperUtil.transform(catOwner, new CatOwnerDto(), true));
    }

    //===========================================================================================================================================//
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
            transactionDao.save(payload);

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
            payload = transactionDao.findAll();
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
            String res = transactionDao.findById(responseInquiry.getTransactionId());
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
            String res = transactionDao.findById(responseInquiry.getTransactionId());
            log.info("DATA : {}", res);
            if (res.equals("") || res == null){
                ret = "DATA KOSONG";
            } else {
                transactionDao.deleteById(responseInquiry.getTransactionId());
                ret = "DATA SUKSES TERHAPUS";
            }
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    //===========================================================================================================================================//
}
