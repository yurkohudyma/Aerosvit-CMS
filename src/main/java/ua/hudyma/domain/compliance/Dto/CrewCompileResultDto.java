package ua.hudyma.domain.compliance.Dto;

import ua.hudyma.domain.profile.CrewType;
import ua.hudyma.domain.profile.PilotType;

public record CrewCompileResultDto(
        Long captainId,
        PilotType captainType,
        Long secondPilotId,
        PilotType secondPilotType,
        Long purserId,
        CrewType purserCrewType,
        Long [] flightAttendantIds,
        CrewType[] FA_CrewTypes
) {
}
