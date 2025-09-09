package ua.hudyma.domain.compliance;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.hudyma.domain.compliance.Dto.CrewCompileRequestDto;
import ua.hudyma.domain.compliance.Dto.CrewCompileResultDto;
import ua.hudyma.service.*;

import java.util.List;

@RestController
@RequestMapping("/compile")
@RequiredArgsConstructor
@Log4j2
public class ComplyController {
    private final ComplyService complyService;

    @PostMapping
    public ResponseEntity<List<CrewCompileResultDto>> compileCrewForFlight (
            @RequestBody CrewCompileRequestDto crewCompileRequestDto){
        return ResponseEntity.ok(complyService.compileCrewForFlight(crewCompileRequestDto));
    }
}
