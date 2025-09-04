package ua.hudyma.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.hudyma.service.PilotService;

@RestController
@RequestMapping("/pilots")
@RequiredArgsConstructor
@Log4j2
public class PilotController {
    private final PilotService pilotService;

    @GetMapping("/{number}")
    public ResponseEntity<String> addPilots (@PathVariable Integer number){
        pilotService.generatePilots(number);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
