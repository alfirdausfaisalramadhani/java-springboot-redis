package id.co.ist.mobile.servicename.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class TesController {

    @RequestMapping(value="/test", method= RequestMethod.POST)
    public ResponseEntity<Object> orderCreate(@RequestBody String jsonRequestDana) {
        return ResponseEntity.ok("okasdasdasdas");
    }
}
