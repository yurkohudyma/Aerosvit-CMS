package ua.hudyma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.hudyma.repository.VisaRepository;

@Service
@RequiredArgsConstructor
@Log4j2
public class VisaService {
    private final VisaRepository visaRepository;
}
