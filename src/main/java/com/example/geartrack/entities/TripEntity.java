package com.example.geartrack.entities;

import com.example.geartrack.models.enums.HikingType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.*;

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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HikingType hikingType;

    private String description;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity tripOwner;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TripItemEntity> tripItems = new HashSet<>();

    public Set<ItemEntity> getAllItemsForTrip() {

        if (this.tripItems == null) {
            return null;
        }

        Set<ItemEntity> items = new HashSet<>();
        for (TripItemEntity tripItem : this.getTripItems()) {
            items.add(tripItem.getItem());
        }

        return items;
    }

}
