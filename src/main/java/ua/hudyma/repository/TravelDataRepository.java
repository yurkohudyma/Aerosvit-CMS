package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.hudyma.domain.visa.TravelData;

@Repository
public interface TravelDataRepository extends JpaRepository<TravelData, Long> {
}
