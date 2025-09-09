package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.hudyma.domain.profile.Pilot;
import ua.hudyma.domain.profile.PilotType;

import java.util.List;
import java.util.Optional;

public interface PilotRepository extends JpaRepository<Pilot, Long> {
    List<Pilot> findByPilotType(PilotType type);
}
