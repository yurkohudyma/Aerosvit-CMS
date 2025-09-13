package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.hudyma.domain.certify.CertificateType;
import ua.hudyma.domain.certify.dto.CertsResponseDto;
import ua.hudyma.service.CertificateService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
@Log4j2
public class CertificateController {
    private final CertificateService certificateService;

    @PatchMapping("/update")
    public ResponseEntity<String> updateCert (@RequestParam Long id){
        return ResponseEntity.ok(certificateService.updateCertById(id));
    }

    @GetMapping("/issue")
    public ResponseEntity<String> issueCert (@RequestParam Long personId, @RequestParam CertificateType type){
        return ResponseEntity.ok(certificateService.issueCertWhenMissing (personId, type));
    }

    @GetMapping
    public ResponseEntity<String> generateCertWhereMissing (){
        certificateService.addCertificatesForAllWhereMissingAtLeastOne();
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/addOne")
    public ResponseEntity<String> generateSingleCert (@RequestParam Long certDataId){
        var certData = certificateService.getCertData(certDataId);
        certificateService.generateCertificate(certData);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/findActiveCrew")
    public ResponseEntity<List<CertsResponseDto>> getAllActiveCrewCerts (){
        return ResponseEntity.ok(certificateService.getAllActiveCrewCerts());
    }

    @GetMapping("/findExpiredCrew")
    public ResponseEntity<List<CertsResponseDto>> getAllExpiredCrewCerts (){
        return ResponseEntity.ok(certificateService.getAllExpiredCrewCerts());
    }
    @GetMapping("/findActivePilot")
    public ResponseEntity<List<CertsResponseDto>> getAllActivePilotCerts (){
        return ResponseEntity.ok(certificateService.getAllActivePilotCerts());
    }

    @GetMapping("/findExpiredPilot")
    public ResponseEntity<List<CertsResponseDto>> getAllExpiredPilotCerts (){
        return ResponseEntity.ok(certificateService.getAllExpiredPilotCerts());
    }
}