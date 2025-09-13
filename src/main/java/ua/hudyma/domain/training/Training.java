package ua.hudyma.domain.training;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ua.hudyma.domain.certify.AircraftType;
import ua.hudyma.domain.profile.Crew;
import ua.hudyma.domain.profile.Pilot;
import ua.hudyma.domain.training.enums.TrainingAuthority;
import ua.hudyma.domain.training.enums.TrainingType;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "trainings")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainingType trainingType;
    @Column(name = "aircraft_type", length = 30)
    @Enumerated(EnumType.STRING)
    private AircraftType aircraftType;
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private TrainingAuthority trainingAuthority;
    @Column(nullable = false)
    private LocalDate issuedOn;
    @Column(name = "expires_at")
    private LocalDate expiresOn;
    @Column(name = "license_number", length = 20)
    private String certificateId;
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    @ManyToOne
    @JoinColumn(name = "pilot_id")
    private Pilot pilot;
    @ManyToOne
    @JoinColumn(name = "crew_id")
    private Crew crew;


}
