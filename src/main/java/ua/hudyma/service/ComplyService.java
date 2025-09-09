package ua.hudyma.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.hudyma.domain.certify.AircraftType;
import ua.hudyma.domain.compliance.Dto.CrewCompileResultDto;
import ua.hudyma.domain.compliance.Dto.CrewCompileRequestDto;
import ua.hudyma.domain.profile.Pilot;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ComplyService {
    private final PilotService pilotService;
    private final VisaService visaService;
    private final CertDataService certDataService;
    private final TravelDataService travelDataService;
    public List<CrewCompileResultDto> compileCrewForFlight(CrewCompileRequestDto crewCompileRequestDto) {
        //todo discover captain from CPT/TRI/TRE/LTC/DE
        var aircraftType = crewCompileRequestDto.aircraftType();
        var cptList = pilotService.findCaptain(aircraftType);

        var pilotsQuantity = aircraftType.getMaxPilots();
        var crewQuantity = aircraftType.getMaxCabinCrew();
        //todo check certs/medical

        //todo discover 2nd pilot from FO/SFO/SO/RO
        //todo check certs/medical
        Pilot pilot3rd;
        if (pilotsQuantity > 2 && cptList.size() > 1){
            pilot3rd = cptList.get(1);
        }

        //todo discover purser from PUR/IFM/TRI/LCC/SCC/SC_CSD
        //todo check certs/medical

        //todo discover cabincrew from CC/SCC/OBS/TRAINEE
        return cptList
                .stream()
                .map(cpt -> new CrewCompileResultDto(
                cpt.getId(),
                cpt.getPilotType(),
                null,
                null,
                null,
                null,
                null,
                null))
                .toList();
    }
}
