package ua.hudyma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.hudyma.domain.certify.Certificate;
import ua.hudyma.domain.certify.CertificateData;
import ua.hudyma.domain.profile.Crew;
import ua.hudyma.domain.profile.Pilot;
import ua.hudyma.repository.CertDataRepository;
import ua.hudyma.repository.CertificateRepository;
import ua.hudyma.repository.CrewRepository;
import ua.hudyma.repository.PilotRepository;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@Log4j2
@RequiredArgsConstructor
public class CertDataService {
    private final CertDataRepository certDataRepository;
    private final CertificateRepository certificateRepository;
    private final PilotRepository pilotRepository;
    private final CrewRepository crewRepository;

    public <T> void generateCertDataWhereMissing(
            Supplier<List<T>> findAllFn,
            Function<T, CertificateData> getCertDataFn,
            BiConsumer<T, CertificateData> setCertDataFn,
            BiConsumer<T, CertificateData> setOwnerFn,
            Consumer<List<T>> saveAllFn) {
        var list = findAllFn.get();
        for (T item: list){
            if (getCertDataFn.apply(item) == null){
                var certData = new CertificateData();
                setOwnerFn.accept(item, certData);
                setCertDataFn.accept(item, certData);
            }
        }
        saveAllFn.accept(list);
    }

    public void generateCrewCertData() {
        generateCertDataWhereMissing(
                crewRepository::findAll,
                Crew::getCertificateData,
                Crew::setCertificateData,
                (crew, sd) -> sd.setCrew(crew),
                crewRepository::saveAll
        );
    }

    public void generatePilotsCertData() {
        generateCertDataWhereMissing(
                pilotRepository::findAll,
                Pilot::getCertificateData,
                Pilot::setCertificateData,
                (pilot, sd) -> sd.setPilot(pilot),
                pilotRepository::saveAll
        );
    }
}
