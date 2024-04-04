package id.co.ist.mobile.servicename.controller;

import id.co.ist.mobile.common.constant.ApiVersion;
import id.co.ist.mobile.common.constant.GenericResponseKey;
import id.co.ist.mobile.common.domain.ApiResponse;
import id.co.ist.mobile.common.util.ResponseUtil;
import id.co.ist.mobile.servicename.constant.ServiceResponseKey;
import id.co.ist.mobile.servicename.domain.dto.internal.GreetingDetailRequest;
import id.co.ist.mobile.servicename.domain.dto.internal.GreetingRequest;
import id.co.ist.mobile.servicename.domain.dto.internal.GreetingResponse;
import id.co.ist.mobile.servicename.service.GreetingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Controller
@RequestMapping(value = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE, consumes = ApiVersion.V1)
public class GreetingController {

    @Autowired
    private GreetingService greetingService;

    @Operation(summary = "greet all cat owner with filter (1 or more data)")
    @ResponseBody
    @PostMapping(value = "/")
    public ResponseEntity<Object> greetAllCatOwner(@RequestBody GreetingRequest requestBody, HttpServletRequest request, HttpServletResponse response) {
        List<String> greetingList = greetingService.greetAllCatOwner(requestBody);
        return ResponseUtil.buildResponse(
                GenericResponseKey.SUCCESS.getResponseKey(),
                new ApiResponse.Message("", GenericResponseKey.SUCCESS.getResponseKey(), "", GenericResponseKey.SUCCESS.getResponseKey()), new GreetingResponse(greetingList),
                HttpStatus.OK);
    }

    @Operation(summary = "greet cat owner (1 data only)")
    @ResponseBody
    @PostMapping(value = "/detail")
    public ResponseEntity<Object> greetCatOwner(@RequestBody GreetingDetailRequest requestBody, HttpServletRequest request, HttpServletResponse response) {
        if (Objects.isNull(requestBody.getId())) {
            return ResponseUtil.buildResponse(
                    ServiceResponseKey.PARAMETER_INVALID,
                    new ApiResponse.Message(),
                    null,
                    HttpStatus.BAD_REQUEST);
        }

        Optional<String> greetOwner = greetingService.greetCatOwner(requestBody.getId());
        if (greetOwner.isEmpty()) {
            return ResponseUtil.buildResponse(
                    ServiceResponseKey.NOT_FOUND,
                    new ApiResponse.Message(),
                    null,
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseUtil.buildResponse(
                GenericResponseKey.SUCCESS.getResponseKey(),
                new ApiResponse.Message("", GenericResponseKey.SUCCESS.getResponseKey(), "", GenericResponseKey.SUCCESS.getResponseKey()), greetOwner.get(),
                HttpStatus.OK);

    }


}
