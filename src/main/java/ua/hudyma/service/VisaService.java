package ua.hudyma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.hudyma.domain.visa.Visa;
import ua.hudyma.domain.visa.VisaStatus;
import ua.hudyma.domain.visa.VisaType;
import ua.hudyma.domain.visa.dto.VisaRequestDto;
import ua.hudyma.enums.Country;
import ua.hudyma.repository.TravelDataRepository;
import ua.hudyma.repository.VisaRepository;

import static ua.hudyma.domain.visa.EntriesNumber.MULTI;
import static ua.hudyma.util.PassportDataGenerator.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class VisaService {
    private final VisaRepository visaRepository;
    private final TravelDataRepository travelDataRepository;

    @Transactional
    public void addVisa(VisaRequestDto visaRequestDto) {
        if (visaRequestDto.travelDataId() == null) {
            throw new IllegalArgumentException("Travel Data Id was not provided");
        }
        var td = travelDataRepository
                .findById(visaRequestDto.travelDataId())
                .orElseThrow();
        var visa = new Visa();
        if (visaRequestDto.entriesNumber() == null){
            visa.setEntriesNumber(MULTI);
        }
        visa.setVisaId(generatePassportId(0, 9));
        if (visaRequestDto.visaType() == null){
            visa.setVisaType(getRandomEnum(
                    VisaType.class));
        }
        if (visaRequestDto.emittedByCountry() == null) {
            visa.setEmittedByCountry(getRandomEnum(
                    Country.class));
        }
        if (visaRequestDto.visaStatus() == null) {
            visa.setVisaStatus(getRandomEnum(
                    VisaStatus.class));
        }
        if (visaRequestDto.issuedOn() == null){
            visa.setIssuedOn(generateIssuedOn());
        }
        if (visaRequestDto.expiresOn() == null){
            visa.setExpiresOn(visa.getIssuedOn().plusMonths(6));
        }
        visaRepository.save(visa);
        visa.setTravelData(td);
    }
}
