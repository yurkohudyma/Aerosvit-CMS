package ua.hudyma.domain.visa.dto;

import ua.hudyma.domain.visa.EntriesNumber;
import ua.hudyma.domain.visa.VisaStatus;
import ua.hudyma.domain.visa.VisaType;
import ua.hudyma.enums.Country;

import java.time.LocalDate;

public record VisaRequestDto(
        Long travelDataId,
        EntriesNumber entriesNumber,
        Country emittedByCountry,
        VisaStatus visaStatus,
        VisaType visaType,
        LocalDate issuedOn,
        LocalDate expiresOn
) {
}
