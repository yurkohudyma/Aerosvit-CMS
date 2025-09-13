package ua.hudyma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.hudyma.domain.certify.Certificate;
import ua.hudyma.domain.certify.CertificateData;
import ua.hudyma.domain.certify.dto.CertsResponseDto;
import ua.hudyma.domain.profile.Crew;
import ua.hudyma.domain.profile.Pilot;
import ua.hudyma.repository.CertDataRepository;
import ua.hudyma.repository.CertificateRepository;
import ua.hudyma.repository.CrewRepository;
import ua.hudyma.repository.PilotRepository;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.time.LocalDate.now;
import static ua.hudyma.domain.certify.CertificateType.MEDICAL;

@RequiredArgsConstructor
@Log4j2
@Service
public class MedicalService {
    private final CertDataRepository certDataRepository;
    private final CertificateService certificateService;
    private final CrewRepository crewRepository;
    private final PilotRepository pilotRepository;

    public List<String> findCrewWithMissingMedicals() {
        return certDataRepository
                .findAll()
                .stream()
                .filter(cd -> cd
                        .getCertificateList()
                        .stream()
                        .noneMatch(c -> c.getCertType() == MEDICAL))
                .flatMap(cd -> {
                    var emails = new ArrayList<String>();
                    if (cd.getPilot() != null) {
                        emails.add(cd.getPilot().getProfile().getEmail());
                    }
                    else if (cd.getCrew() != null) {
                        emails.add(cd.getCrew().getProfile().getEmail());
                    }
                    return emails.stream();
                })
                .filter(Objects::nonNull)
                .sorted()
                .distinct()
                .toList();
    }

    @Deprecated
    public Set<String> findCrewEmailsWithMissingMedicals() {
        var crewList = crewRepository.findAll();
        var pilotList = pilotRepository.findAll();
        var emailsList = new HashSet<String>();
        for (Crew crew : crewList) {
            var crewCertList = crew
                    .getCertificateData()
                    .getCertificateList();
            var certfound = false;
            for (Certificate cert : crewCertList) {
                if (isCertMedical(cert)) {
                    certfound = true;
                    break;
                }
            }
            if (!certfound) emailsList
                    .add(crew.getProfile().getEmail());
        }
        for (Pilot pilot : pilotList) {
            var pilotCertList = pilot
                    .getCertificateData()
                    .getCertificateList();
            var certfound = false;
            for (Certificate cert : pilotCertList) {
                if (isCertMedical(cert)) {
                    certfound = true;
                    break;
                }
            }
            if (!certfound) emailsList
                    .add(pilot.getProfile().getEmail());
        }
        return emailsList;
    }

    private boolean isCertMedical(Certificate cert) {
        return cert.getCertType() == MEDICAL;
    }

    public List<CertsResponseDto> findExpiredMedicals() {
        return certificateService.getCertificatesByFilter(
                anyOfCrewIsAvailableFilter(),
                expiredMedicalsFilter(),
                allCrewMedicalEmailExtractor());
    }

    private Predicate<CertificateData> anyOfCrewIsAvailableFilter() {
        return data -> data.getPilot() != null || data.getCrew() != null;
    }

    private Function<CertificateData, String> allCrewMedicalEmailExtractor() {
        return data ->
                Optional.ofNullable(data.getPilot())
                        .map(pilot ->
                                pilot
                                        .getCertificateData()
                                        .getPilot()
                                        .getProfile()
                                        .getEmail())
                        .or(() -> Optional.ofNullable(data.getCrew())
                                .map(crew ->
                                        crew
                                                .getCertificateData()
                                                .getCrew()
                                                .getProfile()
                                                .getEmail()))
                        .orElse(null);
    }

    private Predicate<Certificate> expiredMedicalsFilter() {
        return certificate -> certificate.getExpiresAt().isBefore(now()) &&
                certificate.getCertType() == MEDICAL;
    }
}
