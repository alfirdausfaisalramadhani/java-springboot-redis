package id.co.ist.mobile.servicename.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.ist.mobile.servicename.domain.dto.test.RequestInquiryDTO;
import id.co.ist.mobile.servicename.domain.dto.test.RequestInquiryModel;
import id.co.ist.mobile.servicename.domain.dto.test.ResponseInquiry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Component
public class TransactionDao {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public static final String hashKey = "testing";

    public RequestInquiryDTO save (RequestInquiryDTO requestInquiryDTO) {
        String json = "";

        try {
            json = objectMapper.writeValueAsString(requestInquiryDTO);
            redisTemplate.opsForHash().put(hashKey, requestInquiryDTO.getTransactionId().toString(), json);
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            log.info("JSON TO REDIS : {}", json);
        }
        return requestInquiryDTO;
    }

    public List<String> findAll(){
        List<String> payloadString = new ArrayList<>();

        try {
            payloadString = redisTemplate.opsForHash().values(hashKey);
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return payloadString;
    }

    public String findById(String id){
        String ret = "";
        try {
            ret = (String) redisTemplate.opsForHash().get(hashKey, id);
            if (ret.equals("") || ret == null){
                ret = "";
            }
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    public String deleteById(String id){
        String ret = "";
        try {
            ret = String.valueOf(redisTemplate.opsForHash().delete(hashKey, id));
            if (ret.equals("") || ret == null){
                ret = "";
            }
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }
}
