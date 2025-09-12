package ua.hudyma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.hudyma.domain.training.Training;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
}
