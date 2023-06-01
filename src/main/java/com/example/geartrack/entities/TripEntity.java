package com.example.geartrack.entities;

import com.example.geartrack.models.enums.HikingType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "trips")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TripEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID tripId;

    private String name;

    @Enumerated(EnumType.STRING)
    private HikingType hikingType;

    private String description;

    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity tripOwner;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripItemEntity> tripItems = new ArrayList<>();

}
