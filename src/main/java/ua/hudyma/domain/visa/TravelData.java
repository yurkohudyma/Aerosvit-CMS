package ua.hudyma.domain.visa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import ua.hudyma.domain.profile.Crew;
import ua.hudyma.domain.profile.Pilot;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "travel_data")
@Data
public class TravelData {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    private String passportId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate issuedOn;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expiresAt;
    @OneToMany(mappedBy = "travelData",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Visa> visaList;
    @OneToOne
    @JoinColumn(name = "pilot_id")
    @JsonIgnore
    private Pilot pilot;
    @OneToOne
    @JoinColumn(name = "crew_id")
    @JsonIgnore
    private Crew crew;
}
