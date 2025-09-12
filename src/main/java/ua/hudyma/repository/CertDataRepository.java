package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.hudyma.domain.certify.Certificate;
import ua.hudyma.domain.certify.CertificateData;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertDataRepository extends JpaRepository<CertificateData, Long> {

    @Query(value = """
            
            SELECT
                p.email AS email,
                'PILOT' AS role
            FROM certificate_data cd
            JOIN pilots p ON cd.pilot_id = p.id
            WHERE NOT EXISTS (
                SELECT 1
                FROM certificates c
                WHERE c.certificate_data_id = cd.id
                  AND c.certificate_type = 'MEDICAL'
            )
                        
            UNION ALL                        
            
            SELECT
                cw.email AS email,
                'CREW' AS role
            FROM certificate_data cd
            JOIN crew cw ON cd.crew_id = cw.id
            WHERE NOT EXISTS (
                SELECT 1
                FROM certificates c
                WHERE c.certificate_data_id = cd.id
                  AND c.certificate_type = 'MEDICAL'
            );
                        
            """, nativeQuery = true)
    List<String> findAllCrewEmailsWithMissingMedicals();

    Optional<CertificateData> findByCrewId(Long personId);

    Optional<CertificateData> findByPilotId(Long personId);
}
