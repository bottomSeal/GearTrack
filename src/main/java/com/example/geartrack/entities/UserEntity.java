package com.example.geartrack.entities;

import com.example.geartrack.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID userId;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate registerDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "tripOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripEntity> ownedTrips = new ArrayList<>();


}
