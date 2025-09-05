package ua.hudyma.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.hudyma.domain.profile.Pilot;
import ua.hudyma.domain.visa.TravelData;
import ua.hudyma.domain.visa.dto.TravelDataRequestDto;
import ua.hudyma.repository.PilotRepository;

import java.util.Arrays;
import java.util.List;

import static ua.hudyma.util.PassportDataGenerator.generateIssuedOn;
import static ua.hudyma.util.PassportDataGenerator.generatePassportId;

@Service
@RequiredArgsConstructor
public class TravelDataService {

    private final PilotRepository pilotRepository;

    public void addTravelData(TravelDataRequestDto[] travelDataDtos) {
        List<Pilot> pilots = Arrays.stream(travelDataDtos)
                .map(this::generateTravelData)
                .toList();
        pilotRepository.saveAll(pilots);
    }

    private Pilot generateTravelData(TravelDataRequestDto td) {
        var pilot = pilotRepository.findById(td.pilotId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Pilot not found with ID: " + td.pilotId()));
        return populateTravelDataFields(pilot);
    }

    public void generateTravelData() {
        List<Pilot> pilotList = pilotRepository.findAll();
        pilotList.forEach(this::populateTravelDataFields);
        pilotRepository.saveAll(pilotList);
    }

    private Pilot populateTravelDataFields(Pilot pilot) {
        var travelData = pilot.getTravelData();
        if (travelData == null) {
            travelData = new TravelData();
            travelData.setPilot(pilot);
            pilot.setTravelData(travelData);
        }
        travelData.setPassportId(generatePassportId(2, 6));
        travelData.setIssuedOn(generateIssuedOn());
        travelData.setExpiresAt(travelData.getIssuedOn().plusYears(10));
        return pilot;
    }
}
