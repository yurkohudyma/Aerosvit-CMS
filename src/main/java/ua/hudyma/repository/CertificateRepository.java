package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.hudyma.domain.certify.Certificate;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
