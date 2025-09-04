package ua.hudyma.domain.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Embeddable
@Data
public class Profile {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    private LocalDate birthday;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @CreationTimestamp
    private LocalDateTime registeredOn;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String phoneNumber;
}
