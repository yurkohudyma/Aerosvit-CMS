package ua.hudyma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.hudyma.domain.certify.*;
import ua.hudyma.domain.certify.dto.CertsResponseDto;
import ua.hudyma.domain.profile.Crew;
import ua.hudyma.domain.profile.Pilot;
import ua.hudyma.repository.CertDataRepository;
import ua.hudyma.repository.CertificateRepository;
import ua.hudyma.repository.CrewRepository;
import ua.hudyma.repository.PilotRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.time.LocalDate.now;
import static ua.hudyma.domain.certify.CertificateCategory.LICENSE;
import static ua.hudyma.util.IdGenerator.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final CertDataRepository certDataRepository;
    private final CrewRepository crewRepository;
    private final PilotRepository pilotRepository;

    @Transactional
    public void addCertificatesForAllWhereMissingAtLeastOne() {
        var commonCertList = certDataRepository.findAll();
        commonCertList
                .stream()
                .filter(cert -> cert.getCertificateList().isEmpty())
                .forEach(this::generateCertificate);
    }

    public void generateCertificate(CertificateData certificateData) {
        var certificate = Certificate
                .builder()
                .certType(getRandomEnum(CertificateType.class))
                .certCat(getRandomEnum(CertificateCategory.class))
                .aircraftType(getRandomEnum(AircraftType.class))
                .certAuthority(getRandomEnum(CertifyAuthority.class))
                .issuedAt(generateIssuedOn())
                .licenseNumber(generateId(3, 10))
                .certificateData(certificateData)
                .build();
        certificate.setExpiresAt(certificate.getIssuedAt().plusYears(1));
        certificateRepository.save(certificate);
    }

    public List<CertsResponseDto> getAllActiveCrewCerts() {
        return getCertificatesByFilter(
                data -> data.getCrew() != null,
                cert -> cert.getExpiresAt().isAfter(now()),
                data -> data.getCrew().getProfile().getEmail()
        );
    }

    public List<CertsResponseDto> getAllExpiredCrewCerts() {
        return getCertificatesByFilter(
                data -> data.getCrew() != null,
                cert -> cert.getExpiresAt().isBefore(now()),
                data -> data.getCrew().getProfile().getEmail()
        );
    }

    public List<CertsResponseDto> getAllActivePilotCerts() {
        return getCertificatesByFilter(
                data -> data.getPilot() != null,
                cert -> cert.getExpiresAt().isAfter(now()),
                data -> data.getPilot().getProfile().getEmail()
        );
    }

    public List<CertsResponseDto> getAllExpiredPilotCerts() {
        return getCertificatesByFilter(
                data -> data.getPilot() != null,
                cert -> cert.getExpiresAt().isBefore(now()),
                data -> data.getPilot().getProfile().getEmail()
        );
    }

    //todo need to separate personId checkUp for presency, it could ambigously belong to both

    public String issueCertWhenMissing(Long personId, CertificateType type) {
        Object certOwner;
        Certificate cert;
        var pilot = pilotRepository.findById(personId);
        if (pilot.isEmpty()) {
            certOwner = crewRepository.findById(personId).orElseThrow();
            if (ownsCertificateOfAType (certOwner, type)){
                throw new UnsupportedOperationException("Person already own the cert of the type");
            }
            var certData = certDataRepository
                    .findByCrewId(personId).orElseThrow();
            cert = createCertificate(type, certData);
        }
        else {
            if (ownsCertificateOfAType(pilot.get(), type)){
                throw new UnsupportedOperationException("Person already own the cert of the type");
            }
            var certData = certDataRepository
                    .findByPilotId(personId).orElseThrow();
            cert = createCertificate(type, certData);
        }
        return String.format("Certificate %s has been successfully created", cert.getLicenseNumber());
    }

    private Certificate createCertificate(CertificateType type, CertificateData certData) {
        var certificate = Certificate
                .builder()
                .certType(type)
                .certCat(LICENSE)
                .aircraftType(getRandomEnum(AircraftType.class))
                .certAuthority(getRandomEnum(CertifyAuthority.class))
                .issuedAt(now())
                .licenseNumber(generateId(3, 10))
                .certificateData(certData)
                .build();
        certificate.setExpiresAt(certificate.getIssuedAt().plusYears(1));
        return certificateRepository.save(certificate);
    }

    private boolean ownsCertificateOfAType(Object certOwner, CertificateType type) {
        if (certOwner instanceof Crew){
            return ((Crew) certOwner)
                    .getCertificateData()
                    .getCertificateList()
                    .stream()
                    .anyMatch(cert -> cert.getCertType() == type);
        }
        else if (certOwner instanceof Pilot){
            return ((Pilot) certOwner)
                    .getCertificateData()
                    .getCertificateList()
                    .stream()
                    .anyMatch(cert -> cert.getCertType() == type);
        }
        return false;
    }

    @Transactional
    public String updateCertById(Long id) {
        var certificate = certificateRepository
                .findById(id)
                .orElseThrow();
        if (isExpired (certificate)) {
            certificate.setIssuedAt(now());
            certificate.setExpiresAt(now().plusYears(1));
            log.info("---> cert {} has been updated", id);
            return String.format("Cert %d has been updated", id);
        }
        else log.error("---> cert {} expires at",
                certificate.getExpiresAt());
        return String.format("Cert %d expires on %s", id, certificate.getExpiresAt());
    }

    private boolean isExpired(Certificate certificate) {
        return certificate.getExpiresAt().isBefore(now());
    }

    public List<CertsResponseDto> getCertificatesByFilter(
            Predicate<CertificateData> entityAvailableFilter,
            Predicate<Certificate> certConditionsFilter,
            Function<CertificateData, String> emailExtractor) {
        return certificateRepository
                .findAll()
                .stream()
                .filter(cert -> {
                    var data = cert.getCertificateData();
                    return data != null && entityAvailableFilter.test(data);
                })
                .filter(certConditionsFilter)
                .map(cert -> {
                    var data = certDataRepository
                            .findById(cert.getCertificateData()
                                    .getId())
                            .orElseThrow();
                    return new CertsResponseDto(
                            emailExtractor.apply(data),
                            cert.getCertType(),
                            cert.getCertCat(),
                            cert.getCertAuthority(),
                            cert.getIssuedAt(),
                            cert.getExpiresAt(),
                            cert.getLicenseNumber()
                    );
                })
                .toList();
    }

    public CertificateData getCertData(Long certDataId) {
        return certDataRepository.findById(certDataId).orElseThrow();
    }
}