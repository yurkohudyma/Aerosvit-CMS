package ua.hudyma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.hudyma.repository.TrainingRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingRepository trainingRepository;

    //todo add CRUD
}
