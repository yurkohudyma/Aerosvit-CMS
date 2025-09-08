package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.hudyma.domain.profile.Crew;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Long> {
}
