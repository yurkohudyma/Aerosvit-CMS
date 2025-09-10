package ua.hudyma.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.hudyma.domain.compliance.Dto.CrewCompileResultDto;
import ua.hudyma.domain.compliance.Dto.CrewCompileRequestDto;
import ua.hudyma.domain.profile.Crew;
import ua.hudyma.domain.profile.Pilot;
import ua.hudyma.domain.profile.PilotType;

import java.util.Collections;
import java.util.List;

import static ua.hudyma.domain.profile.PilotType.NA;

@Service
@RequiredArgsConstructor
@Log4j2
public class ComplyService {
    private final PilotService pilotService;
    private final CrewService crewService;

    public List<CrewCompileResultDto> compileCrewForFlight(
            CrewCompileRequestDto crewCompileRequestDto) {
        var aircraftType = crewCompileRequestDto
                .aircraftType();
        var cptList = pilotService
                .findPilot(aircraftType, true);
        var pilotsQuantity = aircraftType.getMaxPilots();

        var secondPilotList = pilotService
                .findPilot (aircraftType, false);

        var thirdPilot = pilotsQuantity > 2 && cptList.size() > 1
                ? cptList.get(1)
                : new Pilot();
        var crewQuantity = aircraftType.getMaxCabinCrew();

        var purserList = crewService
                .findCrew (aircraftType, true);

        var secondPilot = !secondPilotList.isEmpty()
                ? secondPilotList.get(0) : new Pilot();
        var purser = !purserList.isEmpty()
                ? purserList.get(0) : new Crew();

        var flightAttList = crewService
                .findCrew(aircraftType, false);
        var flightAttendantIdList  = flightAttList
                .stream()
                .map(Crew::getId)
                .limit(crewQuantity)
                .toList();
        var flightAttendantCrewTypesList = flightAttList
                .stream()
                .map(Crew::getCrewType)
                .limit(crewQuantity)
                .toList();
        if (pilotsQuantity <= 2 ){
            thirdPilot.setId(0L);
            thirdPilot.setPilotType(NA);
        }
        return cptList
                .stream()
                .map(cpt -> new CrewCompileResultDto(
                        cpt.getId(),
                        cpt.getPilotType(),
                        secondPilot.getId(),
                        secondPilot.getPilotType(),
                        thirdPilot.getId(),
                        thirdPilot.getPilotType(),
                        purser.getId(),
                        purser.getCrewType(),
                        flightAttendantIdList,
                        flightAttendantCrewTypesList))
                .toList();
    }
}
