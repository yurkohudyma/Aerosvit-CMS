package ua.hudyma.domain.profile;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {
    private String country;
    private String postalIndex;
    private String city;
    private String street;
    private String building;
    private String apartment;
}
