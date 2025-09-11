package ua.hudyma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.hudyma.domain.certify.Certificate;
import ua.hudyma.domain.certify.CertificateData;
import ua.hudyma.domain.certify.CertificateType;
import ua.hudyma.domain.certify.dto.CertsResponseDto;
import ua.hudyma.domain.profile.Profile;
import ua.hudyma.repository.CertDataRepository;
import ua.hudyma.repository.CertificateRepository;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.time.LocalDate.now;
import static ua.hudyma.domain.certify.CertificateType.MEDICAL;

@RequiredArgsConstructor
@Log4j2
@Service
public class MedicalService {
    private final CertDataRepository certDataRepository;
    private final CertificateRepository certificateRepository;
    private final CertificateService certificateService;

    public List<String> findCrewWithMissingMedicals() {
        return certDataRepository
                .findAll()
                .stream()
                .filter(cd -> cd
                        .getCertificateList()
                        .stream()
                        .noneMatch(c -> c.getCertType() == CertificateType.MEDICAL))
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

    private Predicate<Certificate> hasAnyMedical() {
        return cert -> cert.getCertType() == MEDICAL;
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
                                pilot.getCertificateData().getPilot().getProfile().getEmail())
                        .or(() -> Optional.ofNullable(data.getCrew())
                                .map(crew ->
                                        crew.getCertificateData().getCrew().getProfile().getEmail()))
                        .orElse(null);
    }

    private Predicate<Certificate> expiredMedicalsFilter() {
        return certificate -> certificate.getExpiresAt().isBefore(now()) &&
                certificate.getCertType() == MEDICAL;
    }
}
