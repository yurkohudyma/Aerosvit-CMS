package ua.hudyma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.hudyma.domain.certify.*;
import ua.hudyma.repository.CertDataRepository;
import ua.hudyma.repository.CertificateRepository;

import static ua.hudyma.util.IdGenerator.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final CertDataRepository certDataRepository;

    @Transactional
    public void addCertificateWhereMissing (){
        var commonCertList = certDataRepository.findAll();
        commonCertList
                .stream()
                .filter(cert -> cert.getCertificateList().isEmpty())
                .forEach(this::addCertificate);
    }

    private void addCertificate(CertificateData certificateData) {
        var certificate = new Certificate();
        certificate.setCertType(getRandomEnum(CertificateType.class));
        certificate.setCertCat(getRandomEnum(CertificateCategory.class));
        certificate.setAircraftType(getRandomEnum(AircraftType.class));
        certificate.setCertAuthority(getRandomEnum(CertifyAuthority.class));
        certificate.setIssuedAt(generateIssuedOn());
        certificate.setExpiresAt(certificate.getIssuedAt().plusYears(1));
        certificate.setLicenseNumber(generateId(3,10));
        certificate.setCertificateData(certificateData);
        certificateRepository.save(certificate);
    }
}
