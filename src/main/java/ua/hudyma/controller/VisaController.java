package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.hudyma.domain.visa.dto.VisaRequestDto;
import ua.hudyma.service.VisaService;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping("/visas")
@RequiredArgsConstructor
@Log4j2
public class VisaController {
    private final VisaService visaService;

    @PostMapping("/add")
    public ResponseEntity<String> addVisa (
            @RequestBody VisaRequestDto visaRequestDto){
        visaService.addVisa(visaRequestDto);
        return ResponseEntity.status(ACCEPTED).build();

    }
}
