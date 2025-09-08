package ua.hudyma.domain.certify;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import ua.hudyma.domain.profile.Crew;
import ua.hudyma.domain.profile.Pilot;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "certificate_data")
@Data
public class CertificateData {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "certificateData",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Certificate> certificateList;
    @OneToOne
    @JoinColumn(name = "pilot_id")
    @JsonIgnore
    private Pilot pilot;
    @OneToOne
    @JoinColumn(name = "crew_id")
    @JsonIgnore
    private Crew crew;




}
