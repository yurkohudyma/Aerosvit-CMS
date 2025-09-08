package ua.hudyma.domain.profile;

import jakarta.persistence.*;
import lombok.Data;
import ua.hudyma.domain.visa.TravelData;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "pilots")
@Data
public class Pilot {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Embedded
    private Profile profile;
    @Embedded
    private Address address;
    @Enumerated(value = EnumType.STRING)
    private PilotType pilotType;
    @OneToOne(mappedBy = "pilot",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private TravelData travelData;

}
