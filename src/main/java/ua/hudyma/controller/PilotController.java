package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.hudyma.service.PilotService;

@RestController
@RequestMapping("/pilots")
@RequiredArgsConstructor
@Log4j2
public class PilotController {
    private final PilotService pilotService;
}
