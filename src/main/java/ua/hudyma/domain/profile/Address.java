package ua.hudyma.domain.profile;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import ua.hudyma.enums.Country;

@Embeddable
@Data
public class Address {
    @Enumerated(value = EnumType.STRING)
    private Country country;
    private String postalIndex;
    private String city;
    private String street;
    private String building;
    private String apartment;
}
