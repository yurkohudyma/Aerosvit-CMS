package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.hudyma.domain.visa.dto.TravelDataRequestDto;
import ua.hudyma.service.TravelDataService;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/travels")
@RequiredArgsConstructor
@Log4j2
public class TravelDataController {
    private final TravelDataService travelDataService;

    @PostMapping
    public ResponseEntity<String> addTravelData (
            @RequestBody TravelDataRequestDto[] travelDataRequestDto){
        travelDataService.addTravelData (travelDataRequestDto);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/generateTravelDataForAll")
    public ResponseEntity<String> generateTravelDateForAllPilots (){
        travelDataService.generateTravelData();
        return ResponseEntity.status(ACCEPTED).build();
    }
}
