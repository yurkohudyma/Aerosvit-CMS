package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.hudyma.service.CertificateService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
@Log4j2
public class CertificateController {
    private final CertificateService certificateService;

    @GetMapping
    public ResponseEntity<String> generateCertWhereMissing (){
        certificateService.addCertificateWhereMissing();
        return ResponseEntity.status(CREATED).build();
    }
}
