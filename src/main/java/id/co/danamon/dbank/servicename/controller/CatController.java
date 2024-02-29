package id.co.danamon.dbank.servicename.controller;

import id.co.danamon.dbank.common.constant.ApiVersion;
import id.co.danamon.dbank.common.constant.GenericResponseKey;
import id.co.danamon.dbank.common.domain.ApiResponse;
import id.co.danamon.dbank.common.util.ResponseUtil;
import id.co.danamon.dbank.servicename.domain.dto.internal.CatOwnerDto;
import id.co.danamon.dbank.servicename.service.CatService;
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
import java.util.Objects;
import java.util.Optional;

import static id.co.danamon.dbank.servicename.constant.ServiceResponseKey.*;


@Slf4j
@Controller
@RequestMapping(value = "/cat", produces = MediaType.APPLICATION_JSON_VALUE, consumes = ApiVersion.V1)
public class CatController {

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
    /**
     * NOTES: for any controller that don't have request body and wanted to documented
     * automatically using springfox 3.0.0 can follow this pattern
     * 1. add annotation @Operation with content argument (take a look at above @Operation block of code)
     * 2. In controller's parameters there have to be 1 param that given annotation @RequestBody
     */
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
                    PARAMETER_INVALID,
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
                    PARAMETER_INVALID,
                    new ApiResponse.Message(),
                    null,
                    HttpStatus.BAD_REQUEST);

        }
        Optional<CatOwnerDto> catOwnerUpdated = catService.updateCatOwner(requestBody);
        if (catOwnerUpdated.isEmpty()) {
            return ResponseUtil.buildResponse(
                    NOT_FOUND,
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
                    PARAMETER_INVALID,
                    new ApiResponse.Message(),
                    null,
                    HttpStatus.BAD_REQUEST);
        }
        Optional<CatOwnerDto> deletedCatOwner = catService.deleteCatOwner(requestBody.getId());
        if (deletedCatOwner.isEmpty()) {
            return ResponseUtil.buildResponse(
                    NOT_FOUND,
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
