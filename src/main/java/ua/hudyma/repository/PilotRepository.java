package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.hudyma.domain.profile.Pilot;

public interface PilotRepository extends JpaRepository<Long, Pilot> {
}
