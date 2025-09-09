package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.hudyma.domain.profile.Crew;
import ua.hudyma.domain.profile.CrewType;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Long> {
    List<Crew> findByCrewType(CrewType type);
}
