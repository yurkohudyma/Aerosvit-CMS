package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        visaService.addVisaDto(visaRequestDto);
        return ResponseEntity.status(ACCEPTED).build();
    }

    @GetMapping("/addAll")
    public ResponseEntity<String> addVisaWhereMissing (){
        visaService.addVisaWhereMissing();
        return ResponseEntity.status(ACCEPTED).build();
    }
}
