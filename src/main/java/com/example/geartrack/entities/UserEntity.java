package com.example.geartrack.entities;

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

    @Column(length = 100, unique = true)
    private String email;

    private String password;

    @Temporal(TemporalType.DATE)
    private LocalDate registerDate;

    @OneToMany(mappedBy = "tripOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripEntity> ownedTrips = new ArrayList<>();


}
