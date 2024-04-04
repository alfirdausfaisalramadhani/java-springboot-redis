package id.co.ist.mobile.servicename.controller;

import id.co.ist.mobile.common.constant.ApiVersion;
import id.co.ist.mobile.common.constant.GenericResponseKey;
import id.co.ist.mobile.common.domain.ApiResponse;
import id.co.ist.mobile.common.util.ResponseUtil;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Controller
@RequestMapping(value = "/cat", produces = MediaType.APPLICATION_JSON_VALUE, consumes = ApiVersion.V2)
public class CatControllerV2 {

    @Autowired
    private CatService catService;

    @Operation(
            summary = "get cat fact",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = {@Content(examples =
                            {@ExampleObject(value = "{}")}
                    )}
            )
    )
    @ResponseBody
    @PostMapping(value = "/fact/")
    /**
     * NOTES: for any controller that don't have request body and wanted to documented
     * automatically using springfox 3.0.0 can follow this pattern
     * 1. add annotation @Operation with content argument (take a look at above @Operation block of code)
     * 2. In controller's parameters there have to be 1 param that given annotation @RequestBody
     */
    public ResponseEntity<Object> getCatFactV2(@RequestBody Object o, HttpServletRequest request, HttpServletResponse response) {
        return ResponseUtil.buildResponse(
                GenericResponseKey.SUCCESS.getResponseKey(),
                new ApiResponse.Message("", GenericResponseKey.SUCCESS.getResponseKey(), "", GenericResponseKey.SUCCESS.getResponseKey()), catService.getCatFact(),
                HttpStatus.OK);
    }
}
