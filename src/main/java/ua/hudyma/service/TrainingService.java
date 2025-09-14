package ua.hudyma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.hudyma.domain.certify.AircraftType;
import ua.hudyma.domain.profile.Crew;
import ua.hudyma.domain.profile.Pilot;
import ua.hudyma.domain.training.Training;
import ua.hudyma.domain.training.dto.MissingTrainingResponseDto;
import ua.hudyma.domain.training.enums.CrewRole;
import ua.hudyma.domain.training.enums.TrainingAuthority;
import ua.hudyma.domain.training.enums.TrainingType;
import ua.hudyma.exception.CrewMemberNotFoundException;
import ua.hudyma.repository.CrewRepository;
import ua.hudyma.repository.PilotRepository;
import ua.hudyma.repository.TrainingRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static ua.hudyma.domain.training.enums.CrewRole.CREW;
import static ua.hudyma.domain.training.enums.CrewRole.PILOT;
import static ua.hudyma.util.IdGenerator.generateId;
import static ua.hudyma.util.IdGenerator.getRandomEnum;

@Service
@Log4j2
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final CrewRepository crewRepository;
    private final PilotRepository pilotRepository;

    public List<MissingTrainingResponseDto> findAllCrewMemberWithMissingTraining (TrainingType type){
        var crewList = trainingRepository
                .findAll()
                .stream()
                .filter(training -> training.getCrew() != null)
                .filter(training -> training.getCrew()
                        .getTrainingList()
                        .stream()
                        .noneMatch(tr -> tr.getTrainingType() == type))
                .toList();
        var pilotList = trainingRepository
                .findAll()
                .stream()
                .filter(training -> training.getPilot() != null)
                .filter(training -> training.getPilot()
                        .getTrainingList()
                        .stream()
                        .noneMatch(tr -> tr.getTrainingType() == type))
                .toList();
        var commonList = new ArrayList<>(crewList);
        commonList.addAll(pilotList);
        return commonList
                .stream()
                .map(training -> new MissingTrainingResponseDto(
                        getCrewMemberId (training),
                        getRole(training),
                        type))
                .toList();
    }

    public void generateTrainingWhereasCrewHasNone(CrewRole role) {
        if (role == PILOT) {
            pilotRepository
                    .findAll()
                    .stream()
                    .filter(pilot -> pilot.getTrainingList().isEmpty())
                    .forEach(this::generateTraining);
        }
        else {
            crewRepository
                    .findAll()
                    .stream()
                    .filter(crew -> crew.getTrainingList().isEmpty())
                    .forEach(this::generateTraining);
        }
    }

    private Long getCrewMemberId(Training training) {
        return training.getCrew() != null ?
                training.getCrew().getId() :
                training.getPilot().getId();
    }

    private CrewRole getRole(Training training) {
        return training.getCrew() != null ? CREW : PILOT;
    }

    public Training createTraining (Long personId, CrewRole role, TrainingType trainingType){
        var person = getCrewMember(personId, role);
        if (person.isEmpty()) {
            throw new CrewMemberNotFoundException("Crew not found");
        }
        if (trainingType == null){
            log.error("Training Type is Compulsory, not provided");
            throw new IllegalArgumentException();
        }
        var training = generateTrainingByType(trainingType);
        if (role == CREW) {
            training.setCrew((Crew) person.get());
        } else {
            training.setPilot((Pilot) person.get());
        }
        return trainingRepository.save(training);
    }

    private Training generateTrainingByType(TrainingType trainingType) {
        return Training
                .builder()
                .trainingAuthority(getRandomEnum(TrainingAuthority.class))
                .aircraftType(getRandomEnum(AircraftType.class))
                .certificateId(generateId(3, 12))
                .createdOn(now())
                .issuedOn(LocalDate.now())
                .expiresOn(LocalDate.now().plusYears(1))
                .trainingType(trainingType)
                .build();
    }

    @Transactional
    private Training generateTraining(Object crewMember) {
        Pilot pilot;
        Crew crew;
        if (crewMember instanceof Pilot) {
            pilot = (Pilot) crewMember;
            crew = null;
        }
        else {
            crew = (Crew) crewMember;
            pilot = null;
        }
        var training = Training
                .builder()
                .trainingAuthority(getRandomEnum(TrainingAuthority.class))
                .aircraftType(getRandomEnum(AircraftType.class))
                .certificateId(generateId(3, 12))
                .createdOn(now())
                .issuedOn(LocalDate.now())
                .expiresOn(LocalDate.now().plusYears(1))
                .trainingType(getRandomEnum(TrainingType.class))
                .pilot(pilot)
                .crew(crew)
                .build();
        return trainingRepository.save(training);
    }

    private Optional<?> getCrewMember(Long personId, CrewRole role) {
        return role == PILOT ?
                pilotRepository.findById(personId) :
                crewRepository.findById(personId);
    }
}