package ua.hudyma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.hudyma.domain.certify.*;
import ua.hudyma.domain.certify.dto.CertsResponseDto;
import ua.hudyma.repository.CertDataRepository;
import ua.hudyma.repository.CertificateRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static ua.hudyma.util.IdGenerator.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final CertDataRepository certDataRepository;

    @Transactional
    public void addCertificateWhereMissing() {
        var commonCertList = certDataRepository.findAll();
        commonCertList
                .stream()
                .filter(cert -> cert.getCertificateList().isEmpty())
                .forEach(this::addCertificate);
    }

    public void addCertificate(CertificateData certificateData) {
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
                cert -> cert.getExpiresAt().isAfter(LocalDate.now()),
                data -> data.getCrew().getProfile().getEmail()
        );
    }

    public List<CertsResponseDto> getAllExpiredCrewCerts() {
        return getCertificatesByFilter(
                data -> data.getCrew() != null,
                cert -> cert.getExpiresAt().isBefore(LocalDate.now()),
                data -> data.getCrew().getProfile().getEmail()
        );
    }

    public List<CertsResponseDto> getAllActivePilotCerts() {
        return getCertificatesByFilter(
                data -> data.getPilot() != null,
                cert -> cert.getExpiresAt().isAfter(LocalDate.now()),
                data -> data.getPilot().getProfile().getEmail()
        );
    }

    public List<CertsResponseDto> getAllExpiredPilotCerts() {
        return getCertificatesByFilter(
                data -> data.getPilot() != null,
                cert -> cert.getExpiresAt().isBefore(LocalDate.now()),
                data -> data.getPilot().getProfile().getEmail()
        );
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