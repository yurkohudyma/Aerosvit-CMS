package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.hudyma.domain.training.Training;
import ua.hudyma.domain.training.dto.MissingTrainingResponseDto;
import ua.hudyma.domain.training.enums.CrewRole;
import ua.hudyma.domain.training.enums.TrainingType;
import ua.hudyma.service.TrainingService;

import java.util.List;

@RestController
@RequestMapping("/training")
@RequiredArgsConstructor
@Log4j2
public class TrainingController {
    private final TrainingService trainingService;

    @GetMapping("/create")
    public ResponseEntity<Training> createTraining(@RequestParam Long personId,
                                                   @RequestParam TrainingType type,
                                                   @RequestParam CrewRole role) {
        return ResponseEntity.ok(trainingService.createTraining(personId, role, type));
    }

    @GetMapping("/findAllMissingType")
    public ResponseEntity<List<MissingTrainingResponseDto>> getAllWithMissingTrainingType(
            @RequestParam TrainingType type) {
        return ResponseEntity.ok(trainingService.findAllCrewMemberWithMissingTraining(type));
    }
}
