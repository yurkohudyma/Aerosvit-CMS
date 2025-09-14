package ua.hudyma.domain.profile;

import jakarta.persistence.*;
import lombok.Data;
import ua.hudyma.domain.certify.CertificateData;
import ua.hudyma.domain.training.Training;
import ua.hudyma.domain.visa.TravelData;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table
@Data
public class Crew {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Embedded
    private Profile profile;
    @Embedded
    private Address address;
    @Enumerated(value = EnumType.STRING)
    private CrewType crewType;
    @OneToOne(mappedBy = "crew",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private TravelData travelData;
    @OneToOne(mappedBy = "crew",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private CertificateData certificateData;
    @OneToMany(mappedBy = "crew",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<Training> trainingList;
}
