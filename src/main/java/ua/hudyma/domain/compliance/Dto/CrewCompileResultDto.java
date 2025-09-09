package ua.hudyma.domain.compliance.Dto;

import ua.hudyma.domain.profile.CrewType;
import ua.hudyma.domain.profile.PilotType;

import java.util.List;

public record CrewCompileResultDto(
        Long captainId,
        PilotType captainType,
        Long secondPilotId,
        PilotType secondPilotType,
        Long thirdPilotId,
        PilotType thirdPilotType,
        Long purserId,
        CrewType purserCrewType,
        List<Long> flightAttendantIds,
        List<CrewType> flightAttendantCrewTypesList
) {
}
