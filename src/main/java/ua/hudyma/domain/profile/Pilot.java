package ua.hudyma.domain.profile;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pilots")
@Data
public class Pilot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Profile profile;
    @Embedded
    private Address address;
    @Enumerated(value = EnumType.STRING)
    private PilotType pilotType;

}
