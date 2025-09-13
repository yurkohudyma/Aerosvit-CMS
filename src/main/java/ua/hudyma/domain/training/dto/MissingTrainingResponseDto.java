package ua.hudyma.domain.training.dto;

import ua.hudyma.domain.training.enums.CrewRole;
import ua.hudyma.domain.training.enums.TrainingType;

public record MissingTrainingResponseDto(
        Long personId,
        CrewRole role,
        TrainingType type) {
}
