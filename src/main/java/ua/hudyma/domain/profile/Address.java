package ua.hudyma.domain.profile;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String country;
    private String region;
    private String city;
    private String street;
    private String building;
    private String apatment;
}
