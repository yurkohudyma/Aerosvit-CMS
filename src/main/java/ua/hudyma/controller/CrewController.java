package ua.hudyma.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.hudyma.service.CrewService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/crew")
public class CrewController {
    private final CrewService crewService;

    @GetMapping("/{number}")
    public ResponseEntity<String> addCrew (@PathVariable Integer number){
        crewService.generateCrew (number);
        return ResponseEntity.status(CREATED).build();
    }
}
