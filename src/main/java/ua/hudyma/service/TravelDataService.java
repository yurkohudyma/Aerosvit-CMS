package ua.hudyma.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.hudyma.domain.profile.Crew;
import ua.hudyma.domain.profile.Pilot;
import ua.hudyma.domain.visa.TravelData;
import ua.hudyma.domain.visa.dto.TravelDataRequestDto;
import ua.hudyma.repository.CrewRepository;
import ua.hudyma.repository.PilotRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.Function;

import static ua.hudyma.util.PassportDataGenerator.generateIssuedOn;
import static ua.hudyma.util.PassportDataGenerator.generatePassportId;

@Service
@RequiredArgsConstructor
public class TravelDataService {

    private final PilotRepository pilotRepository;
    private final CrewRepository crewRepository;

    public <T> void generateTravelDataGeneric(
            Supplier<List<T>> findAllFn,
            Function<T, TravelData> getTravelDataFn,
            BiConsumer<T, TravelData> setTravelDataFn,
            BiConsumer<T, TravelData> setOwnerFn,
            Consumer<List<T>> saveAllFn
    ) {
        List<T> list = findAllFn.get();
        for (T item : list) {
            if (getTravelDataFn.apply(item) == null) {
                TravelData travelData = new TravelData();
                setOwnerFn.accept(item, travelData);
                setTravelDataFn.accept(item, travelData);
                travelData.setPassportId(generatePassportId(2, 6));
                travelData.setIssuedOn(generateIssuedOn());
                travelData.setExpiresAt(travelData.getIssuedOn().plusYears(10));
            }
        }
        saveAllFn.accept(list);
    }

    public void generateCrewTravelData() {
        generateTravelDataGeneric(
                crewRepository::findAll,
                Crew::getTravelData,
                Crew::setTravelData,
                (crew, td) -> td.setCrew(crew),
                crewRepository::saveAll
        );
    }

    public void generatePilotTravelData() {
        generateTravelDataGeneric(
                pilotRepository::findAll,
                Pilot::getTravelData,
                Pilot::setTravelData,
                (pilot, td) -> td.setPilot(pilot),
                pilotRepository::saveAll
        );
    }
}