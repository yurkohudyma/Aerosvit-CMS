package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.hudyma.domain.visa.Visa;

@Repository
public interface VisaRepository extends JpaRepository<Visa, Long> {
}
