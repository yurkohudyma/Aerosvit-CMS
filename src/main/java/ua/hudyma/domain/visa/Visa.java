package ua.hudyma.domain.visa;

import jakarta.persistence.*;
import lombok.Data;
import ua.hudyma.enums.Country;

import java.time.LocalDate;

@Entity
@Table(name = "visas")
@Data
public class Visa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String visaId;
    @Enumerated(value = EnumType.STRING)
    private Country emittedByCountry;
    @Enumerated(value = EnumType.STRING)
    private VisaType visaType;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private VisaStatus visaStatus;

    @Column(nullable = false)
    private LocalDate issuedOn;
    @Column(nullable = false)
    private LocalDate expiresOn;
    @ManyToOne
    @JoinColumn(name = "travel_data_id")
    private TravelData travelData;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private EntriesNumber entriesNumber;
}
