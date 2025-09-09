package ua.hudyma.domain.certify;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "certificates")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Certificate {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "certificate_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CertificateType certType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CertificateCategory certCat;
    @Column(name = "aircraft_type", length = 30)
    @Enumerated(EnumType.STRING)
    private AircraftType aircraftType;
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private CertifyAuthority certAuthority;
    @Column(name = "issued_at", nullable = false)
    private LocalDate issuedAt;
    @Column(name = "expires_at")
    private LocalDate expiresAt;
    @Column(name = "license_number", length = 20)
    private String licenseNumber;
    @Column(columnDefinition = "TEXT")
    private String notes;
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "certificate_data_id")
    private CertificateData certificateData;

}
