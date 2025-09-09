package ua.hudyma.domain.certify.dto;

import ua.hudyma.domain.certify.CertificateCategory;
import ua.hudyma.domain.certify.CertificateType;
import ua.hudyma.domain.certify.CertifyAuthority;

import java.time.LocalDate;

public record CertsResponseDto(
        String ownerEmail,
        CertificateType certificateType,
        CertificateCategory certCat,
        CertifyAuthority certifyAuthority,
        LocalDate issuedAt,
        LocalDate expiredAt,
        String licenseNumber

) {
}
