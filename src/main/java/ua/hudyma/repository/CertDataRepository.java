package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.hudyma.domain.certify.CertificateData;

@Repository
public interface CertDataRepository extends JpaRepository<CertificateData, Long> {
}
