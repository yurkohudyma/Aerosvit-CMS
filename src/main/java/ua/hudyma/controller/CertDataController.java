package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.hudyma.service.CertDataService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/cert")
@RequiredArgsConstructor
@Log4j2
public class CertDataController {
    private final CertDataService certDataService;

    @GetMapping("/generatePilotsCertData")
    public ResponseEntity<String> generatePilotsCertData (){
        certDataService.generatePilotsCertData();
        return ResponseEntity.status(CREATED).build();
    }
    @GetMapping("/generateCrewCertData")
    public ResponseEntity<String> generateCrewCertData (){
        certDataService.generateCrewCertData();
        return ResponseEntity.status(CREATED).build();
    }
}
