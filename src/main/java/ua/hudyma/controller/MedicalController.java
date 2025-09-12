package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.hudyma.domain.certify.dto.CertsResponseDto;
import ua.hudyma.service.MedicalService;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/medical")
public class MedicalController {
    private final MedicalService medicalService;

    @GetMapping("/expired")
    public ResponseEntity<List<CertsResponseDto>> getExpiredMedicalsForAll (){
        return ResponseEntity.ok(medicalService.findExpiredMedicals());
    }

    @GetMapping("/missing")
    public ResponseEntity<List<String>> getMissingMedicalsForAll (){
        return ResponseEntity.ok(medicalService.findCrewWithMissingMedicals());
    }

    @Deprecated
    @GetMapping("/missing2")
    public ResponseEntity<Set<String>> getMissingMedicalsForAllNoStream (){
        return ResponseEntity.ok(medicalService.findCrewEmailsWithMissingMedicals());
    }

    //todo implement missing certs emission
}
